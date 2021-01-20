package com.kodstar.issuetracker.service;


import com.kodstar.issuetracker.entity.VerificationToken;

public interface ConfirmUserService {
    VerificationToken findByToken(String token);
    VerificationToken save(VerificationToken verificationToken);
    void delete(VerificationToken verificationToken);
}
