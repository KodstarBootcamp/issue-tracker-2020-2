package com.kodstar.issuetracker.repo;

import com.kodstar.issuetracker.auth.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

     User findByUsername(String username);

}