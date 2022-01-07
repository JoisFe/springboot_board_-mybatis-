package com.example.board.mapper;

import com.example.board.domain.Board;
import com.example.board.domain.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("BoardMapper")
public interface BoardMapper {
    List<Board> getList();

    Board getBoard(Long id);

    void uploadBoard(Board board);

    void updateBoard(Board board);

    void deleteBoard(Long id);

    List<Map<String, Object>> boardList(Criteria cri);

    int boardListCnt();

}