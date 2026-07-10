package com.smartinventorymanagement.Auth_Service.Repository;

import com.smartinventorymanagement.Auth_Service.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Long> {

    Optional<User> findByUsername(String username);  // ✅ Correct
}
