package com.doyd.core.util.convert;

import com.doyd.core.util.JacksonUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author zhouzq
 * @date 2019-06-24
 */
public class StringToMapSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String val = value;
        if (value == null || value.isEmpty()) {
            val = "{}";
        }

        gen.writeObject(JacksonUtils.jsonToMap(val));
    }

}
