package com.example.board.controller;

import com.example.board.domain.User;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/register")
    public String registerForm() {
        return "/user/register";
    }


    @ResponseBody
    @PostMapping("/register")
    public String register(Model model, @Valid User user, BindingResult errors, String samePassword) { // @Valid User 다음 바로 BindingResult 나와야함 다른 매개변수가 중간에 있을시 에러나도 잡지를 못함

        try {
            return service.registerValidTest(model, user, errors, samePassword);
        } catch (Exception e) {
            System.out.println(e);
            return "에러가 발생하였습니다.";
        }
    }


    @GetMapping("/")
    public String loginForm() {
        return "user/login";
    }

    @ResponseBody
    @PostMapping("/login")
    public String login(User user, HttpServletRequest req) {

        System.out.println(user.getUserId());
        System.out.println(user.getPassword());
        System.out.println(user.getId());

        try {
            if (service.login(user) != 1) return "회원 정보가 맞지 않습니다.";
            else {
                HttpSession session = req.getSession();
                session.setAttribute("id", user.getUserId());

                return "success";
            }

        } catch (Exception e) {
            return "에러가 발생하였습니다.";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession();

        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/memberDelete")
    public String memberDelete(Model model, HttpServletRequest req) {
        String id = service.findSessionId(req);

        model.addAttribute("id", id);

        return "/user/memberDelete";
    }

    @PostMapping("/memberDelete")
    @ResponseBody
    public String memberDelete(User user) {
        try {
            if (service.login(user) == 1) {
                service.memberDelete(user);

                return "success";
            } else return "Password가 틀렸습니다.";
        } catch (Exception e) {
            return "에러가 발생하였습니다.";
        }
    }

}
