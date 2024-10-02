package com.project.gouteko.controller.mapper;

import com.project.gouteko.DTO.UserDTO;
import com.project.gouteko.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

public class UserMapper {

        public static User toDomain(UserDTO userDTO) throws Exception {
            User domainUser = new User();
            domainUser.setFirstName(userDTO.getFirstName());
            domainUser.setLastName(userDTO.getLastName());
            domainUser.setEmail(userDTO.getEmail());

            MultipartFile imageFile = userDTO.getImage();
            if (imageFile != null && !imageFile.isEmpty()) {
                byte[] imageBytes = imageFile.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                domainUser.setImage("data:image/jpeg;base64," + base64Image);
            }

            return domainUser;
        }


        public static UserDTO toView(User user) {
            UserDTO userDTO = new UserDTO();
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());


            String base64Image = user.getImage();
            userDTO.setImageBase64(base64Image);

            return userDTO;
        }
}
