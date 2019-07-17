package com.doyd.configserver.service;

import com.doyd.configserver.vo.AppInfoVo;
import org.apache.commons.lang3.StringUtils;

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
     * @param application 服务ID(对应spring.application.name)
     * @return
     */
    String getYamlContent(String application);

    /**
     * 编辑修改yml配置内容
     * @param application 服务Id
     * @param content 内容
     */
    void editYamlContent(String application, String content);

    /**
     * 某个应用历史配置信息
     * @param application
     * @return
     */
    List<String> listYamlHistory(String application);

    /**
     * 获取某个应用某个历史版本信息
     * @param application
     * @param version
     * @return
     */
    String getYamlHistoryContent(String application, String version);

    /**
     * 回滚应用到指定版本
     * @param application
     * @param version
     */
    void rollbackAppConfig(String application, String version);
}
