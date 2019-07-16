package com.doyd.configserver.service;

import com.doyd.configserver.vo.AppInfoVo;

import java.util.List;

/**
 * @author zhouzq
 * @date 2019/7/2
 * @desc yml配置文件服务
 */
public interface IYmlConfigService {

    /**
     * 列出当前注册到服务注册中心的应用列表
     * @return
     */
    List<AppInfoVo> listAppInfo();

    /**
     * 获取应用的yaml配置内容
     * @param applicaiton 服务ID(对应spring.application.name)
     * @return
     */
    String getYamlContent(String applicaiton);

    /**
     * 编辑修改yml配置内容
     * @param application 服务Id
     * @param content 内容
     */
    void editYamlContent(String application, String content);
}
