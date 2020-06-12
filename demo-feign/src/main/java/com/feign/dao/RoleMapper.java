package com.feign.dao;

import com.feign.dto.UserRolesDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface RoleMapper {

     UserRolesDTO getUserRoles(String userId);

}