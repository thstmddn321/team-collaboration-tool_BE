package me.seungwoo.controller.user;

import lombok.RequiredArgsConstructor;
import me.seungwoo.domain.user.User;
import me.seungwoo.dto.user.UserSignupRequestDTO;
import me.seungwoo.service.user.UserService;
import me.seungwoo.config.jwt.JwtTokenProvider; // ✅ JWT 유틸 import 추가
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // ✅ 추가
import org.springframework.web.bind.annotation.*;

import java.util.Map; // ✅ Map import 추가

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder; // ✅ 암호화기 주입
    private final JwtTokenProvider jwtTokenProvider;     // ✅ JWT 토큰 유틸 주입

    /**
     * 회원가입 (비밀번호 암호화 + 검증)
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupRequestDTO request) {
        User savedUser = userService.registerUser(request);
        return ResponseEntity.ok("회원가입 성공 ✅ (비밀번호 암호화 완료)\nEmail: " + savedUser.getEmail());
    }

    /**
     * 로그인 (JWT 토큰 발급)
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        User user = userService.findByEmail(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());
        return ResponseEntity.ok(Map.of("token", token));
    }
}