package com.doyd.configserver.api;

import com.doyd.configserver.service.IYmlConfigService;
import com.doyd.configserver.vo.AppInfoVo;
import com.doyd.core.vo.ResponseVo;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhouzq
 * @date 2019/7/2
 * @desc yml配置文件api
 */
@Api(value = "YamlConfigApi", tags = {"yaml文件配置API"})
@RestController
@RequestMapping(value = "/admin/configserver/ymlconfig", produces = MediaType.APPLICATION_JSON_VALUE)
public class YmlConfigApi {

    @Autowired
    private IYmlConfigService ymlConfigService;

    @GetMapping("/ms-apps")
    public ResponseVo<List<AppInfoVo>> listMicroServiceApp() {
        return ResponseVo.success(ymlConfigService.listAppInfo());
    }

    @GetMapping("/ms-app/{appId}")
    public ResponseVo microServiceAppInfo(@PathVariable("appId") String appId){

        return ResponseVo.success();
    }

}
