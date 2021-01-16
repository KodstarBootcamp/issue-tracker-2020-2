package com.kodstar.issuetracker.service.impl;

import com.kodstar.issuetracker.entity.VerificationToken;
import com.kodstar.issuetracker.repo.ConfirmUserRepository;
import com.kodstar.issuetracker.service.ConfirmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmUserServiceImpl implements ConfirmUserService {


    @Autowired
    private ConfirmUserRepository confirmUserRepository;

    @Override
    public VerificationToken findByToken(String token) {
        return confirmUserRepository.findByToken(token);
    }

    @Override
    public VerificationToken save(VerificationToken verificationToken) {
        return confirmUserRepository.save(verificationToken);
    }

    @Override
    public void delete(VerificationToken verificationToken) {
        confirmUserRepository.delete(verificationToken);
    }
}
