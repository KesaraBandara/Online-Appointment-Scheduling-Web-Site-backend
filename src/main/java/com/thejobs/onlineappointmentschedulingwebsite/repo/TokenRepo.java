package com.thejobs.onlineappointmentschedulingwebsite.repo;
import com.thejobs.onlineappointmentschedulingwebsite.entity.AuthenticationToken;
import com.thejobs.onlineappointmentschedulingwebsite.entity.AuthenticationTokenUser;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Consultant;
import com.thejobs.onlineappointmentschedulingwebsite.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<AuthenticationToken, Integer> {
    AuthenticationToken findTokenByConsultant(Optional<Consultant> consultant );
    AuthenticationToken findTokenByToken(String token);

    Optional<AuthenticationToken> findByConsultantId(Long consultantId);

}