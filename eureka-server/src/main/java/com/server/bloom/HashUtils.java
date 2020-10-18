package com.server.bloom;

import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

/**
 * @Desc
 * @Date 2020/10/18 10:28
 */
public class HashUtils {

    public static int hash(String data) {
        return data.hashCode() & Integer.MAX_VALUE;
    }

    public  static long hash128(String key) {
        Charset charset = Charset.forName("UTF-8");
        return Hashing.murmur3_128().hashObject(key, Funnels.stringFunnel(charset)).asLong();
    }

    public static void main(String[] args) {
        String key="raogugen100000000000";
        System.out.println(HashUtils.hash(key)+"              "+HashUtils.hash128(key));
    }
}
