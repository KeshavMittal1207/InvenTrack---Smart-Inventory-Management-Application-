package com.smartinventorymanagement.Auth_Service.Repository;

import com.smartinventorymanagement.Auth_Service.Model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User , Long> {

    Optional<User> findByUsername(String username);
}
