package com.project.gouteko.service;

import com.project.gouteko.DTO.UserDTO;
import com.project.gouteko.controller.mapper.UserMapper;
import com.project.gouteko.model.User;
import com.project.gouteko.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.project.gouteko.controller.mapper.UserMapper.toDomain;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public List<User> getAll(){
        return userRepository.findAll();
    }
    public User createUser(UserDTO userDTO, MultipartFile imageFile) throws Exception {
        User user = toDomain(userDTO, imageFile);

        return userRepository.save(user);
    }




public User updateUser(UUID id, UserDTO userDTO, MultipartFile imageFile) throws Exception {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User updatedUser = toDomain(userDTO, imageFile);
        updatedUser.setId(existingUser.getId());

        return userRepository.save(updatedUser);
    }

    public void deleteUser(UUID id) {
        Optional<User> user1 = userRepository.findById(id);
        if (user1.isPresent()) {
            userRepository.deleteById(id);
        }
        else throw new RuntimeException("User not found with ID: ");
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

}
