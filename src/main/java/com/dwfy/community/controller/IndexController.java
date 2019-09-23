package com.dwfy.community.controller;

import com.dwfy.community.dto.PageDTO;
import com.dwfy.community.dto.QuestionDTO;
import com.dwfy.community.mapper.QuestionMapper;
import com.dwfy.community.mapper.UserMapper;
import com.dwfy.community.model.Question;
import com.dwfy.community.model.User;
import com.dwfy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size){
        if (request.getCookies() != null){
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies){
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        PageDTO pagenation = questionService.list(page,size);
        model.addAttribute("pagenation",pagenation);
        return "index";

    }

}
