package me.seungwoo.service.user;

import lombok.RequiredArgsConstructor;
import me.seungwoo.domain.user.User;
import me.seungwoo.dto.user.UserSignupRequest;
import me.seungwoo.repository.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 회원가입 처리 (비밀번호 검증 + 암호화 + 저장)
     */
    @Transactional
    public User registerUser(UserSignupRequest request) {
        // ✅ 1. 비밀번호 일치 확인
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // ✅ 2. ID 중복 체크
        if (userRepository.existsById(request.getId())) {
            throw new IllegalArgumentException("이미 존재하는 ID입니다.");
        }

        // ✅ 3. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // ✅ 4. User 엔티티 생성 (암호화된 비밀번호로 저장)
        User user = new User(
                request.getId(),
                encodedPassword,
                request.getName(),
                request.getEmail(),
                request.getPhone(),
                request.getField()
        );

        // ✅ 5. DB 저장
        return userRepository.save(user);
    }
}