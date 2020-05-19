package com.server.service.impl;

import com.google.common.base.Strings;
import com.server.annotation.WebLog;
import com.server.dao.NewsUserDao;
import com.server.domain.UserBean;
import com.server.redis.RedisUtils;
import com.server.web.VM.LoginVO;
import com.common.model.BaseResponse;
import com.common.model.Constants;
import com.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

/**
 * @Author gg.rao
 * @Date 2019/3/26 11:41
 */
@Service
@Transactional
public class UserService extends BaseService  {


    @Autowired
    private NewsUserDao newsUserDao;
    @Autowired
    private RedisUtils redisUtils;

    public UserBean getUserById(int userId) {
        return newsUserDao.selectByPrimaryKey(userId);
    }


    public boolean addUser(UserBean record) {
        boolean result = false;
        try {
            newsUserDao.add(record);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @WebLog
    public BaseResponse<String> login(LoginVO request) throws NoSuchAlgorithmException {
        UserBean userBean = new UserBean();
        userBean.setPassword(request.getPassword());
        userBean.setMobile(request.getMobile());
        List<UserBean> userList = newsUserDao.selectUser(userBean);
        if (null != userList && userList.size() > 0) {
            String token = getToken(request.getMobile(), request.getPassword());
            redisUtils.set("userToken", token, 120);
            BaseResponse result = new BaseResponse();
            result.setData(token);
            return result;
        } else {
            return BaseResponse.fail(Constants.FAIL, "手机号或密码输入不正确！");
        }
    }

    public List<UserBean> getUsers() {
        return newsUserDao.getAllUsers();
    }

    public UserBean selectByMobile(String mobile){
        return newsUserDao.selectByMobile(mobile);
    }

    public BaseResponse<UserBean> add(UserBean userBean, String token) {
        String userToken = (String) redisUtils.get("userToken");
        if(Strings.isNullOrEmpty(userToken)){
            return BaseResponse.fail(Constants.FAIL,"用户token失效超时!");
        }
        if (userToken.equals(token)) {
            if(Objects.nonNull(selectByMobile(userBean.getMobile()))){
                return BaseResponse.fail(Constants.FAIL,"手机号已经存在不能重复添加!");
            }
            this.addUser(userBean);
            return BaseResponse.success(userBean);
        } else {
            return BaseResponse.fail(Constants.FAIL, "用户token无效！");
        }
    }


}
