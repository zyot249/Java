package com.topica.zyot.shyn.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {
    public String show(String name, int age) {
        return new StringBuilder()
                .append("hello ")
                .append(name)
                .append(" age = ")
                .append(age).toString();
    }
}
