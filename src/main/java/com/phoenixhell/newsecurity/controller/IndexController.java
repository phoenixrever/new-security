package com.phoenixhell.newsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author phoenixhell
 * @since 2021/10/9 0009-上午 9:11
 */

@Controller
public class IndexController {
    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
