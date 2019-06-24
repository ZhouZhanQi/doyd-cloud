package com.doyd.coordinator.service;

import com.doyd.coordinator.entity.TccRequest;

/**
 * @author zhouzq
 * @date 2019/6/22
 * @desc 协调器服务
 */
public interface ICoordinateService {

    /**
     * 确认资源链接
     */
    void confirm(TccRequest request);


    /**
     * 取消资源链接
     */
    void cancel(TccRequest request);

}
