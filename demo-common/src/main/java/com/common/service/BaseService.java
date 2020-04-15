package com.common.service;


import java.security.NoSuchAlgorithmException;
import com.common.utils.*;

/**
 * @Date 2019/4/19 19:20
 */
public abstract  class BaseService {
    /**
     * 密码加密算法
     * @param password
     * @return
     */
    protected String encryptPassword(String password) throws NoSuchAlgorithmException {
        return SHAUtils.sha1(password);
    }

    /**
     * 生成API鉴权的Token
     * @param mobile
     * @param password
     * @return
     */
    protected String getToken(String mobile,String password) throws NoSuchAlgorithmException {
        return SHAUtils.sha1(mobile+password);
    }
}
