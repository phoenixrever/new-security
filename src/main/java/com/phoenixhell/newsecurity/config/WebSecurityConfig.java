package com.phoenixhell.newsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

/**
 * @author phoenixhell
 * @since 2021/10/9 0009-上午 9:19
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //    @Override
    //    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //        //创建一个用户赋予admin角色 表单输入的和这个用户相同(不加密密码情况下)就可以登录
    //        auth.inMemoryAuthentication().withUser("admin").password("123").roles("admin");
    //    }

    //@Override
    //protected UserDetailsService userDetailsService() {
    //    InMemoryUserDetailsManager detailsManager = new InMemoryUserDetailsManager();
    //    detailsManager.createUser(User.withUsername("admin").password("123").authorities("add").build());
    //    return detailsManager;
    //}

    @Autowired
    private DataSource dataSource;

    //rememberMe
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //自动建表 第一次运行需要
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement() //
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED); //默认创建会话
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/loginPage");
        http.exceptionHandling().accessDeniedPage("/accessDenied");

        http
                //允许表单登录
                .formLogin()
                //loginPage 指定登录页面会覆盖默认登陆页面
                //    .loginPage("/loginPage")//用户未登陆时候,访问任何资源都跳转到该路径,登陆静态页面
                //    .loginProcessingUrl("/login")///登陆表单form里面 action处理表单提交地址 spring 提供 我们controller不需要提供
                    .defaultSuccessUrl("/index")//登陆成功跳转路径
                    .usernameParameter("username")//form表单中input的name名字 不改的话默认是username
                    .passwordParameter("password")//form表单中input的name名字 不改的话默认是password
                .and()
                //URL路径拦截
                .authorizeRequests()//需要登陆路径request
                    .antMatchers("/loginPage", "/login", "/static/**").permitAll()//不需要登陆验证就可以访问的路径 permitAll 放行
                    .antMatchers("/index").hasAnyAuthority("p1")//特别指出index需要认证并且需要p1权限才能访问
                    .anyRequest().authenticated()//其他所有路径都需要认证
                .and()
                //开启rememberMe 指定token数据库  由JdbcTokenRepositoryImpl 创建
                .rememberMe()
                    .rememberMeParameter("rememberMe")//form表单中input的remember-me名字 不改的话默认是remember-me
                    .tokenRepository(persistentTokenRepository())//指定token表 插入token数据
                    .tokenValiditySeconds(60)//token 有效时长 单位为60秒
                .and()
                .csrf().disable();//关闭crsf跨域攻击

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
