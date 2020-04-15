package com.common.utils;


import com.alibaba.fastjson.JSONException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class JSONUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JSONUtil.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final XmlMapper xmlMapper = new XmlMapper();
    private static final JavaTimeModule javaTimeModule = new JavaTimeModule();

    static {
        //todo 过时方法替换
        mapper.enable(SerializationFeature.WRITE_NULL_MAP_VALUES);
        mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        mapper.registerModule(javaTimeModule);
        //todo 过时方法替换
        xmlMapper.enable(SerializationFeature.WRITE_NULL_MAP_VALUES);
        xmlMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        xmlMapper.registerModule(javaTimeModule);
    }

    /**
     * 把Java对象转为JSON字符串
     * @param obj the object need to transfer into json string.
     * @return json string.
     */
    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            LOG.error("LK-PC0019a: to json exception.", e);
            throw new JSONException("把对象转换为JSON时出错了", e);
        }
    }

    /**
     * 把JSON转换为Java对象
     * @param json  the json string
     * @param clazz will convert into class
     * @param <T>   return type
     * @return java object.
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            LOG.error("LK-PC00186: from json exception", e);
            throw new JSONException(e.getLocalizedMessage());
        }
    }

    /**
     * 获取泛型的Collection Type
     * @param jsonStr         json字符串
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类型
     */
    public static <T> T fromJson(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return mapper.readValue(jsonStr, javaType);
        } catch (IOException e) {
            throw new RuntimeException("convert json error:" + e.getLocalizedMessage());
        }
    }

    /**
     * 把Object对象转换为Class类型的对象
     */
    public static <T> T convert(Object o, Class<T> tClass) {
        return mapper.convertValue(o, tClass);
    }


    public static Map<String, Object> toMap(String json) {
        return fromJson(json, Map.class);
    }

    /**
     * 根据key获取json对应value
     */
    public static String getValue(String jsonString, String key) {
        try {
            return mapper.readTree(jsonString).findValue(key).asText();
        } catch (IOException e) {
            throw new JSONException("JSON异常", e);
        }
    }

    /**
     * 根据key获取json对应values
     */
    public static List<String> getValues(String jsonString, String key) {
        return getValues(jsonString, key, String.class);
    }

    /**
     * 根据key获取json对应value
     */
    public static <T> T getValue(String jsonString, String key, Class<T> clazz) {
        try {
            return fromJson(mapper.readTree(jsonString).findValue(key).toString(), clazz);
        } catch (IOException e) {
            throw new JSONException("JSON异常", e);
        }
    }

    /**
     * 根据key获取json对应values
     */
    public static <T> List<T> getValues(String jsonString, String key, Class<T> clazz) {
        try {
            if (!StringUtils.isEmpty(key)) {
                List<JsonNode> values = mapper.readTree(jsonString).findValues(key);
                return fromJson(values.toString(), List.class, clazz);
            } else {
                return fromJson(jsonString, List.class, clazz);
            }
        } catch (IOException e) {
            throw new JSONException("JSON异常", e);
        }
    }

    /**
     * xml转json
     */
    public static String xmlToJson(String xmlString) {
        //剔除xml的注释信息
        xmlString = xmlString.replace("\n", "").replace("(?s)<\\!\\-\\-.+?\\-\\->", "");
        try {
            return toJson(xmlMapper.readTree(xmlString));
        } catch (IOException e) {
            throw new JSONException("JSON异常", e);
        }
    }


}

