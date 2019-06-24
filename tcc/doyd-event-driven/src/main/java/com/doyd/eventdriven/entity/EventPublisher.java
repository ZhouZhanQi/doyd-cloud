package com.doyd.eventdriven.entity;

import com.doyd.dps.entity.domain.BasicDomain;
import com.doyd.eventdriven.entity.enums.EventStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"}, ignoreUnknown = true)
public class EventPublisher extends BasicDomain {
    private static final long serialVersionUID = 2840081172778887899L;

    @NotNull
    @NotBlank
    private String businessType;

    @NotNull
    private EventStatusEnum eventStatus;

    @NotNull
    @JsonRawValue
    private String payload;

    @NotNull
    private Integer lockVersion;

    @NotNull
    @NotBlank
    private String guid;

}