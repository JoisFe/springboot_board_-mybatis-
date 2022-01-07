package com.example.board.controller;

import com.example.board.domain.Board;

import com.example.board.domain.Criteria;
import com.example.board.domain.Paging;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/main")
    public String main(Model model, HttpServletRequest req) {
        String id = userService.findSessionId(req);
        model.addAttribute("id", id);

        return "boards/main";
    }

    @PostMapping("/main")
    @ResponseBody
    public Map<String, Object> main(Criteria cri, Model model, HttpServletRequest req) {
        String id = userService.findSessionId(req);
        model.addAttribute("id", id);

        int boardListCnt = boardService.boardListCnt();

        Paging paging = new Paging(cri, boardListCnt);

        List<Map<String, Object>> list = boardService.boardList(cri);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("list", list);
        map.put("paging", paging);

        return map;
    }

    @GetMapping("/view")
    public String viewBoard(Model model, Long id, HttpServletRequest req) {
        model.addAttribute("view", boardService.getBoard(id));

        String userId = userService.findSessionId(req);
        model.addAttribute("id", userId);

        return "/boards/view";
    }

    @PostMapping("/view")
    @ResponseBody
    public Map<String, Object> viewBoard(Criteria cri, Long id) {
        int commentListCnt = commentService.commentListCnt(id);

        Paging paging = new Paging(cri, commentListCnt);

        HashMap<String, Object> params = commentService.commentListParameter(id, cri);
        System.out.println(params);

        List<Map<String, Object>> list = commentService.commentList(params);
        System.out.println(list);


        Map<String, Object> map = new HashMap<String, Object>();

        map.put("list", list);
        map.put("paging", paging);

        return map;
    }

    @GetMapping("/upload")
    public String uploadBoardForm(Model model, HttpServletRequest req) {
        String id = userService.findSessionId(req);

        model.addAttribute("id", id);

        return "boards/upload";
    }

    private final Logger logger = LoggerFactory.getLogger(BoardController.class);

    @PostMapping("/upload")
    @ResponseBody
    public String uploadBoard(@Valid Board board, BindingResult errors, HttpServletRequest req) {
        logger.info("Hell world");
        if (!userService.boardWriterTest(board, req)) return "현재 로그인한 아이디와 작성자가 다릅니다.";

        if (errors.hasErrors()) {
            return boardService.validTest(errors, userService);
        }

        boardService.uploadBoard(board);

        return "success";
    }

    @PutMapping("/update")
    public String updateBoard(@Valid Board board, BindingResult errors, RedirectAttributes rttr, HttpServletRequest req) {
        if (!userService.boardWriterTest(board, req)) return "현재 로그인한 아이디와 작성자가 다릅니다.";

        if (errors.hasErrors()) {
            rttr.addFlashAttribute("message", boardService.validTest(errors, userService));

            return "redirect:/view?id=" + board.getId();

        }
        boardService.updateBoard(board);

        return "redirect:/main";
    }

    @DeleteMapping("/delete")
    public String deleteBoard(Long id, Board board, HttpServletRequest req) {
        System.out.println(board.getUserId());

        if (!userService.boardWriterTest(board, req)) return "현재 로그인한 아이디와 작성자가 다릅니다.";

        boardService.deleteBoard(id);

        return "redirect:/main";
    }
}
