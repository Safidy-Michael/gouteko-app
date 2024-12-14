package com.project.gouteko.controller;
import com.project.gouteko.DTO.UserDTO;
import com.project.gouteko.controller.mapper.UserMapper;
import com.project.gouteko.model.User;
import com.project.gouteko.service.UserService;
import com.project.gouteko.utils.PageableUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    @Autowired
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/")
    public Page<UserDTO> findAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        Pageable pageable = PageableUtils.createPageable(page, size);
        Page<User> users = userService.getAll(pageable);
        return users.map(userMapper::toView);
    }

    @GetMapping("/{id}")
        public ResponseEntity<UserDTO> findUserById(@PathVariable UUID id) {
            try {
                User user = userService.getUserById(id);
                return new ResponseEntity<>(userMapper.toView(user), HttpStatus.OK);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @ModelAttribute UserDTO userDTO, @RequestParam("image") MultipartFile imageFile) {
        try {
            User updatedUser = userService.updateUser(id, userDTO, imageFile);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteUser(@PathVariable UUID id){
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }       

}
