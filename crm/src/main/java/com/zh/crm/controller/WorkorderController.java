package com.zh.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@Controller
public class WorkorderController {

    @GetMapping(value = "/workorder")
    public String toAddWorkorderPage(){
        return "view/workorder/workorder_add";
    }
}
