package com.doyd.configserver.api;

import com.doyd.configserver.service.IYmlConfigService;
import com.doyd.configserver.vo.AppInfoVo;
import com.doyd.core.vo.ResponseVo;
import io.swagger.annotations.*;
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
@RequestMapping(value = "/admin/ymlconfig", produces = MediaType.APPLICATION_JSON_VALUE)
public class YmlConfigApi {

    @Autowired
    private IYmlConfigService ymlConfigService;

    @GetMapping("/applications")
    @ApiOperation(value = "查询注册中心所有应用")
    public ResponseVo<List<AppInfoVo>> listMicroServiceApp() {
        return ResponseVo.success(ymlConfigService.listAppInfo());
    }

    @GetMapping("/applications/{name}")
    @ApiOperation(value = "查询某个应用配置信息")
    public ResponseVo microServiceAppInfo(@PathVariable("name") String name){
        return ResponseVo.success(ymlConfigService.getYamlContent(name));
    }

    @PutMapping("/applications/{name}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "配置文件内容", required = true)
    })
    @ApiOperation(value = "修改某个应用配置信息")
    public ResponseVo microServiceConfigEdit(@PathVariable("name") String name, @RequestParam("content") String content){
        ymlConfigService.editYamlContent(name, content);
        return ResponseVo.success();
    }

    @ApiOperation(value = "获取某个应用历史配置版本信息")
    @GetMapping("/applications/{name}/history")
    public ResponseVo<List<String>> listMicroServiceHistoryConfig(@PathVariable("name") String name) {
        return ResponseVo.success(ymlConfigService.listYamlHistory(name));
    }

    @ApiOperation(value = "获取某个应用某个版本配置信息")
    @GetMapping("/applications/{name}/history/{version}")
    public ResponseVo microServiceHistoryInfo(@PathVariable("name") String name, @PathVariable("version") String version) {
        return ResponseVo.success(ymlConfigService.getYamlHistoryContent(name, version));
    }

    @ApiOperation(value = "回滚某个应用配置到某个版本")
    @PostMapping("/applications/{name}/history/{version}/rollback")
    public ResponseVo rollbackMicroServiceConfig(@PathVariable("name") String name, @PathVariable("version") String version) {
        ymlConfigService.rollbackAppConfig(name, version);
        return ResponseVo.success();
    }

}
