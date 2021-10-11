package com.phoenixhell.newsecurity.service.impl;

import com.phoenixhell.newsecurity.entity.User;
import com.phoenixhell.newsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

        List<String> stringAuthorities = userService.getStringAuthorities(username);

        //可以用security user 类生成一个 User
        //无参数的toArray()有一个缺点，就是转换后的数组类型是Object[]
        //stringAuthorities.toArray(new String[stringAuthorities.size()]
        //0会返回包含此 collection 中所有元素的数组
        //    * userDetails 不同同时分配角色和权限 角色会失效
        //     * .roles("admin").authorities()
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).authorities(stringAuthorities.toArray(new String[0])).build();

        //另外一种建立user方式
        //List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList(stringAuthorities.toArray(new String[0]));
        //org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
        return userDetails;
    }
}
