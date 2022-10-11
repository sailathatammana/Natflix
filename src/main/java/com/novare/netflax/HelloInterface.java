package com.novare.netflax;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HelloInterface extends JpaRepository<User, Long> {

User findByEmail(String email);
}
