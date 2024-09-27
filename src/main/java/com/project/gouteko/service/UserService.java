package com.project.gouteko.service;

import com.project.gouteko.model.User;
import com.project.gouteko.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public List<User> getAll(){
        return userRepository.findAll();
    }
    public User create(User user){
      return  userRepository.save(user);
    }

    public User updateUser(UUID id, User updateUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {

            User user = existingUser.get();
            user.setFirstName(updateUser.getFirstName());
            user.setLastName(updateUser.getLastName());
            user.setEmail(updateUser.getEmail());
            user.setAddress(updateUser.getAddress());
            user.setPhoneNumber(updateUser.getPhoneNumber());

            return userRepository.save(user);
        }
        else throw new RuntimeException("User id not found" + id);
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
