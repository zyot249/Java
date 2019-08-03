package com.zyot.shyn.servicereleasemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebDemoController {
    @GetMapping("/release/add-form")
    public String releaseAddForm(){
        return "add-release-form";
    }

    @GetMapping("/release/home")
    public String toHome(){
        return "home";
    }

    @GetMapping("/service/add-form")
    public String serviceAddForm(){
        return "add-service-form";
    }
}
