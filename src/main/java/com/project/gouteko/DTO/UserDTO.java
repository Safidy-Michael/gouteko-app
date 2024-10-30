package com.project.gouteko.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    private MultipartFile image;
    private String imageBase64;
    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private String cin;
    private String status;
    private String geolocation;
}
