package com.kodstar.issuetracker.repo;

import com.kodstar.issuetracker.auth.User;
import com.kodstar.issuetracker.entity.VerificationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConfirmUserRepository extends CrudRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);
    boolean findByUser(User user);
}