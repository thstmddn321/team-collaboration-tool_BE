package me.seungwoo.controller.user;

import lombok.RequiredArgsConstructor;
import me.seungwoo.domain.user.User;
import me.seungwoo.dto.user.UserSignupRequest;
import me.seungwoo.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 (비밀번호 암호화 + 검증)
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupRequest request) {
        User savedUser = userService.registerUser(request);
        return ResponseEntity.ok("회원가입 성공 ✅ (비밀번호 암호화 완료)\nID: " + savedUser.getId());
    }
}