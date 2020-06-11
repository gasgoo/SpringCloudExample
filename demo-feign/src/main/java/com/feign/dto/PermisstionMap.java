package com.feign.dto;

import lombok.Data;
import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 权限相关
 * @Date 2020/6/11 17:54
 * @name PermisstionMap
 */


@Data
public class PermisstionMap {

    public static HashMap<String, Collection<ConfigAttribute>> map;

    public static List<PermisstionDTO> list;
}