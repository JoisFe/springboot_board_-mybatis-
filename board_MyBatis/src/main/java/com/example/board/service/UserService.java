package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.domain.Comment;
import com.example.board.domain.User;
import com.example.board.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public void register(User user) {
        userMapper.register(user);
    }

    public int login(User user) {
        return userMapper.login(user);
    }

    public void memberDelete(User user) {
        userMapper.memberDelete(user);
    }

    public User findId(User user) {
        return userMapper.findId(user);
    }

    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    public int idCheck(User user) {
        return userMapper.idCheck(user);
    }


    public String findSessionId(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String sessionId = (String)session.getAttribute("id");

        return sessionId;
    }

    public String registerValidTest(Model model, @Valid User user, BindingResult errors, String samePassword) {
        if (errors.hasErrors()) {
            Map<String, String> validatorResult = validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));

                return validatorResult.get(key);
            }
        }

        if (idCheck(user) == 1) return "중복된 아이디가 존재합니다.";

        if (!user.getPassword().equals(samePassword)) return "두 비밀번호가 다릅니다";

        register(user);

        return "success";
    }

    public boolean boardWriterTest(Board board, HttpServletRequest req) {
        String sessionId = findSessionId(req);
        return board.getUserId().equals(sessionId);
    }

    public boolean commentWriterTest(Comment comment, HttpServletRequest req) {
        String sessionId = findSessionId(req);
        return comment.getUserId().equals(sessionId);
    }
}
