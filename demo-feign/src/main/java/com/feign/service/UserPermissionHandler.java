package com.feign.service;

import com.feign.dao.PermissionMapper;
import com.feign.dto.PermisstionDTO;
import com.feign.dto.PermisstionMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 判断是否具有权限访问当前资源
 * @Date 2020/6/11 17:18
 * @name UserPermissionHandler
 */

@Service
@Slf4j
public class UserPermissionHandler {

    @Autowired
    private PermissionMapper permissionMapper;


    /**
     * 判断是否有权限
     *
     * @param request
     * @param authentication
     * @return
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) throws Exception {
        Collection<ConfigAttribute> collection = getAttributes(request);
        if (authentication.getPrincipal().equals("anonymousUser")) {
            return false;
        }

        if (null == collection || collection.size() <= 0) {
            return true;
        }

        ConfigAttribute configAttribute;
        String needRole;
        for (Iterator<ConfigAttribute> iterator = collection.iterator(); iterator.hasNext(); ) {
            configAttribute = iterator.next();
            needRole = configAttribute.getAttribute();
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (needRole.trim().equals(grantedAuthority.getAuthority())) {
                    return true;
                }
            }
        }
       throw new Exception("没有权限");
    }

    /**
     * 判定用户请求的url是否在权限表中，如果在权限表中，则返回decide方法，
     * 用来判定用户是否有权限，如果不在权限表中则放行
     *
     * @param request
     * @return
     * @throws IllegalArgumentException
     */
    public Collection<ConfigAttribute> getAttributes(HttpServletRequest request) throws IllegalArgumentException {
        HashMap<String, Collection<ConfigAttribute>> map = PermisstionMap.map;
        if (map == null) {
            map = loadResourceDefine(map);
        }
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : map.entrySet()) {
            String url = entry.getKey();
            if (new AntPathRequestMatcher(url).matches(request)) {
                return map.get(url);
            }
        }
        return null;
    }

    /**
     * 加载权限表中所有权限
     */
    private HashMap<String, Collection<ConfigAttribute>> loadResourceDefine(HashMap<String, Collection<ConfigAttribute>> map) {
        map = new HashMap<>();
        List<PermisstionDTO> all = permissionMapper.findAll();
        for (PermisstionDTO permissionDto : all) {
            List<ConfigAttribute> configAttributeList = permissionDto.getRoleNames().stream().map(roleName -> {
                ConfigAttribute configAttribute = new SecurityConfig("ROLE_" + roleName.toUpperCase());
                return configAttribute;
            }).collect(Collectors.toList());
            map.put(permissionDto.getPermissionUrl(), configAttributeList);
        }
        PermisstionMap.map = map;
        return map;
    }

}
