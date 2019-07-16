package com.doyd.configserver.vo.bootadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * @author zhouzq
 * @date 2019/7/3
 * @desc
 */
@Data
@EqualsAndHashCode(exclude = {"unsavedEvents", "statusTimestamp"})
@ToString(exclude = "unsavedEvents")
public class Instance implements Serializable {

    private  InstanceId id;

    private  long version;

    private  Registration registration;

    private  boolean registered;

    private  StatusInfo statusInfo;

    private  Instant statusTimestamp;

    private  Info info;

    private  List<InstanceEvent> unsavedEvents;

    private  List<Endpoint> endpoints;

    private BuildVersion buildVersion;

    private Tags tags;

}
