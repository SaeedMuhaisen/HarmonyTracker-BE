package com.HarmonyTracker.CSR.Repositories;

import com.HarmonyTracker.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByOauthId(String oauthId);
}
