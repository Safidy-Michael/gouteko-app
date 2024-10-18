package com.project.gouteko.repository;

import com.project.gouteko.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,UUID> {
    Optional<User> findByFirstName(String userName);

    Optional<User> findByEmail(String email);

}
