package com.thejobs.onlineappointmentschedulingwebsite.controller;

import com.thejobs.onlineappointmentschedulingwebsite.dto.ResponseDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.UserDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.UserSignInResponseDTO;
import com.thejobs.onlineappointmentschedulingwebsite.service.UserService;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final ResponseDTO responseDTO;

    @Autowired
    public UserController(UserService userService, ResponseDTO responseDTO) {
        this.userService = userService;
        this.responseDTO = responseDTO;
    }

    @PostMapping("/signUpUser")
    public ResponseEntity<ResponseDTO> signUpUser(@RequestBody UserDTO userDTO) {
        try {
            String result = userService.signUpUser(userDTO);
            return getResponseEntity(userDTO, result);
        } catch (Exception exception) {
            return buildErrorResponse(exception);
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<ResponseDTO> getAllUsers() {
        try {
            List<UserDTO> userDTOList = userService.getAllUsers();
            return buildResponseEntity(userDTOList, VarList.RSP_SUCCESS, "Success", HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return buildErrorResponse(exception);
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UserDTO userDTO) {
        try {
            String result = userService.updateUser(userDTO);
            return getUpdateResponseEntity(userDTO, result);
        } catch (Exception exception) {
            return buildErrorResponse(exception);
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String id) {
        try {
            String result = userService.deleteUser(id);
            if (result.equals("00")) {
                return buildResponse(VarList.RSP_SUCCESS, "Success", null, HttpStatus.ACCEPTED);
            } else {
                return buildResponse(VarList.RSP_NO_DATA_FOUND, "No user available for this ID", null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return buildErrorResponse(e);
        }
    }

    @PostMapping("/signIn")
    public ResponseEntity<UserSignInResponseDTO> signInUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.signInUser(userDTO));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/getUserByToken/{token}")
    public ResponseEntity<ResponseDTO> getUserByToken(@PathVariable String token) {
        try {
            Long consultantId = userService.getUserByToken(token);
            return buildResponseEntity(consultantId, VarList.RSP_SUCCESS, "Success", HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return buildErrorResponse(exception);
        }
    }

    private ResponseEntity<ResponseDTO> getResponseEntity(UserDTO userDTO, String result) {
        switch (result) {
            case "00":
                return buildResponse(VarList.RSP_SUCCESS, "Success", userDTO, HttpStatus.ACCEPTED);
            case "06":
                return buildResponse(VarList.RSP_DUPLICATED, "Already added", userDTO, HttpStatus.BAD_REQUEST);
            default:
                return buildResponse(VarList.RSP_FAIL, "Error", null, HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<ResponseDTO> getUpdateResponseEntity(UserDTO userDTO, String result) {
        switch (result) {
            case "00":
                return buildResponse(VarList.RSP_SUCCESS, "Success", userDTO, HttpStatus.ACCEPTED);
            case "01":
                return buildResponse(VarList.RSP_DUPLICATED, "Not a registered user", userDTO, HttpStatus.BAD_REQUEST);
            default:
                return buildResponse(VarList.RSP_FAIL, "Error", null, HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<ResponseDTO> buildResponseEntity(Object content, String code, String message, HttpStatus status) {
        responseDTO.setCode(code);
        responseDTO.setMessage(message);
        responseDTO.setContent(content);
        return new ResponseEntity<>(responseDTO, status);
    }

    private ResponseEntity<ResponseDTO> buildResponse(String code, String message, Object content, HttpStatus status) {
        responseDTO.setCode(code);
        responseDTO.setMessage(message);
        responseDTO.setContent(content);
        return new ResponseEntity<>(responseDTO, status);
    }

    private ResponseEntity<ResponseDTO> buildErrorResponse(Exception exception) {
        responseDTO.setCode(VarList.RSP_ERROR);
        responseDTO.setMessage(exception.getMessage());
        responseDTO.setContent(null);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}




//package com.thejobs.onlineappointmentschedulingwebsite.controller;
//
//import com.thejobs.onlineappointmentschedulingwebsite.dto.ConsultantDTO;
//import com.thejobs.onlineappointmentschedulingwebsite.dto.CountryDTO;
//import com.thejobs.onlineappointmentschedulingwebsite.dto.ResponseDTO;
//import com.thejobs.onlineappointmentschedulingwebsite.dto.UserDTO;
//import com.thejobs.onlineappointmentschedulingwebsite.service.UserService;
//import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/v1/user")
//public class UserController {
//
//    @Autowired
//    public UserService userService;
//
//    @Autowired
//    private ResponseDTO responseDTO;
//    @PostMapping(value = "/signUpUser")
//    public ResponseEntity signUpUser(@RequestBody UserDTO userDTO) {
//
//        try {
//
//            String res = userService.signUpUser(userDTO);
//            if (res.equals("00")) {
//                responseDTO.setCode(VarList.RSP_SUCCESS);
//                responseDTO.setMessage("Success");
//                responseDTO.setContent(userDTO);
//                System.out.println(responseDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//            } else if (res.equals("06")) {
//                responseDTO.setCode(VarList.RSP_DUPLICATED);
//                responseDTO.setMessage("Already added");
//                responseDTO.setContent(userDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
//            } else {
//                responseDTO.setCode(VarList.RSP_FAIL);
//                responseDTO.setMessage("Error");
//                responseDTO.setContent(null);
//                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
//            }
//        } catch (Exception exception) {
//            responseDTO.setCode(VarList.RSP_ERROR);
//            responseDTO.setMessage(exception.getMessage());
//            responseDTO.setContent(null);
//            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/getAllUsers")
//    public ResponseEntity getAllUsers() {
//
//        try {
//
//            List<UserDTO> UserDTOList = userService.getAllUsers();
//            responseDTO.setCode(VarList.RSP_SUCCESS);
//            responseDTO.setMessage("Success");
//            responseDTO.setContent(UserDTOList);
//            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//
//
//        } catch (Exception exception) {
//
//            responseDTO.setCode(VarList.RSP_ERROR);
//            responseDTO.setMessage(exception.getMessage());
//            responseDTO.setContent(null);
//            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//
//        }
//    }
//
//    @PutMapping(value = "/updateUser")
//    public ResponseEntity updateUser(@RequestBody UserDTO userDTO) {
//
//        try {
//            String res = userService.updateUser(userDTO);
//            if (res.equals("00")) {
//                responseDTO.setCode(VarList.RSP_SUCCESS);
//                responseDTO.setMessage("Success");
//                responseDTO.setContent(userDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//            } else if (res.equals("01")) {
//                responseDTO.setCode(VarList.RSP_DUPLICATED);
//                responseDTO.setMessage("Not A Registered user");
//                responseDTO.setContent(userDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
//            } else {
//                responseDTO.setCode(VarList.RSP_FAIL);
//                responseDTO.setMessage("Error");
//                responseDTO.setContent(null);
//                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
//            }
//        } catch (Exception exception) {
//            responseDTO.setCode(VarList.RSP_ERROR);
//            responseDTO.setMessage(exception.getMessage());
//            responseDTO.setContent(null);
//            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @DeleteMapping("/deleteUser/{id}")
//    public ResponseEntity deleteUser(@PathVariable String id){
//        try {
//            String res = userService.deleteUser(id);
//            if (res.equals("00")) {
//                responseDTO.setCode(VarList.RSP_SUCCESS);
//                responseDTO.setMessage("Success");
//                responseDTO.setContent(null);
//                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//            } else {
//                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
//                responseDTO.setMessage("No Product Available For this ID");
//                responseDTO.setContent(null);
//                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
//            }
//        } catch (Exception e) {
//            responseDTO.setCode(VarList.RSP_ERROR);
//            responseDTO.setMessage(e.getMessage());
//            responseDTO.setContent(e);
//            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PostMapping(value = "/signIn")
//    public ResponseEntity signInUser(@RequestBody UserDTO userDTO) {
//
//        System.out.println(userDTO);
//        return ResponseEntity.ok(userService.signInUser(userDTO));
//
//
//    }
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout() {
//
//        return ResponseEntity.ok("Logged out successfully");
//    }
//
//    @GetMapping("/getUserByToken/{token}")
//    public ResponseEntity getUserByToken(@PathVariable String token) {
//
//        try {
//
//            Long ConsultantId = userService.getUserByToken(token);
//            responseDTO.setCode(VarList.RSP_SUCCESS);
//            responseDTO.setMessage("Success");
//            responseDTO.setContent(ConsultantId);
//            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//
//
//        } catch (Exception exception) {
//
//            responseDTO.setCode(VarList.RSP_ERROR);
//            responseDTO.setMessage(exception.getMessage());
//            responseDTO.setContent(null);
//            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//
//        }
//    }
//
//}
