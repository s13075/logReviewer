package com.pjatkInz.logReviewer.repository;

import com.pjatkInz.logReviewer.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<MyUser, UUID> {

    MyUser findByEmail(String email);
}
