package com.doyd.configserver.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhouzq
 * @date 2019/7/2
 * @desc
 */
@Data
@ApiModel("配置文件版本参数")
public class AppVersionReqVo implements Serializable {

    @ApiModelProperty("历史版本")
    private String version;

}
