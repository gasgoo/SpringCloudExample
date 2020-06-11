package com.feign.dto;

import lombok.Data;

import java.util.List;

/**
 * 角色资源 哪些角色有当前资源
 * @Date 2020/6/11 17:21
 * @name PermisstionDTO
 */


@Data
public class PermisstionDTO {

    /**
     * url
     */
    private String permissionUrl;

    /**
     * 角色名称
     */
    private List<String> roleNames;
}