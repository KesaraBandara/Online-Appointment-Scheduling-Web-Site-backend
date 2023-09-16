package com.thejobs.onlineappointmentschedulingwebsite;
import com.thejobs.onlineappointmentschedulingwebsite.dto.UserSignInResponseDTO;
import com.thejobs.onlineappointmentschedulingwebsite.exceptions.AuthenticationFailException;
import com.thejobs.onlineappointmentschedulingwebsite.exceptions.CustomException;
import com.thejobs.onlineappointmentschedulingwebsite.repo.UserTokenRepo;
import com.thejobs.onlineappointmentschedulingwebsite.service.PasswordHasher;
import com.thejobs.onlineappointmentschedulingwebsite.service.UserService;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.thejobs.onlineappointmentschedulingwebsite.dto.UserDTO;
import com.thejobs.onlineappointmentschedulingwebsite.entity.User;
import com.thejobs.onlineappointmentschedulingwebsite.entity.AuthenticationTokenUser;
import com.thejobs.onlineappointmentschedulingwebsite.service.UserAuthenticationService;
import com.thejobs.onlineappointmentschedulingwebsite.repo.UserRepo;
import org.springframework.test.context.ActiveProfiles;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private PasswordHasher passwordHasher;

    @MockBean
    private UserAuthenticationService userAuthenticationService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private UserTokenRepo userTokenRepo;

    @Test
    public void testSignUpUser_Success() throws NoSuchAlgorithmException {
        // Prepare a mock UserDTO for testing
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("user@example.com");
        userDTO.setPassword("password");

        // Mock the behavior of userRepo.findByEmail to return null (no existing user with the same email)
        when(userRepo.findByEmail(userDTO.getEmail())).thenReturn(null);

        // Mock the behavior of passwordHasher.hashPassword to return a hashed password
        when(passwordHasher.hashPassword(userDTO.getPassword())).thenReturn("hashed_password");

        // Mock the behavior of userRepo.save to return the created user
        User mockUser = new User();
        when(userRepo.save(any(User.class))).thenReturn(mockUser);

        // Mock the behavior of userAuthenticationService.saveConfirmationToken
        doNothing().when(userAuthenticationService).saveConfirmationToken(any(AuthenticationTokenUser.class));

        // Call the signUpUser method
        String result = userService.signUpUser(userDTO);

        // Assert the expected result
        assertEquals(VarList.RSP_SUCCESS, result);

        // Verify that save and saveConfirmationToken methods are called
        verify(userRepo, times(1)).save(any(User.class));
        verify(userAuthenticationService, times(1)).saveConfirmationToken(any(AuthenticationTokenUser.class));
    }

    @Test
    public void testSignUpUser_DuplicateEmail() {
        // Prepare a mock UserDTO for testing with a duplicate email
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("duplicate@example.com");

        // Mock the behavior of userRepo.findByEmail to return an existing user with the same email
        User existingUser = new User();
        when(userRepo.findByEmail(userDTO.getEmail())).thenReturn(existingUser);

        // Call the signUpUser method
        String result = userService.signUpUser(userDTO);

        // Assert the expected result
        assertEquals(VarList.RSP_DUPLICATED, result);

        // Verify that save and saveConfirmationToken methods are not called
        verify(userRepo, never()).save(any(User.class));
        verify(userAuthenticationService, never()).saveConfirmationToken(any(AuthenticationTokenUser.class));
    }

    @Test
    public void testSignUpUser_HashingError() throws NoSuchAlgorithmException {
        // Prepare a mock UserDTO for testing
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("user@example.com");
        userDTO.setPassword("password");

        // Mock the behavior of userRepo.findByEmail to return null (no existing user with the same email)
        when(userRepo.findByEmail(userDTO.getEmail())).thenReturn(null);

        // Mock the behavior of passwordHasher.hashPassword to throw a NoSuchAlgorithmException
        when(passwordHasher.hashPassword(userDTO.getPassword())).thenThrow(new NoSuchAlgorithmException("Hashing error"));

        // Call the signUpUser method
        String result = userService.signUpUser(userDTO);

        // Assert the expected result
        assertEquals(VarList.RSP_ERROR, result);

        // Verify that save and saveConfirmationToken methods are not called
        verify(userRepo, never()).save(any(User.class));
        verify(userAuthenticationService, never()).saveConfirmationToken(any(AuthenticationTokenUser.class));
    }


    @Test
    public void testGetAllUsers() {
        // Prepare mock user data for testing
        User user1 = new User("John", "Doe", "Male", "john@example.com", "123456", "hashed_password_1");
        User user2 = new User("Jane", "Doe", "Female", "jane@example.com", "7891011", "hashed_password_2");
        List<User> mockUsersList = Arrays.asList(user1, user2);

        // Mock the behavior of userRepo.findAll to return the mockUsersList
        when(userRepo.findAll()).thenReturn(mockUsersList);

        // Prepare mock DTOs for mapping
        UserDTO userDTO1 = new UserDTO("John", "Doe", "Male", "john@example.com", "123456");
        UserDTO userDTO2 = new UserDTO("Jane", "Doe", "Female", "jane@example.com", "7891011");

        // Mock the behavior of modelMapper.map to return the mock DTOs
        when(modelMapper.map(mockUsersList, new TypeToken<List<UserDTO>>() {}.getType()))
                .thenReturn(Arrays.asList(userDTO1, userDTO2));

        // Call the getAllUsers method
        List<UserDTO> result = userService.getAllUsers();

        // Assert the expected result
        assertNotNull(result);
        assertEquals(2, result.size());

        // You can add more specific assertions if needed
        assertEquals("John", result.get(0).getfName());
        assertEquals("Jane", result.get(1).getfName());
    }
    @Test
    public void testUpdateUser_Success() throws NoSuchAlgorithmException {
        // Prepare a mock UserDTO for testing
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L); // Set an existing user ID
        userDTO.setPassword("new_password");

        // Mock the behavior of userRepo.existsById to return true (user exists)
        when(userRepo.existsById(userDTO.getId())).thenReturn(true);

        // Mock the behavior of passwordHasher.hashPassword to return a hashed password
        when(passwordHasher.hashPassword(userDTO.getPassword())).thenReturn("hashed_new_password");

        // Mock the behavior of modelMapper.map to return a User entity
        User mockUser = new User();
        when(modelMapper.map(userDTO, User.class)).thenReturn(mockUser);

        // Mock the behavior of userRepo.save to return the updated user
        when(userRepo.save(mockUser)).thenReturn(mockUser);

        // Call the updateUser method
        String result = userService.updateUser(userDTO);

        // Assert the expected result
        assertEquals(VarList.RSP_SUCCESS, result);

        // Verify that save is called with the expected User entity
        verify(userRepo, times(1)).save(mockUser);
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        // Prepare a mock UserDTO for testing with a non-existent user ID
        UserDTO userDTO = new UserDTO();
        userDTO.setId(999L); // Set a non-existent user ID

        // Mock the behavior of userRepo.existsById to return false (user does not exist)
        when(userRepo.existsById(userDTO.getId())).thenReturn(false);

        // Call the updateUser method
        String result = userService.updateUser(userDTO);

        // Assert the expected result
        assertEquals(VarList.RSP_NO_DATA_FOUND, result);

        // Verify that save is not called
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    public void testUpdateUser_HashingError() throws NoSuchAlgorithmException {
        // Prepare a mock UserDTO for testing
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L); // Set an existing user ID
        userDTO.setPassword("new_password");

        // Mock the behavior of userRepo.existsById to return true (user exists)
        when(userRepo.existsById(userDTO.getId())).thenReturn(true);

        // Mock the behavior of passwordHasher.hashPassword to throw a NoSuchAlgorithmException
        when(passwordHasher.hashPassword(userDTO.getPassword())).thenThrow(new NoSuchAlgorithmException("Hashing error"));

        // Call the updateUser method
        String result = userService.updateUser(userDTO);

        // Assert the expected result
        assertEquals(VarList.RSP_ERROR, result);

        // Verify that save is not called
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    public void testDeleteUser_Success() {
        // Prepare a mock user ID for testing
        String userId = "1";

        // Mock the behavior of userRepo.existsById to return true (user exists)
        when(userRepo.existsById(Long.valueOf(userId))).thenReturn(true);

        // Mock the behavior of userAuthenticationService.deleteTokenByUserId
        doNothing().when(userAuthenticationService).deleteTokenByUserId(Long.valueOf(userId));

        // Call the deleteUser method
        String result = userService.deleteUser(userId);

        // Assert the expected result
        assertEquals(VarList.RSP_SUCCESS, result);

        // Verify that deleteTokenByUserId and deleteById are called
        verify(userAuthenticationService, times(1)).deleteTokenByUserId(Long.valueOf(userId));
        verify(userRepo, times(1)).deleteById(Long.valueOf(userId));
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        // Prepare a mock user ID for testing
        String userId = "999"; // Set a non-existent user ID

        // Mock the behavior of userRepo.existsById to return false (user does not exist)
        when(userRepo.existsById(Long.valueOf(userId))).thenReturn(false);

        // Call the deleteUser method
        String result = userService.deleteUser(userId);

        // Assert the expected result
        assertEquals(VarList.RSP_NO_DATA_FOUND, result);

        // Verify that deleteTokenByUserId and deleteById are not called
        verify(userAuthenticationService, never()).deleteTokenByUserId(anyLong());
        verify(userRepo, never()).deleteById(anyLong());
    }

    @Test
    public void testSignInUser_Success() throws NoSuchAlgorithmException {
        // Prepare a mock UserDTO for testing
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        // Mock the behavior of userRepo.findByEmail to return a mock User
        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("hashed_password"); // Assuming you've hashed the password
        when(userRepo.findByEmail(userDTO.getEmail())).thenReturn(mockUser);

        // Mock the behavior of passwordHasher.hashPassword to return the hashed password
        when(passwordHasher.hashPassword(userDTO.getPassword())).thenReturn("hashed_password");

        // Mock the behavior of userAuthenticationService.getUserToken
        AuthenticationTokenUser mockToken = new AuthenticationTokenUser(mockUser);
        when(userAuthenticationService.getUserToken(Optional.of(mockUser))).thenReturn(mockToken);

        // Call the signInUser method
        UserSignInResponseDTO result = userService.signInUser(userDTO);

        // Assert the expected result
        assertNotNull(result);
        assertEquals(VarList.RSP_SUCCESS, result.getCode());
        assertNotNull(result.getUserToken());
    }

    @Test
    public void testSignInUser_UserNotFound() {
        // Prepare a mock UserDTO for testing
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("nonexistent@example.com");
        userDTO.setPassword("password");

        // Mock the behavior of userRepo.findByEmail to return null (user not found)
        when(userRepo.findByEmail(userDTO.getEmail())).thenReturn(null);

        // Call the signInUser method and expect an AuthenticationFailException
        assertThrows(AuthenticationFailException.class, () -> userService.signInUser(userDTO));
    }

    @Test
    public void testSignInUser_IncorrectPassword() throws NoSuchAlgorithmException {
        // Prepare a mock UserDTO for testing
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("wrong_password");

        // Mock the behavior of userRepo.findByEmail to return a mock User
        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("hashed_password"); // Assuming you've hashed the password
        when(userRepo.findByEmail(userDTO.getEmail())).thenReturn(mockUser);

        // Mock the behavior of passwordHasher.hashPassword to return a different hashed password
        when(passwordHasher.hashPassword(userDTO.getPassword())).thenReturn("different_hashed_password");

        // Call the signInUser method and expect an AuthenticationFailException
        assertThrows(AuthenticationFailException.class, () -> userService.signInUser(userDTO));
    }

    @Test
    public void testSignInUser_HashingError() throws NoSuchAlgorithmException {
        // Prepare a mock UserDTO for testing
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        // Mock the behavior of userRepo.findByEmail to return a mock User
        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("hashed_password"); // Assuming you've hashed the password
        when(userRepo.findByEmail(userDTO.getEmail())).thenReturn(mockUser);

        // Mock the behavior of passwordHasher.hashPassword to throw a NoSuchAlgorithmException
        when(passwordHasher.hashPassword(userDTO.getPassword())).thenThrow(new NoSuchAlgorithmException("Hashing error"));

        // Call the signInUser method and expect a CustomException
        assertThrows(CustomException.class, () -> userService.signInUser(userDTO));
    }

    @Test
    public void testSignInUser_TokenNotPresent() throws NoSuchAlgorithmException {
        // Prepare a mock UserDTO for testing
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        // Mock the behavior of userRepo.findByEmail to return a mock User
        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("hashed_password"); // Assuming you've hashed the password
        when(userRepo.findByEmail(userDTO.getEmail())).thenReturn(mockUser);

        // Mock the behavior of passwordHasher.hashPassword to return the hashed password
        when(passwordHasher.hashPassword(userDTO.getPassword())).thenReturn("hashed_password");

        // Mock the behavior of userAuthenticationService.getUserToken to return null
        when(userAuthenticationService.getUserToken(Optional.of(mockUser))).thenReturn(null);

        // Call the signInUser method and expect a CustomException
        assertThrows(CustomException.class, () -> userService.signInUser(userDTO));
    }
    @Test
    public void testGetUserByToken_Success() {
        // Prepare a mock token for testing
        String token = "test_token";

        // Mock the behavior of userTokenRepo.findTokenByUserToken to return a mock AuthenticationTokenUser
        AuthenticationTokenUser mockToken = new AuthenticationTokenUser();
        mockToken.setUser(new User()); // Assuming you have a User object associated with the token
        when(userTokenRepo.findTokenByUserToken(token)).thenReturn(mockToken);

        // Call the getUserByToken method
        Long userId = userService.getUserByToken(token);

        // Assert the expected result
        assertNotNull(userId);
        // You can further assert the value of userId if needed
    }

    @Test
    public void testGetUserByToken_TokenNotFound() {
        // Prepare a mock token for testing
        String token = "nonexistent_token";

        // Mock the behavior of userTokenRepo.findTokenByUserToken to return null (token not found)
        when(userTokenRepo.findTokenByUserToken(token)).thenReturn(null);

        // Call the getUserByToken method and expect an EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> userService.getUserByToken(token));
    }

}

