package com.thejobs.onlineappointmentschedulingwebsite.service;

import com.thejobs.onlineappointmentschedulingwebsite.dto.UserDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.UserSignInResponseDTO;
import com.thejobs.onlineappointmentschedulingwebsite.entity.AuthenticationTokenUser;
import com.thejobs.onlineappointmentschedulingwebsite.entity.User;
import com.thejobs.onlineappointmentschedulingwebsite.exceptions.AuthenticationFailException;
import com.thejobs.onlineappointmentschedulingwebsite.exceptions.CustomException;
import com.thejobs.onlineappointmentschedulingwebsite.repo.UserRepo;
import com.thejobs.onlineappointmentschedulingwebsite.repo.UserTokenRepo;
import com.thejobs.onlineappointmentschedulingwebsite.util.Helper;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserAuthenticationService userAuthenticationService;
    @Autowired
    UserTokenRepo userTokenRepo;
    @Autowired
    PasswordHasher passwordHasher;
    Logger logger = LoggerFactory.getLogger(ConsultantService.class);
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    public String signUpUser(UserDTO userDTO) {

//
        // Check to see if the current email address has already been registered.
        if (Helper.notNull(userRepo.findByEmail(userDTO.getEmail()))) {
            // If the email address has been registered then throw an exception.
            return VarList.RSP_DUPLICATED;
        }
        // first encrypt the password
        String encryptedPassword = userDTO.getPassword();
        try {
            encryptedPassword = passwordHasher.hashPassword(userDTO.getPassword()); // Use the PasswordHasher bean
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("Hashing password failed: {}", e.getMessage());
        }


        User user = new User(userDTO.getfName(), userDTO.getlName(), userDTO.getGender(), userDTO.getEmail(), userDTO.getContactNumber(), encryptedPassword);

        User createdUser;
        try {
            // save the User
            createdUser = userRepo.save(user);
            System.out.println(createdUser);
            // generate token for user
            final AuthenticationTokenUser authenticationTokenUser = new AuthenticationTokenUser(createdUser);
            // save token in database
            userAuthenticationService.saveConfirmationToken(authenticationTokenUser);
            // success in creating
            return VarList.RSP_SUCCESS;
        } catch (Exception e) {
            // handle signup error
            return VarList.RSP_SUCCESS;
        }
    }


    public List<UserDTO> getAllUsers() {
        List<User> usersList = userRepo.findAll();
        return modelMapper.map(usersList, new TypeToken<List<UserDTO>>() {
        }.getType());
    }

    public String updateUser(UserDTO userDTO) {
        if (userRepo.existsById(userDTO.getId())) {
            // Check if a new password is provided
            if (userDTO.getPassword() != null) {
                try {
                    String hashedPassword = passwordHasher.hashPassword(userDTO.getPassword());
                    userDTO.setPassword(hashedPassword); // Update the DTO with the hashed password
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    logger.error("Hashing password failed: {}", e.getMessage());
                    return VarList.RSP_ERROR; // Handle the hashing error as needed
                }
            }

            userRepo.save(modelMapper.map(userDTO, User.class));
            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }

    }

    public String deleteUser(String id) {

        Long userId = Long.valueOf(id);

        if (userRepo.existsById(userId)) {
            // First, delete the associated token
            userAuthenticationService.deleteTokenByUserId(userId);

            // Then, delete the user
            userRepo.deleteById(userId);

            return VarList.RSP_SUCCESS;
        } else {
            return VarList.RSP_NO_DATA_FOUND;
        }
    }


    public UserSignInResponseDTO signInUser(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String providedPassword = userDTO.getPassword();

        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new AuthenticationFailException("User not present");
        }

        try {
            String hashedProvidedPassword = passwordHasher.hashPassword(providedPassword);
            if (!hashedProvidedPassword.equals(user.getPassword())) {
                throw new AuthenticationFailException("Incorrect password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("Hashing password failed: {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }

        AuthenticationTokenUser token = userAuthenticationService.getUserToken(Optional.of(user));
        if (!Helper.notNull(token)) {
            throw new CustomException("Token not present");
        }

        return new UserSignInResponseDTO(token.getUserToken(), VarList.RSP_SUCCESS);
    }
}
