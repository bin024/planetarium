package com.planetarium.planetarium.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planetarium.planetarium.models.User;

public interface UserDao extends JpaRepository<User, Integer>{

    Optional<User> getUserByUsername(String name);

    @Modifying
    @Transactional
    @Query(value = "insert into users values (default, :username, :password)", nativeQuery = true) 
    void createUser(@Param("username") String username, @Param("password") String password);
}
