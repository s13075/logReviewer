package com.pjatkInz.logReviewer.repository;

import com.pjatkInz.logReviewer.model.EMyRole;
import com.pjatkInz.logReviewer.model.MyRole;
import com.pjatkInz.logReviewer.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<MyRole, UUID> {
    MyRole findByEnumRole(EMyRole rolename);

}
