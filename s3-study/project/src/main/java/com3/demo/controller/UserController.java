package com3.demo.controller;

import com3.demo.domain.User;
import com3.demo.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Getter
    public static class LoginRequestParam {
        private String name;
        private String password;
    }

    @PostMapping(value= "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> login(@RequestBody LoginRequestParam loginRequestParam,
                                      HttpSession httpSession) {
        User findUser = userService.findByName(loginRequestParam.name);

        if (findUser == null || !findUser.matchPassword(loginRequestParam.password)) {
            return ResponseEntity.notFound().build();
        }

        httpSession.setAttribute("userIdx", findUser.getIdx());
        return ResponseEntity.ok(findUser.getIdx());
    }
}
