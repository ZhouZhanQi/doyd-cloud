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

}
