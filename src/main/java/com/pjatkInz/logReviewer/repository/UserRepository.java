package com.pjatkInz.logReviewer.repository;

import com.pjatkInz.logReviewer.model.Application;
import com.pjatkInz.logReviewer.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<MyUser, UUID> {

    List<MyUser> findAll();
    MyUser findByEmail(String email);
    MyUser findByEmploeeId(String emploeeId);
    List<MyUser> findByEmploeeIdStartsWith(String emploeeIdStartLetters);
}
