package com.thejobs.onlineappointmentschedulingwebsite.service;


import com.thejobs.onlineappointmentschedulingwebsite.dto.ConsultantDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.ConsultantSignInResponseDTO;
import com.thejobs.onlineappointmentschedulingwebsite.entity.AuthenticationToken;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Consultant;
import com.thejobs.onlineappointmentschedulingwebsite.exceptions.AuthenticationFailException;
import com.thejobs.onlineappointmentschedulingwebsite.exceptions.CustomException;
import com.thejobs.onlineappointmentschedulingwebsite.repo.ConsultantRepo;
import com.thejobs.onlineappointmentschedulingwebsite.util.Helper;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
//import java.util.logging.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@Transactional
public class ConsultantService {

    @Autowired
    private ConsultantRepo consultantRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    AuthenticationService authenticationService;

    Logger logger = LoggerFactory.getLogger(ConsultantService.class);

    public String saveConsultant(ConsultantDTO consultantDTO) {

//
        // Check to see if the current email address has already been registered.
        if (Helper.notNull(consultantRepo.findByEmail(consultantDTO.getEmail()))) {
            // If the email address has been registered then throw an exception.
            return VarList.RSP_DUPLICATED;
        }
        // first encrypt the password
        String encryptedPassword = consultantDTO.getPassword();
        try {
            encryptedPassword = hashPassword(consultantDTO.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("hashing password failed {}", e.getMessage());
        }


        Consultant consultant = new Consultant(consultantDTO.getfName(), consultantDTO.getlName(),consultantDTO.getGender() ,consultantDTO.getEmail(),consultantDTO.getContactNumber(),encryptedPassword  );

        Consultant createdConsultant;
        try {
            // save the User
            createdConsultant = consultantRepo.save(consultant);
            System.out.println(createdConsultant);
            // generate token for user
            final AuthenticationToken authenticationToken = new AuthenticationToken(createdConsultant);
            // save token in database
            authenticationService.saveConfirmationToken(authenticationToken);
            // success in creating
            return VarList.RSP_SUCCESS;
        } catch (Exception e) {
            // handle signup error
            return VarList.RSP_ERROR;
        }

    }

    public List<ConsultantDTO> getAllConsultants() {
        List<Consultant> consultantsList = consultantRepo.findAll();
        return modelMapper.map(consultantsList, new TypeToken<List<ConsultantDTO>>() {
        }.getType());

    }

    public String updateConsultant(ConsultantDTO consultantDTO) {
        if (consultantRepo.existsById(consultantDTO.getId())) {

            consultantRepo.save(modelMapper.map(consultantDTO, Consultant.class));
            return VarList.RSP_SUCCESS;

        } else {

            return VarList.RSP_NO_DATA_FOUND;
        }
    }



    public ConsultantSignInResponseDTO signInConsultant(ConsultantDTO consultantDTO) {
        String email = consultantDTO.getEmail();
        String providedPassword = consultantDTO.getPassword();

        Consultant consultant = consultantRepo.findByEmail(email);
        if (consultant == null) {
            System.out.println("okkkk");
            throw new AuthenticationFailException("Consultant not present");
        }

        try {
            String hashedProvidedPassword = hashPassword(providedPassword);
            if (!hashedProvidedPassword.equals(consultant.getPassword())) {
                throw new AuthenticationFailException("Incorrect password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("Hashing password failed: {}", e.getMessage());
            throw new CustomException(e.getMessage());
        }

        AuthenticationToken token = authenticationService.getToken(Optional.of(consultant));
        if (!Helper.notNull(token)) {
            throw new CustomException("Token not present");
        }

        return new ConsultantSignInResponseDTO(token.getToken(),VarList.RSP_SUCCESS);
    }

    // ... Rest of the service class


    String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }
}

