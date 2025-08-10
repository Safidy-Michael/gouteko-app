package com.project.gouteko.service;

import com.project.gouteko.DTO.ProductDTO;
import com.project.gouteko.DTO.UserDTO;
import com.project.gouteko.controller.mapper.ProductMapper;
import com.project.gouteko.controller.mapper.UserMapper;
import com.project.gouteko.model.Product;
import com.project.gouteko.model.User;
import com.project.gouteko.repository.UserRepository;
import com.project.gouteko.utils.PageableUtils;
import com.project.gouteko.utils.PaginationRequest;
import com.project.gouteko.utils.PaginationUtils;
import com.project.gouteko.utils.PagingResult;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public PagingResult<UserDTO> getAll(PaginationRequest request) {

        final Pageable pageable = PaginationUtils.getPageable(request);

        final Page<User> users = userRepository.findAll(pageable);

        final Page<UserDTO> userDTOS = users.map(UserMapper::toView);

        return new PagingResult<>(userDTOS);
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
