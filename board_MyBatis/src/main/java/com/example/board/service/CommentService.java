package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.domain.Comment;
import com.example.board.domain.Criteria;
import com.example.board.domain.User;
import com.example.board.mapper.BoardMapper;
import com.example.board.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentMapper commentMapper;

    public int commentListCnt(Long boardId) {
        return commentMapper.commentListCnt(boardId);
    }

    @Transactional
    public List<Map<String, Object>> commentList(Map<String, Object> params) {
        return commentMapper.commentList(params);
    }

    public HashMap<String, Object> commentListParameter(Long id, Criteria cri) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("boardId", id);
        params.put("pageStart", cri.getPageStart());
        params.put("perPageNum",cri.getPerPageNum());

        return params;
    }

    public Comment getComment(Long id) {
        return commentMapper.getComment(id);
    }


    @Transactional
    public void uploadComment(Comment comment) {
        commentMapper.uploadComment(comment);
    }

    @Transactional
    public void updateComment(Comment comment) {
        commentMapper.updateComment(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentMapper.deleteComment(id);
    }

}
