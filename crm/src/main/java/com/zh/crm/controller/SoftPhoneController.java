package com.zh.crm.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@Controller
public class SoftPhoneController {
    @GetMapping(value="/Callerid")
    public String toPage1(){
        return "view/common/Callerid";
    }

    @GetMapping(value="/dialPhone")
    public String toPage2(){
        return "view/common/dialPhone";
    }

    @GetMapping(value="/minS")
    public String toPage3(){
        return "view/common/minS";
    }

    @GetMapping(value="/Callerid1")
    public String toPage4(){
        return "view/common/Callerid1";
    }
}
