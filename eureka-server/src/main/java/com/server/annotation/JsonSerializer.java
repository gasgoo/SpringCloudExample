package com.server.annotation;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 自定义序列化  如果有注解则判断注解是否有值 否则就用属性名称当作序列化key
 * @Date 2020/4/15 9:39
 * @name JsonSerializer
 */

@Slf4j
public class JsonSerializer {


    /**
     * @Description  反射获取对象属性 判断属性是否有序列化注解
     * @Date 2020/4/15 9:40
     **/
    public static String serialize(Object object) throws IllegalAccessException {
        Class<?> objectClass=object.getClass();
        Map<String,String> jsonEle= Maps.newHashMap();
        for(Field field:objectClass.getDeclaredFields()){
            field.setAccessible(true);
            jsonEle.put(getSerializeKey(field), (String)field.get(object));
        }
        return toJsonString(jsonEle);
    }

    private static String getSerializeKey(Field field){
        //获取注解的值
        if(field.isAnnotationPresent(JsonField.class)){
            String annotationValue=field.getAnnotation(JsonField.class).value();
            if(annotationValue.isEmpty()){
                return field.getName();
            }else{
                return annotationValue;
            }
        }else{
            return field.getName();
        }

    }

    private static String toJsonString(Map<String,String> jsonMap){
        String eleString = jsonMap.entrySet().stream().map(entry -> "\"" + entry.getKey() + "\":" + entry.getValue() + "\"").collect(Collectors.joining(","));
        return "{"+eleString+"}";


    }

}
