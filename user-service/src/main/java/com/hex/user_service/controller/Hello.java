package com.hex.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hello")
public class Hello {
    // create a rest endpoint that return "hello github-copilot"

    @GetMapping("/world")
            public String hello()
    {
        return "hello github-copilot";
    }














}
