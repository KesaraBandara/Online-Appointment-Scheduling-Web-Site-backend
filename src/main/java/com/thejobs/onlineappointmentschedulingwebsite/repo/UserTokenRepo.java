package com.thejobs.onlineappointmentschedulingwebsite.repo;

import com.thejobs.onlineappointmentschedulingwebsite.entity.AuthenticationToken;
import com.thejobs.onlineappointmentschedulingwebsite.entity.AuthenticationTokenUser;
import com.thejobs.onlineappointmentschedulingwebsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepo extends JpaRepository<AuthenticationTokenUser, Integer> {

    AuthenticationTokenUser findTokenByUser(Optional<User> user);

    Optional<AuthenticationTokenUser> findByUserId(Long userId);

    AuthenticationTokenUser findTokenByUserToken(String token);
}
