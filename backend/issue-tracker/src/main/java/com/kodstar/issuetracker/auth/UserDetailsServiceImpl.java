package com.kodstar.issuetracker.auth;

import com.kodstar.issuetracker.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            try {
                User user = userRepository.findByUsername(username);
                if (user == null) {
                    throw new UsernameNotFoundException(
                            "No user found with username: " + username);
                }

                return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), emptyList());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

}