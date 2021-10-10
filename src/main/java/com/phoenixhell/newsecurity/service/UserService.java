package com.phoenixhell.newsecurity.service;

import com.phoenixhell.newsecurity.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author phoenixhell
 * @since 2021-10-10
 */
public interface UserService extends IService<User> {
   List<String> getStringAuthorities(String username);
}
