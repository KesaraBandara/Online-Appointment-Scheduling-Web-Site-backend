package com.thejobs.onlineappointmentschedulingwebsite.service;

import com.thejobs.onlineappointmentschedulingwebsite.entity.AuthenticationTokenUser;
import com.thejobs.onlineappointmentschedulingwebsite.entity.User;
import com.thejobs.onlineappointmentschedulingwebsite.exceptions.AuthenticationFailException;
import com.thejobs.onlineappointmentschedulingwebsite.repo.UserTokenRepo;
import com.thejobs.onlineappointmentschedulingwebsite.util.Helper;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserAuthenticationService {

    @Autowired
    UserTokenRepo userTokenRepo;


    public void saveConfirmationToken(AuthenticationTokenUser authenticationTokenUser) {
        userTokenRepo.save(authenticationTokenUser);
    }

    public AuthenticationTokenUser getUserToken(Optional<User> user) {
        return userTokenRepo.findTokenByUser(user);
    }

    public User getUser(String token) {
        AuthenticationTokenUser authenticationTokenUser = userTokenRepo.findTokenByUserToken(token);
        if (Helper.notNull(authenticationTokenUser)) {
            if (Helper.notNull(authenticationTokenUser.getUser())) {
                return authenticationTokenUser.getUser();
            }
        }
        return null;
    }

    public void authenticate(String token) throws AuthenticationFailException {
        if (!Helper.notNull(token)) {
            throw new AuthenticationFailException(VarList.AUTH_TOEKN_NOT_PRESENT);
        }
        if (!Helper.notNull(getUser(token))) {
            throw new AuthenticationFailException(VarList.AUTH_TOEKN_NOT_VALID);
        }
    }

    public void deleteTokenByUserId(Long userId) throws AuthenticationFailException{
        // Find the token associated with the consultant ID
        Optional<AuthenticationTokenUser> tokenOptional = userTokenRepo.findByUserId(userId);

        // Check if a token was found
        if (tokenOptional.isPresent()) {
            AuthenticationTokenUser token = tokenOptional.get();
            userTokenRepo.delete(token); // Delete the token
        }
    }

}
