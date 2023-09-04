package com.thejobs.onlineappointmentschedulingwebsite.repo;

import com.thejobs.onlineappointmentschedulingwebsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
