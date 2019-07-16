package com.doyd.configserver.vo.bootadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Map;
import java.util.Scanner;

/**
 * @author zhouzq
 * @date 2019/7/3
 * @desc 版本
 */
@Data
public class BuildVersion  implements Serializable, Comparable<BuildVersion>  {

    private final String value;

    private BuildVersion(String value) {
        Assert.hasText(value, "'value' must not be empty");
        this.value = value;
    }

    public static BuildVersion valueOf(String s) {
        return new BuildVersion(s);
    }

    @Nullable
    public static BuildVersion from(Map<String, ?> map) {
        if (map.isEmpty()) {
            return null;
        }

        Object build = map.get("build");
        if (build instanceof Map) {
            Object version = ((Map<?, ?>) build).get("version");
            if (version instanceof String) {
                return valueOf((String) version);
            }
        }

        Object version = map.get("build.version");
        if (version instanceof String) {
            return valueOf((String) version);
        }

        version = map.get("version");
        if (version instanceof String) {
            return valueOf((String) version);
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public int compareTo(BuildVersion other) {
        try (Scanner s1 = new Scanner(this.value); Scanner s2 = new Scanner(other.value)) {
            s1.useDelimiter("[.\\-+]");
            s2.useDelimiter("[.\\-+]");

            while (s1.hasNext() && s2.hasNext()) {
                int c;
                if (s1.hasNextInt() && s2.hasNextInt()) {
                    c = Integer.compare(s1.nextInt(), s2.nextInt());
                } else {
                    c = s1.next().compareTo(s2.next());
                }
                if (c != 0) {
                    return c;
                }
            }

            if (s1.hasNext()) {
                return 1;
            } else if (s2.hasNext()) {
                return -1;
            }
        }
        return 0;
    }
}
