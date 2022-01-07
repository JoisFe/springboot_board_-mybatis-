package com.example.board.domain;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    @NotBlank(message = "Id는 필수 입력값 입니다.")
    @Size(min = 4, max=15, message = "Id를 4 ~ 15자 사이로 입력해주세요")
    private String userId;

    @NotBlank(message = "Password는 필수 입력값 입니다.")
    @Size(min = 4, max=15, message = "Password를 4 ~ 15자 사이로 입력해주세요")
    private String password;
}
