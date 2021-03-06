package com.feign.domain;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table users
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class Users {
    /**
     * Database Column Remarks:
     *   用户编号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.user_id
     *
     * @mbg.generated
     */
    private String userId;

    /**
     * Database Column Remarks:
     *   用户名
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.user_name
     *
     * @mbg.generated
     */
    private String userName;

    /**
     * Database Column Remarks:
     *   用户手机号码
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.user_phone
     *
     * @mbg.generated
     */
    private String userPhone;

    /**
     * Database Column Remarks:
     *   用户密码
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.user_password
     *
     * @mbg.generated
     */
    private String userPassword;

    /**
     * Database Column Remarks:
     *   用户最近一次登录时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.user_last_login_time
     *
     * @mbg.generated
     */
    private String userLastLoginTime;

    /**
     * Database Column Remarks:
     *   用户注册时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.user_create_time
     *
     * @mbg.generated
     */
    private String userCreateTime;

    /**
     * Database Column Remarks:
     *   用户状态，0正常，-1删除
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.user_status
     *
     * @mbg.generated
     */
    private String userStatus;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.user_id
     *
     * @return the value of users.user_id
     *
     * @mbg.generated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.user_id
     *
     * @param userId the value for users.user_id
     *
     * @mbg.generated
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.user_name
     *
     * @return the value of users.user_name
     *
     * @mbg.generated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.user_name
     *
     * @param userName the value for users.user_name
     *
     * @mbg.generated
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.user_phone
     *
     * @return the value of users.user_phone
     *
     * @mbg.generated
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.user_phone
     *
     * @param userPhone the value for users.user_phone
     *
     * @mbg.generated
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.user_password
     *
     * @return the value of users.user_password
     *
     * @mbg.generated
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.user_password
     *
     * @param userPassword the value for users.user_password
     *
     * @mbg.generated
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.user_last_login_time
     *
     * @return the value of users.user_last_login_time
     *
     * @mbg.generated
     */
    public String getUserLastLoginTime() {
        return userLastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.user_last_login_time
     *
     * @param userLastLoginTime the value for users.user_last_login_time
     *
     * @mbg.generated
     */
    public void setUserLastLoginTime(String userLastLoginTime) {
        this.userLastLoginTime = userLastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.user_create_time
     *
     * @return the value of users.user_create_time
     *
     * @mbg.generated
     */
    public String getUserCreateTime() {
        return userCreateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.user_create_time
     *
     * @param userCreateTime the value for users.user_create_time
     *
     * @mbg.generated
     */
    public void setUserCreateTime(String userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.user_status
     *
     * @return the value of users.user_status
     *
     * @mbg.generated
     */
    public String getUserStatus() {
        return userStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.user_status
     *
     * @param userStatus the value for users.user_status
     *
     * @mbg.generated
     */
    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}