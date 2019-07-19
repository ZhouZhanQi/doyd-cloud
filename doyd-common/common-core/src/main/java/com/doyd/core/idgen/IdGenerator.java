package com.doyd.core.idgen;

/**
 * 主键ID生成器
 *
 * @author zhouzq
 * @date 2018-08-23
 */
public interface IdGenerator {

    /**
     * 生成主键ID
     *
     * @return 生成的主键ID
     */
    long generateId();
}
