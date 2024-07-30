package com.example.demo.controller;

import com.example.demo.domain.LoginForm;
import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository memberRepository;

    @PostMapping("/membersLogin")
    public ResponseEntity<?> login(@RequestBody LoginForm form, HttpServletRequest request) {

        // 이메일로 사용자 검색 및 비밀번호 검증
        Member loginMember = memberRepository.findByEmail(form.getEmail())
                .filter(member -> BCrypt.checkpw(form.getPwd(), member.getPwd()))
                .orElse(null);

        if (loginMember == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("유효하지 않은 이메일 또는 비밀번호입니다."));
        }

        // 로그인 성공 처리
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return ResponseEntity.ok(new LoginResponse("로그인 성공", session.getId()));
    }

    @PostMapping("/membersLogout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(new LoginResponse("로그아웃 성공"));
    }
}
