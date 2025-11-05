package me.seungwoo.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupRequestDTO {
    private String id; // 없에야함
    private String password;
    private String name;
    private String email;
    private String phone;
    private String field;
}