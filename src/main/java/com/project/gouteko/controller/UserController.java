package com.project.gouteko.controller;

import com.project.gouteko.DTO.UserDTO;
import com.project.gouteko.controller.mapper.UserMapper;
import com.project.gouteko.model.User;
import com.project.gouteko.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public ResponseEntity<UserDTO> newUser(@RequestParam("user") UserDTO userDTO) throws Exception {
        User newUser = UserMapper.toDomain(userDTO);
        User createdUser = userService.create(newUser);
        return new ResponseEntity<>(userMapper.toView(createdUser), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestParam("user") UserDTO userDTO) {
        try {
            User updatedUser = UserMapper.toDomain(userDTO);
            userService.updateUser(id, updatedUser);
            return new ResponseEntity<>(userMapper.toView(updatedUser), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
