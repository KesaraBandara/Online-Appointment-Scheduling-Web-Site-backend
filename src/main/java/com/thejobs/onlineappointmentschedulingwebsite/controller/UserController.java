package com.thejobs.onlineappointmentschedulingwebsite.controller;

import com.thejobs.onlineappointmentschedulingwebsite.dto.ConsultantDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.CountryDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.ResponseDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.UserDTO;
import com.thejobs.onlineappointmentschedulingwebsite.service.UserService;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    private ResponseDTO responseDTO;
    @PostMapping(value = "/signUpUser")
    public ResponseEntity signUpUser(@RequestBody UserDTO userDTO) {

        try {

            String res = userService.signUpUser(userDTO);
            if (res.equals("00")) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(userDTO);
                System.out.println(responseDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else if (res.equals("06")) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Already added");
                responseDTO.setContent(userDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            } else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(exception.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity getAllUsers() {

        try {

            List<UserDTO> UserDTOList = userService.getAllUsers();
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(UserDTOList);
            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);


        } catch (Exception exception) {

            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(exception.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping(value = "/updateUser")
    public ResponseEntity updateUser(@RequestBody UserDTO userDTO) {

        try {
            String res = userService.updateUser(userDTO);
            if (res.equals("00")) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(userDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else if (res.equals("01")) {
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Not A Registered user");
                responseDTO.setContent(userDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            } else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(exception.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteUser(@PathVariable String id){
        try {
            String res = userService.deleteUser(id);
            if (res.equals("00")) {
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            } else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("No Product Available For this ID");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setContent(e);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/signIn")
    public ResponseEntity signInUser(@RequestBody UserDTO userDTO) {

        System.out.println(userDTO);
        return ResponseEntity.ok(userService.signInUser(userDTO));


    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {

        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/getUserByToken/{token}")
    public ResponseEntity getUserByToken(@PathVariable String token) {

        try {

            Long ConsultantId = userService.getUserByToken(token);
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(ConsultantId);
            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);


        } catch (Exception exception) {

            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(exception.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
