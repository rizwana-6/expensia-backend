
package com.expensia.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expensia.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}