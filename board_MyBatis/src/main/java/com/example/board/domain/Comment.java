package com.example.board.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private Long id;
    private Long boardId;

    @NotBlank(message = "내용은 필수 입력값 입니다.")
    @Size(min = 1, max=300, message = "내용을 0자 ~ 300자로 입력해주세요")
    private String content;

    private String userId;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
