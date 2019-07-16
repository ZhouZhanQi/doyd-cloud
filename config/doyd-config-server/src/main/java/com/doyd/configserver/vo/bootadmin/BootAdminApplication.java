package com.doyd.configserver.vo.bootadmin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zhouzq
 * @date 2019/7/3
 * @desc bootadmin应用信息
 */
@Data
@NoArgsConstructor
public class BootAdminApplication implements Serializable {

    private String name;

    @Nullable
    private BuildVersion buildVersion;

    private String status;

    private Instant statusTimestamp = Instant.now();

    private List<Instance> instances = new ArrayList<>();
}
