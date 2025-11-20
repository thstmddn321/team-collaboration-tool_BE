package me.seungwoo.controller.user;

import lombok.RequiredArgsConstructor;
import me.seungwoo.domain.user.User;
import me.seungwoo.dto.user.*;
import me.seungwoo.service.user.UserService;
import me.seungwoo.config.jwt.JwtTokenProvider; // ✅ JWT 유틸 import 추가
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        try {
            User savedUser = userService.registerUser(request);
            return ResponseEntity.ok("회원가입 성공 \nEmail: " + savedUser.getEmail());
        } catch (IllegalArgumentException e) {
            // 이메일 중복 등의 예외 처리
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            // DB 문제 등 기타 예외 처리
            return ResponseEntity.status(500).body("회원가입 중 오류가 발생했습니다.");
        }
    }

    /**
     * 로그인 (JWT 토큰 발급)
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        String email = request.getEmail();
        String password = request.getPassword();

        User user = userService.findByEmail(email);
        // 🔹 존재하지 않는 이메일인 경우
        if (user == null) {
            return ResponseEntity.status(404).body("해당 이메일의 사용자가 존재하지 않습니다.");
        }

        // 🔹 탈퇴한 계정인지 확인
        if (user.getIsDeleted()) {
            return ResponseEntity.status(403).body("탈퇴한 계정입니다. 로그인할 수 없습니다.");
        }

        // 🔹 비밀번호 불일치
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());
        return ResponseEntity.ok(Map.of("token", token));
    }

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> getMyInfo(@AuthenticationPrincipal String userEmail) {
        User user = userService.findByEmail(userEmail);

        UserInfoResponse response = new UserInfoResponse(
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getField()
        );

        return ResponseEntity.ok(response);
    }
    // 🔹 프로필 수정
    @PatchMapping("/update")
    public ResponseEntity<String> updateUser(
            @AuthenticationPrincipal String userEmail,
            @RequestBody UserUpdateRequest request) {

        try {
            userService.updateUser(userEmail, request);
            return ResponseEntity.ok("회원 정보가 성공적으로 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            // 존재하지 않는 사용자 등 비즈니스 로직 예외 처리
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // 그 외 예기치 못한 오류 처리
            return ResponseEntity.internalServerError().body("서버 내부 오류가 발생했습니다.");
        }
    }
    // 비밀번호 변경
    @PatchMapping("/update/password")
    public ResponseEntity<String> updatePassword(
            @AuthenticationPrincipal String userEmail,
            @RequestBody UserPasswordUpdateRequest request) {
        try {
            userService.updatePassword(userEmail, request);
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal String userEmail) {
        try {
            userService.deleteUser(userEmail);
            return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("회원 탈퇴 중 오류가 발생했습니다.");
        }
    }
}