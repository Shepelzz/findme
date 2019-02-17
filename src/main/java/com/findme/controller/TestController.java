package com.findme.controller;

import com.findme.dao.PostDAO;
import com.findme.model.FilterPagePosts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping("content")
public class TestController {
    private PostDAO postDAO;

    @Autowired
    public TestController(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    @RequestMapping("")
    public String loadContent() throws Exception{
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

    @RequestMapping(path = "content3", method = RequestMethod.POST)
    public String getContent3(Model model, @ModelAttribute FilterPagePosts filter) throws Exception{
        model.addAttribute("userPagePostList", postDAO.getPostList("1"));
        model.addAttribute("currDate", new Date().toString());
        return "test_page :: content3";
    }
}
