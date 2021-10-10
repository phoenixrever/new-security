package com.phoenixhell.newsecurity.service.impl;

import com.phoenixhell.newsecurity.entity.User;
import com.phoenixhell.newsecurity.mapper.UserMapper;
import com.phoenixhell.newsecurity.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author phoenixhell
 * @since 2021-10-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public List<String> getStringAuthorities(String username) {
        return baseMapper.getGrantedAuthorities(username);
    }
}
