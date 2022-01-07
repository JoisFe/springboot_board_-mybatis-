package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.domain.Criteria;
import com.example.board.domain.Paging;
import com.example.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardMapper boardMapper;

    public int boardListCnt() {
        return boardMapper.boardListCnt();
    }

    public List<Map<String, Object>> boardList(Criteria cri) {
        return boardMapper.boardList(cri);
    }

    public Board getBoard(Long id) {
        return boardMapper.getBoard(id);
    }

    @Transactional
    public void uploadBoard(Board board) {
        boardMapper.uploadBoard(board);
    }

    @Transactional
    public void updateBoard(Board board) {
        boardMapper.updateBoard(board);
    }

    @Transactional
    public void deleteBoard(Long id) {
        boardMapper.deleteBoard(id);
    }

    public String validTest(BindingResult errors, UserService userService) {
        Map<String, String> validatorResult = userService.validateHandling(errors);
        for (String key : validatorResult.keySet()) {
            return validatorResult.get(key);
        }
        return ""; // 여기 올 수가 없는게 애초에 이 함수안에 들어왔다는 것은 for문 안에 하나는 걸린다는 것!!
    }

}
