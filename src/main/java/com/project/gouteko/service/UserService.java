package com.project.gouteko.service;

import com.project.gouteko.model.User;
import com.project.gouteko.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> getAll(){
        return userRepository.findAll();
    }
}
