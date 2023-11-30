package com.group1.QRPass.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@CrossOrigin("*")
public class HelloWorldController {

    @GetMapping
    public String getHello(){
        return "everything is OK if you are getting this response";
    }
}
