package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/status")
    public String status(){
        return "ok";
    }

    @GetMapping("/temp")
    public String temperature(){
        return "17.2 C";
    }
}
