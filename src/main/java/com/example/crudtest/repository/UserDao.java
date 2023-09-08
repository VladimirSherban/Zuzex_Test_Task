package com.example.crudtest.repository;

import com.example.crudtest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByNameAndDeletedStatusFalse(String userName);

    List<User> findByDeletedStatusFalse();
}
