package com.team10.whatis.test;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping("/hello")
    public String helloWorld(){
        return "CICD 동작확인5 오후 2시 43분 번째";
    }

}
