package com.github.pioneeryi.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pioneeryi.exception.MixqJdbcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * json中的Object转换为java对象
     *
     * @param object
     * @param valueType
     * @param <T>       对象类型
     * @return
     */
    public static <T> T objectToObject(Object object, Class<T> valueType) {
        try {
            return objectMapper.convertValue(object, valueType);
        } catch (Exception e) {
            logger.error("Convert objectToObject by class error.obj={},class={}", object, valueType, e);
            throw new MixqJdbcException("JsonUtil mapToObject ERROR! object: " + object + ", class: " + valueType, e);
        }

    }

    /**
     * json中的Object转换为java对象
     *
     * @param object
     * @param typeReference
     * @param <T>           对象类型，支持list
     * @return
     */
    public static <T> T objectToObject(Object object, TypeReference<?> typeReference) {
        try {
            return (T) objectMapper.convertValue(object, typeReference);
        } catch (Exception e) {
            logger.error("Convert objectToObject by TypeReference error.obj={},typeReference={}", object, typeReference,
                    e);
            throw new MixqJdbcException(
                    "JsonUtil mapToObject ERROR! object: " + object + ", typeReference: " + typeReference, e);
        }
    }

    /**
     * java对象转json
     *
     * @param object
     * @return json字符串
     * @throws IOException
     */
    public static String objectToJsonStr(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error("Convert objectToJson error.obj={}", object, e);
            throw new MixqJdbcException("JsonUtil mapToJson ERROR! obj: " + object, e);
        }
    }

    /**
     * json的inputStream转java对象
     *
     * @param inputStream
     * @param valueType
     * @param <T>         对象类型
     * @return
     */
    public static <T> T jsonStreamToObject(InputStream inputStream, Class<T> valueType) {
        try {
            return objectMapper.readValue(inputStream, valueType);
        } catch (Exception e) {
            logger.error("Convert jsonStreamToObject error.", e);
            throw new MixqJdbcException("JsonUtils mapToObject ERROR!", e);
        }
    }

}
