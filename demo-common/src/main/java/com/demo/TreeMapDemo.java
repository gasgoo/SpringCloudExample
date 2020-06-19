package com.demo;

import com.alibaba.fastjson.JSON;
import com.common.utils.JSONUtil;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;

/**
 * @Date 2019/9/23 15:41
 */
public class TreeMapDemo  {

    class DTO implements  Comparable<DTO>{
        private Integer id;
        private String name;
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public DTO(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public DTO(){

        }
        @Override
        public int compareTo(DTO o) {
            return id -o.getId();
        }

    }

    public void testComparable(){
        List<DTO> list= new ArrayList();
        for(int i=5;i>0;i--){
            list.add(new DTO(i,"i"));
        }
        Collections.sort(list);
        System.out.println("===="+ JSON.toJSONString(list));
        System.out.println("<<<<<<<<====>>>>>>");
        // 第二种排序，从大到小排序，利用外部排序器 Comparator 进行排序
        Comparator comparator = (Comparator<DTO>) (o1, o2) -> o2.getId() - o1.getId();
        List<DTO> list2 = new ArrayList<>();
        for (int i = 5; i > 0; i--) {
            list2.add(new DTO(i,"i"));
        }
        Collections.sort(list,comparator);

    }

    public static void main(String[] args) throws InterruptedException, IOException {
        TreeMapDemo dto = new TreeMapDemo();
        String url="http://192.168.1.32:100/gateway/defensor/api/open/jwt/login?userName=ccs&password=ccs1234qwer&appId=0000000000";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost=new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }




}
