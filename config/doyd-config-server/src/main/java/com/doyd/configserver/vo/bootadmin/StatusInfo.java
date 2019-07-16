package com.doyd.configserver.vo.bootadmin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import java.util.*;

import static java.util.Arrays.asList;

/**
 * Represents a certain status a certain time.
 *
 * @author Johannes Stelzer
 */
@Data
public class StatusInfo {

    public static final String STATUS_UNKNOWN = "UNKNOWN";
    public static final String STATUS_OUT_OF_SERVICE = "OUT_OF_SERVICE";
    public static final String STATUS_UP = "UP";
    public static final String STATUS_DOWN = "DOWN";
    public static final String STATUS_OFFLINE = "OFFLINE";
    public static final String STATUS_RESTRICTED = "RESTRICTED";
    private static final List<String> STATUS_ORDER = asList(STATUS_DOWN,
            STATUS_OUT_OF_SERVICE,
            STATUS_OFFLINE,
            STATUS_UNKNOWN,
            STATUS_RESTRICTED,
            STATUS_UP
    );

    private String status;

    private Map<String, Object> details;
}