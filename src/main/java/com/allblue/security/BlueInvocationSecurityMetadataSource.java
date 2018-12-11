package com.allblue.security;

/**
 * @Description: loadResourceDefine方法不是必须的，这个只是加载所有的资源与权限的对应关系并缓存起来，
 * 避免每次获取权限都访问数据库（提高性能），然后getAttributes根据参数（被拦截url）返回权限集合
 * @Author Xone
 * @Date 17:02 2018/12/1
 **/

import com.allblue.model.vo.SystemRoleVO;
import com.allblue.service.BlueRoleService;
import com.allblue.utils.AntUrlPathMatcher;
import com.allblue.utils.UrlMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlueInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private UrlMatcher urlMatcher = new AntUrlPathMatcher();

    @Autowired
    private BlueRoleService blueRoleService;

    //参数是要访问的url，返回这个url对于的所有权限（或角色）
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 将参数转为url
        String url = ((FilterInvocation) object).getRequestUrl();
        //查询所有的url和角色的对应关系
        List<SystemRoleVO> rus = blueRoleService.getSystemRoleList();

        //匹配所有的url，并对角色去重
        Set<String> roles = new HashSet<String>();
        for (SystemRoleVO ru : rus) {
            if (urlMatcher.pathMatchesUrl(ru.getUrl(), url)) {
                roles.add(ru.getRoleCode());
            }
        }
        Collection<ConfigAttribute> cas = new ArrayList<ConfigAttribute>();
        for (String role : roles) {
            ConfigAttribute ca = new SecurityConfig("ROLE_" + role);
            cas.add(ca);
        }
        System.out.println("url：" + url + " 权限:" + cas);
        return cas;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
}