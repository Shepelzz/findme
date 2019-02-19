package com.findme.controller;

import com.findme.dao.PostDAO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("content")
public class TestController {
    private PostDAO postDAO;
    private List<Item> list= new ArrayList<>();

    @Autowired
    public TestController(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    @PostConstruct
    void init() {
        list.add(Item.builder().num(1).clarification(1).text("rrr").build());
        list.add(Item.builder().num(2).clarification(1).text("wwww").build());
        list.add(Item.builder().num(3).clarification(1).text("ggg").build());
        list.add(Item.builder().num(4).clarification(1).text("uuu").build());
        list.add(Item.builder().num(5).clarification(2).text("666").build());
        list.add(Item.builder().num(6).clarification(2).text("vvvv").build());
        list.add(Item.builder().num(7).clarification(2).text("pppp").build());
        list.add(Item.builder().num(8).clarification(2).text(",,,,").build());
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

}

@Builder @Setter @Getter
class Filter {
    private boolean first;
    private boolean second;
}

@Builder @Setter @Getter
class Item {
    private int num;
    private String text;
    private int clarification;
}