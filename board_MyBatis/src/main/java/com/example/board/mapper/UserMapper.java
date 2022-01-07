package com.example.board.mapper;

import com.example.board.domain.User;
import org.springframework.stereotype.Repository;

@Repository("UserMapper")
public interface UserMapper {

    void register(User user);

    int login(User user);

    void memberDelete(User user);

    User findId(User user);

    int idCheck(User user);

}
