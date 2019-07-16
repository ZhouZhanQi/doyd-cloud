package com.doyd.configserver.helper;

import com.doyd.core.util.json.Json;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 特殊处理
 * @author zhouzq
 * @date 2019/7/15
 * @desc
 */
@Slf4j
public final class ByteArrayDecoder implements Decoder {

    public ByteArrayDecoder() {
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        if (response.status() == 404) {
            return Util.emptyValueOf(type);
        } else if (response.body() == null) {
            return null;
        } else if (String.class.equals(type)){
            return Util.toString(response.body().asReader());
        } else {
            return new String(Util.toByteArray(response.body().asInputStream()));
        }
    }
}
