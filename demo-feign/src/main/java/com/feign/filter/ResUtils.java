package com.feign.filter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @TODO
 * @Date 2020/6/12 11:56
 * @name ResUtils
 */

@Slf4j
public class ResUtils {

    /**
     * @Description  内容写入body
     * @Date 2020/6/11 19:40
     **/
    public static void responseJson(HttpServletResponse response, Object obj)  {
        response.setContentType("application/json; charset=utf-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.print(JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat));
            writer.close();
            response.flushBuffer();
        }catch (Exception e){
            log.error("返回response内容异常:{}",e);
        }
    }
}
