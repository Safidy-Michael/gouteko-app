package com.project.gouteko.controller.mapper;

import com.project.gouteko.DTO.UserDTO;
import com.project.gouteko.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
@Component
public class UserMapper {

    public static User toDomain(UserDTO userDTO, MultipartFile imageFile) throws Exception {
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email must not be null or empty");
        }
        User domainUser = new User();
        domainUser.setId(userDTO.getId());
        domainUser.setFirstName(userDTO.getFirstName());
        domainUser.setLastName(userDTO.getLastName());
        domainUser.setEmail(userDTO.getEmail());
        domainUser.setPassword(userDTO.getPassword());
        domainUser.setPhoneNumber(userDTO.getPhoneNumber());
        domainUser.setAddress(userDTO.getAddress());

        if (imageFile != null && !imageFile.isEmpty()) {
            byte[] imageBytes = imageFile.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            domainUser.setImage("data:image/jpeg;base64," + base64Image);
        } else {
            domainUser.setImage(userDTO.getImageBase64());
        }

        return domainUser;
    }



    public UserDTO toView(User user) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPhoneNumber(user.getPhoneNumber());
            userDTO.setAddress(user.getAddress());
            userDTO.setPassword(user.getPassword());
            String base64Image = user.getImage();
            userDTO.setImageBase64(base64Image);

            return userDTO;
        }
}
