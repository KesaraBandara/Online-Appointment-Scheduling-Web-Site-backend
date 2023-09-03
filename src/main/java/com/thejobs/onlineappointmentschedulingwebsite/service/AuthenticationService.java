package com.thejobs.onlineappointmentschedulingwebsite.service;


import com.thejobs.onlineappointmentschedulingwebsite.entity.AuthenticationToken;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Consultant;
import com.thejobs.onlineappointmentschedulingwebsite.exceptions.AuthenticationFailException;
import com.thejobs.onlineappointmentschedulingwebsite.repo.TokenRepo;
import com.thejobs.onlineappointmentschedulingwebsite.util.Helper;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepo tokenRepo;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepo.save(authenticationToken);
    }

    public AuthenticationToken getToken(Optional<Consultant> consultant) {
        return tokenRepo.findTokenByConsultant(consultant);
    }

    public Consultant getConsultant(String token) {
        AuthenticationToken authenticationToken = tokenRepo.findTokenByToken(token);
        if (Helper.notNull(authenticationToken)) {
            if (Helper.notNull(authenticationToken.getConsultant())) {
                return authenticationToken.getConsultant();
            }
        }
        return null;
    }

    public void authenticate(String token) throws AuthenticationFailException {
        if (!Helper.notNull(token)) {
            throw new AuthenticationFailException(VarList.AUTH_TOEKN_NOT_PRESENT);
        }
        if (!Helper.notNull(getConsultant(token))) {
            throw new AuthenticationFailException(VarList.AUTH_TOEKN_NOT_VALID);
        }
    }

    public void deleteTokenByConsultantId(Long consultantId) {
        // Find the token associated with the consultant ID
        Optional<AuthenticationToken> tokenOptional = tokenRepo.findByConsultantId(consultantId);

        // Check if a token was found
        if (tokenOptional.isPresent()) {
            AuthenticationToken token = tokenOptional.get();
            tokenRepo.delete(token); // Delete the token
        }
    }
}