package com.phoenixhell.newsecurity.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author phoenixhell
 * @since 2021/10/9 0009-上午 9:11
 */

@Controller
public class IndexController {
    @GetMapping("/index")
    public String index(Model model) {
        //org.springframework.security.core.userdetails.User
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("username",user.getUsername());
        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
