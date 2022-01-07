package com.example.board.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private Long id;
    private String userId; // User 의 userId를 가리키는 외래키임 User의 userId는 고유키이기 때문에 외래키로 연결 가능

    @NotBlank(message = "제목은 필수 입력값 입니다.")
    @Size(min = 1, max=30, message = "제목을 1 ~ 30자 사이로 입력해주세요")
    private String title;

    @Size(min = 0, max=300, message = "내용을 300자 이하로 입력해주세요")
    private String content;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

}