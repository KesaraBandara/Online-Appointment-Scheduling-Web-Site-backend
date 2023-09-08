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

@RestController
@RequestMapping("api/v1/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private ResponseDTO responseDTO;

    @PostMapping(value = "/saveAppointment")
    public ResponseEntity saveAppointment(@RequestBody AppointmentDTO appointmentDTO){

        try{
            String res = appointmentService.saveAppointment(appointmentDTO);
            if (res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(appointmentDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            }
            else if (res.equals("06")){
                responseDTO.setCode(VarList.RSP_DUPLICATED);
                responseDTO.setMessage("Already added");
                responseDTO.setContent(appointmentDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
            else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception exception){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(exception.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/appointmentConfirmation/{appointmentId}")
    public ResponseEntity appointmentConfirmation(
            @PathVariable Long appointmentId,
            @RequestBody AppointmentVerifyDTO appointmentVerifyDTO
    ) {
        try {
            appointmentService.updateActivity(appointmentId, appointmentVerifyDTO);

            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(appointmentVerifyDTO);
            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
        } catch (EntityNotFoundException e) {
            responseDTO.setCode(VarList.RSP_DUPLICATED);
            responseDTO.setMessage("Appointment not found");
            responseDTO.setContent(appointmentVerifyDTO);
            return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(exception.getMessage());
            responseDTO.setContent(null);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
