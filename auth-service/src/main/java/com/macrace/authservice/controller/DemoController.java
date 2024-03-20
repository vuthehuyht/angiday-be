package com.macrace.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/")
public class DemoController {
    @GetMapping(path = "/demo")
    public String demo() {
        return "API demo is running";
    }
}
