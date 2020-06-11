package com.feign.dao;

import com.feign.dto.PermisstionDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {

    /**
     * 返回所有角色资源
     *
     * @return
     */
    List<PermisstionDTO> findAll();
}