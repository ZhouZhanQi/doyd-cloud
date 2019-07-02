package com.doyd.configserver.client;

import com.doyd.configserver.vo.bootadmin.BootAdminAppVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author zhouzq
 * @date 2019/7/2
 * @desc bootadmin 调用接口
 */
@FeignClient(name = "doyd-boot-admin")
public interface BootAdminApiClient {

    /**
     * 获取注册中心的服务实例列表
     *
     * @return
     */
    @GetMapping("/api/applications")
    List<BootAdminAppVo> listApps();

}
