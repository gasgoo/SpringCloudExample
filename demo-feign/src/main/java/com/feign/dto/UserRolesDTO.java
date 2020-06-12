package com.feign.dto;

import lombok.Data;

import java.util.List;

/**
 * @Date 2020/6/12 10:54
 * @name UserRolesDTO
 */

@Data
public class UserRolesDTO {

    private String userId;

    /**
     * 角色名称
     */
    private List<String> roleNames;
}
