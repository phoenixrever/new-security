package com.phoenixhell.newsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author phoenixhell
 * @since 2021/10/9 0009-上午 9:19
 */

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //创建一个用户赋予admin角色 表单输入的和这个用户相同(不加密密码情况下)就可以登录
        auth.inMemoryAuthentication().withUser("admin").password("123").roles("admin");
    }

    //@Override
    //protected UserDetailsService userDetailsService() {
    //    InMemoryUserDetailsManager detailsManager = new InMemoryUserDetailsManager();
    //    detailsManager.createUser(User.withUsername("admin").password("123").authorities("add").build());
    //    return detailsManager;
    //}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //允许表单登录
                .formLogin()
                     //loginPage 指定登录页面会覆盖默认登陆页面
                    //.loginPage("/loginPage")//用户未登陆时候,访问任何资源都跳转到该路径,登陆静态页面
                    //.loginProcessingUrl("/login")///登陆表单form里面 action处理表单提交地址 spring 提供 我们controller不需要提供
                    .defaultSuccessUrl("/index")//登陆成功跳转路径
                    .usernameParameter("username")//form表单中input的name名字 不改的话默认是username
                    .passwordParameter("password")//form表单中input的name名字 不改的话默认是password
                .and()
                //路径拦截
                .authorizeRequests()//需要登陆路径request
                    .antMatchers("/loginPage", "/login").permitAll()//不需要登陆验证就可以访问的路径 permitAll 放行
                    .antMatchers("/index").hasAnyRole("admin")//特别指出index需要认证并且需要admin权限才能访问
                    .anyRequest().authenticated()//其他所有路径都需要认证
                .and()
                .csrf().disable();//关闭crsf跨域攻击
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
