package com.project.gouteko.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.gouteko.DTO.UserDTO;
import com.project.gouteko.controller.mapper.UserMapper;
import com.project.gouteko.model.User;
import com.project.gouteko.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
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
    public List<UserDTO> findAll() {
        List<User> users = userService.getAll();
        return users.stream()
                .map(userMapper::toView)
                .toList();
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




    @PostMapping("/create")
    public ResponseEntity<User> createUser(@ModelAttribute UserDTO userDTO, @RequestParam("image") MultipartFile imageFile) {
        try {
            User createdUser = userService.createUser(userDTO, imageFile);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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
