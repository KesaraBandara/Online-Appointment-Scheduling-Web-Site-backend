package com.thejobs.onlineappointmentschedulingwebsite;

import com.thejobs.onlineappointmentschedulingwebsite.dto.ConsultantDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.ConsultantSignInResponseDTO;
import com.thejobs.onlineappointmentschedulingwebsite.entity.AuthenticationToken;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Consultant;
import com.thejobs.onlineappointmentschedulingwebsite.exceptions.AuthenticationFailException;
import com.thejobs.onlineappointmentschedulingwebsite.exceptions.CustomException;
import com.thejobs.onlineappointmentschedulingwebsite.repo.ConsultantRepo;
import com.thejobs.onlineappointmentschedulingwebsite.repo.TokenRepo;
import com.thejobs.onlineappointmentschedulingwebsite.service.AuthenticationService;
import com.thejobs.onlineappointmentschedulingwebsite.service.ConsultantService;
import com.thejobs.onlineappointmentschedulingwebsite.service.PasswordHasher;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class ConsultantServiceTest {

    @Autowired
    private ConsultantService consultantService;

    @MockBean
    private ConsultantRepo consultantRepo;
    @MockBean
    private PasswordHasher passwordHasher;

    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private TokenRepo tokenRepo;

    @Test
    public void testSaveConsultant() {
        // Prepare a mock ConsultantDTO for testing
        ConsultantDTO consultantDTO = new ConsultantDTO();
        consultantDTO.setfName("John");
        consultantDTO.setlName("Doe");
        consultantDTO.setEmail("john@example.com");
        consultantDTO.setPassword("password"); // You may want to hash this in a real test

        // Mock the behavior of consultantRepo.findByEmail
        when(consultantRepo.findByEmail(consultantDTO.getEmail())).thenReturn(null);

        // Mock the behavior of consultantRepo.save
        when(consultantRepo.save(org.mockito.ArgumentMatchers.any(Consultant.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // Return the saved consultant

        // Test the saveConsultant method
        String result = consultantService.saveConsultant(consultantDTO);

        // Assert the expected result
        assertEquals(VarList.RSP_SUCCESS, result);
    }
    @Test
    public void testGetAllConsultants() {
        // Prepare mock data for the repository
        List<Consultant> mockConsultants = Arrays.asList(
                new Consultant("John", "Doe", "Male", "john@example.com", "123456", "hashed_password"),
                new Consultant("Jane", "Smith", "Female", "jane@example.com", "789012", "hashed_password")
        );

        // Mock the behavior of consultantRepo.findAll
        when(consultantRepo.findAll()).thenReturn(mockConsultants);

        // Call the getAllConsultants method
        List<ConsultantDTO> result = consultantService.getAllConsultants();

        // Assert the expected result based on the mocked data
        assertEquals(mockConsultants.size(), result.size());
        // You can add more specific assertions as needed to verify the mapping logic.
    }

//    @Test
//    public void testUpdateConsultant() throws NoSuchAlgorithmException {
//        // Prepare a mock ConsultantDTO for testing
//        ConsultantDTO consultantDTO = new ConsultantDTO();
//        consultantDTO.setId(1L); // Set the ID to an existing consultant
//        consultantDTO.setPassword("new_password"); // New password to hash
//
//        // Mock the behavior of consultantRepo.existsById
//        when(consultantRepo.existsById(consultantDTO.getId())).thenReturn(true);
//
//        // Mock the behavior of passwordHasher.hashPassword
//        when(passwordHasher.hashPassword(consultantDTO.getPassword())).thenReturn("hashed_password");
//
//        // Call the updateConsultant method
//        String result = consultantService.updateConsultant(consultantDTO);
//
//        // Assert the expected result
//        assertEquals(VarList.RSP_SUCCESS, result);
//
//        // Verify that save is called with the updated DTO
//        verify(consultantRepo, ExsltDatetime.time(1)).save(any(Consultant.class));
//    }

    @Test
    public void testSignInConsultant_ValidCredentials() throws NoSuchAlgorithmException {
        // Prepare a mock ConsultantDTO for testing
        ConsultantDTO consultantDTO = new ConsultantDTO();
        consultantDTO.setEmail("john@example.com");
        consultantDTO.setPassword("password");

        // Mock the behavior of consultantRepo.findByEmail
        Consultant mockConsultant = new Consultant();
        mockConsultant.setEmail(consultantDTO.getEmail());
        mockConsultant.setPassword("hashed_password"); // Mocked hashed password
        when(consultantRepo.findByEmail(consultantDTO.getEmail())).thenReturn(mockConsultant);

        // Mock the behavior of passwordHasher.hashPassword
        when(passwordHasher.hashPassword(consultantDTO.getPassword())).thenReturn("hashed_password");

        // Mock the behavior of authenticationService.getToken
        AuthenticationToken mockToken = new AuthenticationToken(mockConsultant);
        when(authenticationService.getToken(Optional.of(mockConsultant))).thenReturn(mockToken);

        // Call the signInConsultant method
        ConsultantSignInResponseDTO result = consultantService.signInConsultant(consultantDTO);

        // Assert the expected result
        assertNotNull(result);
        assertEquals(mockToken.getToken(), result.getToken());
        assertEquals(VarList.RSP_SUCCESS, result.getClass());
    }

    @Test
    public void testSignInConsultant_InvalidEmail() {
        // Prepare a mock ConsultantDTO for testing with an invalid email
        ConsultantDTO consultantDTO = new ConsultantDTO();
        consultantDTO.setEmail("nonexistent@example.com");
        consultantDTO.setPassword("password");

        // Mock the behavior of consultantRepo.findByEmail to return null (no matching consultant)
        when(consultantRepo.findByEmail(consultantDTO.getEmail())).thenReturn(null);

        // Assert that an AuthenticationFailException is thrown
        assertThrows(AuthenticationFailException.class, () -> consultantService.signInConsultant(consultantDTO));
    }

    @Test
    public void testSignInConsultant_IncorrectPassword() throws NoSuchAlgorithmException {
        // Prepare a mock ConsultantDTO for testing with incorrect password
        ConsultantDTO consultantDTO = new ConsultantDTO();
        consultantDTO.setEmail("john@example.com");
        consultantDTO.setPassword("incorrect_password");

        // Mock the behavior of consultantRepo.findByEmail to return a consultant
        Consultant mockConsultant = new Consultant();
        mockConsultant.setEmail(consultantDTO.getEmail());
        mockConsultant.setPassword("hashed_password"); // Mocked hashed password
        when(consultantRepo.findByEmail(consultantDTO.getEmail())).thenReturn(mockConsultant);

        // Mock the behavior of passwordHasher.hashPassword to return a different hash
        when(passwordHasher.hashPassword(consultantDTO.getPassword())).thenReturn("different_hash");

        // Assert that an AuthenticationFailException is thrown
        assertThrows(AuthenticationFailException.class, () -> consultantService.signInConsultant(consultantDTO));
    }

    @Test
    public void testSignInConsultant_NoToken() {
        // Prepare a mock ConsultantDTO for testing
        ConsultantDTO consultantDTO = new ConsultantDTO();
        consultantDTO.setEmail("john@example.com");
        consultantDTO.setPassword("password");

        // Mock the behavior of consultantRepo.findByEmail to return a consultant
        Consultant mockConsultant = new Consultant();
        mockConsultant.setEmail(consultantDTO.getEmail());
        mockConsultant.setPassword("hashed_password"); // Mocked hashed password
        when(consultantRepo.findByEmail(consultantDTO.getEmail())).thenReturn(mockConsultant);

        // Mock the behavior of authenticationService.getToken to return null
        when(authenticationService.getToken(Optional.of(mockConsultant))).thenReturn(null);

        // Assert that a CustomException is thrown
        assertThrows(CustomException.class, () -> consultantService.signInConsultant(consultantDTO));
    }

    @Test
    public void testDeleteConsultant_ExistingConsultant() {
        // Prepare a mock consultant ID for testing
        Long consultantId = 1L;

        // Mock the behavior of consultantRepo.existsById to return true (consultant exists)
        when(consultantRepo.existsById(consultantId)).thenReturn(true);

        // Call the deleteConsultant method
        String result = consultantService.deleteConsultant(String.valueOf(consultantId));

        // Assert the expected result
        assertEquals(VarList.RSP_SUCCESS, result);

        // Verify that deleteTokenByConsultantId and deleteById methods are called
        verify(authenticationService, times(1)).deleteTokenByConsultantId(consultantId);
        verify(consultantRepo, times(1)).deleteById(consultantId);
    }

    @Test
    public void testDeleteConsultant_NonExistingConsultant() {
        // Prepare a mock consultant ID for testing
        Long consultantId = 1L;

        // Mock the behavior of consultantRepo.existsById to return false (consultant doesn't exist)
        when(consultantRepo.existsById(consultantId)).thenReturn(false);

        // Call the deleteConsultant method
        String result = consultantService.deleteConsultant(String.valueOf(consultantId));

        // Assert the expected result
        assertEquals(VarList.RSP_NO_DATA_FOUND, result);

        // Verify that deleteTokenByConsultantId and deleteById methods are not called
        verify(authenticationService, never()).deleteTokenByConsultantId(consultantId);
        verify(consultantRepo, never()).deleteById(consultantId);
    }

    @Test
    public void testGetConsultantByToken_ValidToken() {
        // Prepare a mock token for testing
        String mockTokenValue = "valid_token";

        // Mock the behavior of tokenRepo.findTokenByToken to return a token with a consultant
        AuthenticationToken mockToken = new AuthenticationToken();
        mockToken.setToken(mockTokenValue);
        mockToken.setConsultant(new Consultant());
        when(tokenRepo.findTokenByToken(mockTokenValue)).thenReturn(mockToken);

        // Call the getConsultantByToken method
        Long consultantId = consultantService.getConsultantByToken(mockTokenValue);

        // Assert the expected consultant ID
        assertNotNull(consultantId);
        // You can add more specific assertions if needed
    }

    @Test
    public void testGetConsultantByToken_InvalidToken() {
        // Prepare a mock invalid token for testing
        String mockTokenValue = "invalid_token";

        // Mock the behavior of tokenRepo.findTokenByToken to return null (no matching token)
        when(tokenRepo.findTokenByToken(mockTokenValue)).thenReturn(null);

        // Assert that an EntityNotFoundException is thrown
        assertThrows(EntityNotFoundException.class, () -> consultantService.getConsultantByToken(mockTokenValue));
    }

    @Test
    public void testGetConsultantByToken_NullConsultant() {
        // Prepare a mock token for testing with a null consultant
        String mockTokenValue = "token_with_null_consultant";

        // Mock the behavior of tokenRepo.findTokenByToken to return a token with a null consultant
        AuthenticationToken mockToken = new AuthenticationToken();
        mockToken.setToken(mockTokenValue);
        mockToken.setConsultant(null);
        when(tokenRepo.findTokenByToken(mockTokenValue)).thenReturn(mockToken);

        // Assert that an EntityNotFoundException is thrown
        assertThrows(EntityNotFoundException.class, () -> consultantService.getConsultantByToken(mockTokenValue));
    }


}
