package com.kodstar.issuetracker.repo;


import com.kodstar.issuetracker.auth.User;
import com.kodstar.issuetracker.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
