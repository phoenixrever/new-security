package com.phoenixhell.newsecurity.service.impl;

import com.phoenixhell.newsecurity.entity.RolePermission;
import com.phoenixhell.newsecurity.entity.User;
import com.phoenixhell.newsecurity.service.PermissionService;
import com.phoenixhell.newsecurity.service.RolePermissionService;
import com.phoenixhell.newsecurity.service.UserRoleService;
import com.phoenixhell.newsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/10/10 0010-上午 9:47
 */

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //不存在的由spring security DaoAuthenticationProvider 自动抛出异常
        User user = userService.query().eq("username", username).one();

        //可以用security user 类生成一个 User
//        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).authorities("admin").build();

        List<String> stringAuthorities = userService.getStringAuthorities(username);
        //无参数的toArray()有一个缺点，就是转换后的数组类型是Object[]
        //stringAuthorities.toArray(new String[stringAuthorities.size()]
        //如果指定的数组能容纳该 collection，则返回包含此 collection 元素的数组。否则，将根据指定数组的运行时类型和此 collection 的大小分配一个新数组
        // 这里给的参数的数组长度是0，因此就会返回包含此 collection 中所有元素的数组，并且返回数组的类型与指定数组的运行时类型相同。
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList(stringAuthorities.toArray(new String[0]));
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
        return userDetails;
    }
}
