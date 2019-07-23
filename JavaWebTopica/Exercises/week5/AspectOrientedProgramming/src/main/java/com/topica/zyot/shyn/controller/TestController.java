package com.topica.zyot.shyn.controller;

import com.topica.zyot.shyn.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/print")
    public String print() {
        return testService.show("Shyn", 21);
    }
}
