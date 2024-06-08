package com.thejobs.onlineappointmentschedulingwebsite.controller;

import com.thejobs.onlineappointmentschedulingwebsite.dto.AppointmentDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.AppointmentVerifyDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.ResponseDTO;
import com.thejobs.onlineappointmentschedulingwebsite.service.AppointmentService;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final ResponseDTO responseDTO;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, ResponseDTO responseDTO) {
        this.appointmentService = appointmentService;
        this.responseDTO = responseDTO;
    }

    @PostMapping("/saveAppointment")
    public ResponseEntity<ResponseDTO> saveAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        try {
            String res = appointmentService.saveAppointment(appointmentDTO);
            switch (res) {
                case "00":
                    return buildResponse(VarList.RSP_SUCCESS, "Success", appointmentDTO, HttpStatus.ACCEPTED);
                case "06":
                    return buildResponse(VarList.RSP_DUPLICATED, "Already added", appointmentDTO, HttpStatus.BAD_REQUEST);
                default:
                    return buildResponse(VarList.RSP_FAIL, "Error", null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            return buildResponse(VarList.RSP_ERROR, exception.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/appointmentConfirmation/{appointmentId}")
    public ResponseEntity<ResponseDTO> appointmentConfirmation(
            @PathVariable Long appointmentId,
            @RequestBody AppointmentVerifyDTO appointmentVerifyDTO) {
        try {
            appointmentService.updateActivity(appointmentId, appointmentVerifyDTO);
            return buildResponse(VarList.RSP_SUCCESS, "Success", appointmentVerifyDTO, HttpStatus.ACCEPTED);
        } catch (EntityNotFoundException e) {
            return buildResponse(VarList.RSP_DUPLICATED, "Appointment not found", appointmentVerifyDTO, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            return buildResponse(VarList.RSP_ERROR, exception.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllAppointmentById/{id}")
    public ResponseEntity<ResponseDTO> getAllAppointmentById(@PathVariable String id) {
        try {
            List<AppointmentDTO> appointmentDTOList = appointmentService.getAllAppointmentById(id);
            return buildResponse(VarList.RSP_SUCCESS, "Success", appointmentDTOList, HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return buildResponse(VarList.RSP_ERROR, exception.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllAppointments")
    public ResponseEntity<ResponseDTO> getAllAppointments() {
        try {
            List<AppointmentDTO> appointmentDTOList = appointmentService.getAllAppointments();
            return buildResponse(VarList.RSP_SUCCESS, "Success", appointmentDTOList, HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return buildResponse(VarList.RSP_ERROR, exception.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<ResponseDTO> buildResponse(String code, String message, Object content, HttpStatus status) {
        responseDTO.setCode(code);
        responseDTO.setMessage(message);
        responseDTO.setContent(content);
        return new ResponseEntity<>(responseDTO, status);
    }
}


//package com.thejobs.onlineappointmentschedulingwebsite.controller;
//
//import com.thejobs.onlineappointmentschedulingwebsite.dto.*;
//import com.thejobs.onlineappointmentschedulingwebsite.service.AppointmentService;
//import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/v1/appointment")
//public class AppointmentController {
//    @Autowired
//    private AppointmentService appointmentService;
//    @Autowired
//    private ResponseDTO responseDTO;
//
//    @PostMapping(value = "/saveAppointment")
//    public ResponseEntity saveAppointment(@RequestBody AppointmentDTO appointmentDTO){
//
//        try{
//            String res = appointmentService.saveAppointment(appointmentDTO);
//            if (res.equals("00")){
//                responseDTO.setCode(VarList.RSP_SUCCESS);
//                responseDTO.setMessage("Success");
//                responseDTO.setContent(appointmentDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//            }
//            else if (res.equals("06")){
//                responseDTO.setCode(VarList.RSP_DUPLICATED);
//                responseDTO.setMessage("Already added");
//                responseDTO.setContent(appointmentDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
//            }
//            else {
//                responseDTO.setCode(VarList.RSP_FAIL);
//                responseDTO.setMessage("Error");
//                responseDTO.setContent(null);
//                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
//            }
//        }
//        catch (Exception exception){
//            responseDTO.setCode(VarList.RSP_ERROR);
//            responseDTO.setMessage(exception.getMessage());
//            responseDTO.setContent(null);
//            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PutMapping(value = "/appointmentConfirmation/{appointmentId}")
//    public ResponseEntity appointmentConfirmation(
//            @PathVariable Long appointmentId,
//            @RequestBody AppointmentVerifyDTO appointmentVerifyDTO
//    ) {
//        try {
//            appointmentService.updateActivity(appointmentId, appointmentVerifyDTO);
//
//            responseDTO.setCode(VarList.RSP_SUCCESS);
//            responseDTO.setMessage("Success");
//            responseDTO.setContent(appointmentVerifyDTO);
//            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//        } catch (EntityNotFoundException e) {
//            responseDTO.setCode(VarList.RSP_DUPLICATED);
//            responseDTO.setMessage("Appointment not found");
//            responseDTO.setContent(appointmentVerifyDTO);
//            return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
//        } catch (Exception exception) {
//            responseDTO.setCode(VarList.RSP_ERROR);
//            responseDTO.setMessage(exception.getMessage());
//            responseDTO.setContent(null);
//            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping("/getAllAppointmentById/{id}")
//    public ResponseEntity getAllAppointmentById(@PathVariable String id) {
//
//        try {
//
//            List<AppointmentDTO> AppointmentDTOList = appointmentService.getAllAppointmentById(id);
//            responseDTO.setCode(VarList.RSP_SUCCESS);
//            responseDTO.setMessage("Success");
//            responseDTO.setContent(AppointmentDTOList);
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
//    @GetMapping("/getAllAppointments")
//    public ResponseEntity getAllAppointments() {
//
//        try {
//            List<AppointmentDTO> appointmentDTOList = appointmentService.getAllAppointments();
//            responseDTO.setCode(VarList.RSP_SUCCESS);
//            responseDTO.setMessage("Success");
//            System.out.println(appointmentDTOList);
//            responseDTO.setContent(appointmentDTOList);
//            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
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
//
//
//}
