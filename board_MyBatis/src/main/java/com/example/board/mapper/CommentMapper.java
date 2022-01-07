package com.example.board.mapper;

import com.example.board.domain.Board;
import com.example.board.domain.Comment;
import com.example.board.domain.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("CommentMapper")
public interface CommentMapper {
    List<Comment> getCommentList(Long boardId);

    Comment getComment(Long id);

    void uploadComment(Comment comment);

    void updateComment(Comment comment);

    void deleteComment(Long id);

    List<Map<String, Object>> commentList(Map<String, Object> params);

    int commentListCnt(Long boardId);
}
