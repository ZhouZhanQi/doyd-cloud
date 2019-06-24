package com.doyd.coordinator.entity;

import com.doyd.coordinator.enums.TccCodeEnum;
import com.doyd.core.util.convert.OffsetDateTimeToIso8601Serializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

/**
 * @author zhouzq
 * @date 2019/6/24
 * @desc 参与者
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class Participant {

    @URL
    @ApiModelProperty(value = "资源URI", required = true, example = "http://www.example.com/part/123")
    private String uri;

    @ApiModelProperty(value = "过期时间, ISO标准", required = true, example = "2017-03-20T14:00:41+08:00")
    @JsonSerialize(using = OffsetDateTimeToIso8601Serializer.class)
    private OffsetDateTime expireTime;

    @ApiModelProperty(value = "发起confirm的时间, ISO标准", hidden = true, example = "2017-03-20T14:00:41+08:00")
    @JsonSerialize(using = OffsetDateTimeToIso8601Serializer.class)
    private OffsetDateTime executeTime;

    @ApiModelProperty(value = "参与方返回的错误码", hidden = true, example = "451")
    private ResponseEntity<?> participantErrorResponse;

    @ApiModelProperty(value = "预留资源的状态", hidden = true, example = "TO_BE_CONFIRMED")
    private TccCodeEnum tccStatus = TccCodeEnum.TO_BE_CONFIRMED;

}
