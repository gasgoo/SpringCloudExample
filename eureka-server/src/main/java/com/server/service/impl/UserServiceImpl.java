package com.server.service.impl;

import com.server.annotation.WebLog;
import com.server.dao.NewsUserDao;
import com.server.domain.UserBean;
import com.server.redis.RedisUtils;
import com.server.service.UserService;
import com.server.web.VM.LoginVO;
import com.server.web.VM.TokenResponse;
import com.common.model.BaseResponse;
import com.common.model.Constants;
import com.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Author gg.rao
 * @Date 2019/3/26 11:41
 */
@Service
@Transactional
public class UserServiceImpl extends BaseService implements UserService {


    @Autowired
    private NewsUserDao newsUserDao;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public UserBean getUserById(int userId) {
        return newsUserDao.selectByPrimaryKey(userId);
    }

    @Override
    public boolean addUser(UserBean record) {
        boolean result = false;
        try {
            //record.setPassword(encryptPassword(record.getPassword()));
            newsUserDao.add(record);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    @WebLog
    public BaseResponse<TokenResponse> login(LoginVO request) throws NoSuchAlgorithmException {
        //String password=encryptPassword(request.getPassword());
        UserBean userBean = new UserBean();
        userBean.setPassword(request.getPassword());
        userBean.setMobile(request.getMobile());
        List<UserBean> userList = newsUserDao.selectUser(userBean);
        if (null != userList && userList.size() > 0) {
            String token = getToken(request.getMobile(), request.getPassword());
            redisUtils.set("userToken", token, 600);
            TokenResponse response = new TokenResponse();
            response.setToken(token);
            return BaseResponse.success(response);
        } else {
            return BaseResponse.fail(Constants.FAIL, "手机号或密码输入不正确！");
        }
    }

    @Override
    public List<UserBean> getUsers() {
        return newsUserDao.getAllUsers();
    }

    @Override
    public BaseResponse<UserBean> add(UserBean userBean, String token) {
        String userToken = (String) redisUtils.get("userToken");
        if (userToken.equals(token)) {
            this.addUser(userBean);
            return BaseResponse.success(userBean);
        } else {
            return BaseResponse.fail(Constants.FAIL, "用户token无效！");
        }
    }


}
