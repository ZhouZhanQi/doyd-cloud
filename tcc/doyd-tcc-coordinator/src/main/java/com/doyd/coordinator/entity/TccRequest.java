package com.doyd.coordinator.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author zhouzq
 * @date 2019/6/24
 * @desc tcc请求参数
 */
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class TccRequest implements Serializable {

    private static final long serialVersionUID = -4808695222488720769L;


    @Valid
    @NotNull
    @Size(min = 1)
    @ApiModelProperty(value = "参与方提供的链接集合", required = true)
    private List<Participant> participantLinks;

}
