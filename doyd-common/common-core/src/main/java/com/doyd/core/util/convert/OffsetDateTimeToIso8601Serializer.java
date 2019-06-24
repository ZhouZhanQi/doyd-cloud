package com.doyd.core.util.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zhouzq
 * @date 2019-06-24
 */
public class OffsetDateTimeToIso8601Serializer extends JsonSerializer<OffsetDateTime> {
    public static final OffsetDateTimeToIso8601Serializer INSTANCE = new OffsetDateTimeToIso8601Serializer();

    @Override
    public void serialize(OffsetDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }
}
