package com.feign.dao;

import com.feign.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String userRoleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
     *
     * @mbg.generated
     */
    int insert(UserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
     *
     * @mbg.generated
     */
    int insertSelective(UserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
     *
     * @mbg.generated
     */
    UserRole selectByPrimaryKey(String userRoleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_role
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(UserRole record);
}