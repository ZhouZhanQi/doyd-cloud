package com.doyd.eventdriven.persistence;


import com.doyd.eventdriven.entity.EventSubscriber;
import com.doyd.eventdriven.entity.enums.EventStatusEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EventSubscriberMapper {

    int insertSelective(EventSubscriber subscriber);

    int updateEventStatusByPrimaryKeyInCasMode(@Param("id") Long id, @Param("expect") EventStatusEnum expect, @Param("target") EventStatusEnum target);

}