package com.doyd.coordinator.service;

import com.doyd.coordinator.entity.Participant;
import com.doyd.coordinator.entity.TccErrorResponse;
import com.doyd.coordinator.entity.TccRequest;
import com.doyd.coordinator.enums.ResponseCodeEnum;
import com.doyd.coordinator.enums.TccCodeEnum;
import com.doyd.coordinator.exception.PartialConfirmException;
import com.doyd.coordinator.exception.ReservationExpireException;
import com.doyd.dps.Shift;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhouzq
 * @date 2019/6/24
 * @desc tcc协调器实现类
 */
@Slf4j
@Service
public class CoordinateServiceImpl implements ICoordinateService {

    private static final int LEEWAY = 1;

    @Autowired
    private RestTemplate restTemplate;

    private static final HttpEntity<?> REQUEST_ENTITY;

    static {
        final HttpHeaders header = new HttpHeaders();
        header.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON_UTF8));
        header.setContentType(MediaType.APPLICATION_JSON_UTF8);
        REQUEST_ENTITY = new HttpEntity<>(header);
    }

    @Override
    public void confirm(TccRequest request) {
        Preconditions.checkNotNull(request);
        final List<Participant> participantLinks = request.getParticipantLinks();
        Preconditions.checkNotNull(participantLinks);
        //checkExpireInLocal(request, participantLinks);
        // 调用确认资源链接
        int success = 0;
        int fail = 0;
        for (Participant participant : participantLinks) {
            participant.setExecuteTime(OffsetDateTime.now());
            // 必须设置重试以防参与者宕机或网络抖动
            final ResponseEntity<String> response = restTemplate.exchange(participant.getUri(), HttpMethod.PUT, REQUEST_ENTITY, String.class);
            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                participant.setTccStatus(TccCodeEnum.CONFIRMED);
                success++;
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                participant.setTccStatus(TccCodeEnum.TIMEOUT);
                participant.setParticipantErrorResponse(response);
                fail++;
            } else {
                Shift.fatal(ResponseCodeEnum.SERVER_UNKNOWN_ERROR, response);
            }
        }
        // 检查是否有冲突
        if (success > 0 && fail > 0) {
            // 出现冲突必须返回并需要人工介入
            throw new PartialConfirmException("all reservation were cancelled or timeout", new TccErrorResponse(participantLinks));
        } else if (fail == participantLinks.size()) {
            // 全部timeout
            throw new ReservationExpireException("although we have check the expire time in request body, we got an expiration when confirming actually");
        }
    }

    @Override
    public void cancel(TccRequest request) {
        Preconditions.checkNotNull(request);
        final List<Participant> participantList = Preconditions.checkNotNull(request.getParticipantLinks());
        try {
            for (Participant participant : participantList) {
                restTemplate.exchange(participant.getUri(), HttpMethod.DELETE, null, String.class);
            }
        } catch (Exception e) {
            log.debug("unexpected error when making compensation: {}", e.toString());
        }
    }

    private OffsetDateTime fetchTheRecentlyExpireTime(List<Participant> participantLink) {
        Preconditions.checkNotNull(participantLink);
        // 计算出过期时间集合
        final List<OffsetDateTime> dateTimeList = participantLink.stream()
                .flatMap(x -> Stream.of(x.getExpireTime()))
                .filter(x -> x.isAfter(OffsetDateTime.now()))
                .sorted()
                .collect(Collectors.toList());
        // 检查是否具有已经过期的事务
        if (dateTimeList.size() != participantLink.size()) {
            throw new ReservationExpireException("there has a expired transaction");
        }
        // 检测是否将近过期, 集合经过Validator检查必有一个元素
        return dateTimeList.get(0);
    }
}
