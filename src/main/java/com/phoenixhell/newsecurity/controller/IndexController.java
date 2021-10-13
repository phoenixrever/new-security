package com.phoenixhell.newsecurity.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author phoenixhell
 */

@Controller
public class IndexController {

    @PreAuthorize("hasAnyAuthority('p1')")
    @GetMapping({"/","/index"})
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()){
            return null;
        }
        Object principal = authentication.getPrincipal();
        if(principal instanceof org.springframework.security.core.userdetails.User){
            //密码已经被security 清空
            User user= (User) principal;
            model.addAttribute("username", user.getUsername());
        }
        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    //@Secured({"ROLE_customer","ROLE_admin"})
    //@PreAuthorize("hasAnyAuthority('p10')")
    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }


    /**
     * Secured 需要 加上 ROLE_
     * 下面分配角色的需要手动加ROLE_
     *  AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin");
     *  -----------------------------------------
     *  Secured({"ROLE_customer","ROLE_admin"})
     *  效果等同
     *  PreAuthorize("hasAnyRole('admin,customer')")
     *
     * userDetails 不同同时分配角色和权限 角色会失效
     * .roles("admin").authorities()
     *
     * URL路径拦截（先）和方法拦截（后）按先后顺序共同作用
     * 任何一个不匹配都会拦截
     */
    @Secured({"ROLE_customer","ROLE_admin"})
    @GetMapping("/role")
    public String role(){
        return "security admin";
    }

    //和Secured 效果相同 适合单个permission
    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/preAuthorize")
    public String preAuthorize(){
        return "security PreAuthorize";
    }

    /**
     * 方法先执行在校验  没有权限也会先执行方法
     * 适合验证带有返回值的权限
     */

    @PostAuthorize("hasAnyAuthority('postAuthorize')")
    @GetMapping("/postAuthorize")
    public String postAuthorize(){
        return "security PostAuthorize";
    }

    /**
     * 权限验证过后对返回数据进行过滤吗
     * 对返回的list进行过滤只返回username=admin1的
     * @return
     */

    @PostAuthorize("hasAnyAuthority('postfilter')")
    @PostFilter("filterObject.username=='admin1'")
    @GetMapping("/postfilter")
    public ArrayList<com.phoenixhell.newsecurity.entity.User> postfilter(){
        ArrayList<com.phoenixhell.newsecurity.entity.User> list = new ArrayList<>();
        list.add(new com.phoenixhell.newsecurity.entity.User("admin1","password1"));
        list.add(new com.phoenixhell.newsecurity.entity.User("admin2","password2"));
        return list;
    }

    /**
     * 权限验证过后进入控制器之前对参数进行过滤吗
     * 传参数直传满足过滤条件的
     */

    @PreAuthorize("hasAnyAuthority('prefilter')")
    @PreFilter("filterObject.id%2==0")
    @GetMapping("/prefilter")
    public List<com.phoenixhell.newsecurity.entity.User> preFilter(@RequestBody List<com.phoenixhell.newsecurity.entity.User> list){
        list.forEach(System.out::println);
        return list;
    }
}
