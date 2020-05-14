package com.server.dao;

import com.server.domain.UserBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户操作dao
 * @Author gg.rao
 * @Date 2019/4/22 10:57
 */
@Mapper
public interface NewsUserDao {

    List<UserBean> selectUser(UserBean userBean);

    UserBean selectByPrimaryKey(Integer id);

    void add(UserBean userBean);

    List<UserBean> getAllUsers();

    UserBean selectByMobile(String mobile);



}
