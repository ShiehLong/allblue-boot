package com.allblue.security;

import com.allblue.model.po.BlueRole;
import com.allblue.model.po.BlueUser;
import com.allblue.service.BlueRoleService;
import com.allblue.service.BlueUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlueUserDetailsService implements UserDetailsService {

    @Autowired
    private BlueUserService blueUserService;

    @Autowired
    private BlueRoleService blueRoleService;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String name)
            throws UsernameNotFoundException {
        BlueUser blueUser = blueUserService.getUserInfo(name);
        if (blueUser == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return new org.springframework.security.core.userdetails.User(blueUser.getName(), blueUser.getPassword(), getGrantedAuthorities(blueUser));
    }


    private List<GrantedAuthority> getGrantedAuthorities(BlueUser blueUser) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        List<BlueRole> list = blueRoleService.getRoleListByUserName(blueUser.getName());

        for (BlueRole blueRole : list) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + blueRole.getCode()));
        }
        System.out.println("用户【" + blueUser.getName() + "】拥有角色权限 :" + authorities);
        return authorities;
    }

}