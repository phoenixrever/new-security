package com.phoenixhell.newsecurity.mapper;

import com.phoenixhell.newsecurity.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author phoenixhell
 * @since 2021-10-10
 */
public interface UserMapper extends BaseMapper<User> {
    List<String> getGrantedAuthorities(@Param("username") String username);
}
