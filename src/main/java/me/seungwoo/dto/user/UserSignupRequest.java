package me.seungwoo.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupRequest {
    private String id;
    private String password;
    private String passwordConfirm;
    private String name;
    private String email;
    private String phone;
    private String field;
}