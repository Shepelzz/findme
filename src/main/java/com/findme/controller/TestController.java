package com.findme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("content")
public class TestController {

    @RequestMapping("")
    public String loadContent() {
        return "test_page";
    }

    @RequestMapping("content1")
    public String getContent1(Model model) {
        model.addAttribute("text", "cococco 1 "+new Date().toString());
        return "test_page :: content1";
    }

    @RequestMapping("content2")
    public String getContent2(Model model) {
        model.addAttribute("text", "sdfsdfs 2 "+new Date().toString());
        return "test_page :: content2";
    }
}
