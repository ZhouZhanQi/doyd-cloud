package com.doyd.configserver.client;

import com.doyd.configserver.config.FeginClientConfig;
import com.doyd.configserver.vo.bootadmin.BootAdminApplication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zhouzq
 * @date 2019/7/2
 * @desc bootadmin 调用接口
 */
@FeignClient(name = "doyd-boot-admin", configuration = FeginClientConfig.class)
public interface BootAdminApiClient {

    /**
     * 获取注册中心的服务实例列表
     *
     * @return
     */
    @GetMapping(value = "/applications", produces = MediaType.APPLICATION_JSON_VALUE)
    String listApps();

    /**
     * 获取某个服务实例信息
     * @param name
     * @return
     */
    @GetMapping(value = "/applications/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BootAdminApplication> getApplicationInfoByAppName(@PathVariable("name") String name);
}
