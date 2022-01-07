package com.example.board.controller;

import com.example.board.domain.Comment;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final BoardService boardService;

    @PostMapping("comment")
    @ResponseBody
    public String uploadComment(@Valid Comment comment, BindingResult errors, HttpServletRequest req) {
        if (!userService.commentWriterTest(comment, req)) return "현재 로그인한 아이디와 작성자가 다릅니다.";

        if (errors.hasErrors()) {
            return boardService.validTest(errors, userService);
        }

        commentService.uploadComment(comment);

        return "success";
    }

    @GetMapping("/commentView")
    public String viewComment(Model model, Long id, HttpServletRequest req) {
        model.addAttribute("list", commentService.getComment(id));

        String userId = userService.findSessionId(req);
        model.addAttribute("id", userId);

        return "boards/commentView";
    }

    @PutMapping("/updateComment")
    public String updateComment(@Valid Comment comment, BindingResult errors, RedirectAttributes rttr, HttpServletRequest req) {
        if (!userService.commentWriterTest(comment, req)) return "현재 로그인한 아이디와 작성자가 다릅니다.";

        if (errors.hasErrors()) {
            rttr.addFlashAttribute("message", boardService.validTest(errors, userService));

            return "redirect:/commentView?id=" + comment.getId();
        }

        commentService.updateComment(comment);

        return "redirect:/view?id=" + comment.getBoardId();
    }

    @DeleteMapping("/deleteComment")
    public String deleteComment(Long id, Comment comment, HttpServletRequest req) {
        if (!userService.commentWriterTest(comment, req)) return "현재 로그인한 아이디와 작성자가 다릅니다.";

        commentService.deleteComment(id);

        return "redirect:/view?id=" + comment.getBoardId();

    }
}
