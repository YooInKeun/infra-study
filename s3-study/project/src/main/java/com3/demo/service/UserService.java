package com3.demo.service;

import com3.demo.domain.User;
import com3.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByName(String name) {
        return userRepository.findByName(name);
    }
}
