package com.thejobs.onlineappointmentschedulingwebsite.controller;

import com.thejobs.onlineappointmentschedulingwebsite.dto.ResponseDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.TimeDTO;
import com.thejobs.onlineappointmentschedulingwebsite.service.TimeService;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/time")
public class TimeController {

    private final TimeService timeService;
    private final ResponseDTO responseDTO;

    @Autowired
    public TimeController(TimeService timeService, ResponseDTO responseDTO) {
        this.timeService = timeService;
        this.responseDTO = responseDTO;
    }

    @PostMapping("/saveTime")
    public ResponseEntity<ResponseDTO> saveTime(@RequestBody TimeDTO timeDTO) {
        try {
            String result = timeService.saveTime(timeDTO);
            return getResponseEntity(timeDTO, result);
        } catch (Exception exception) {
            return buildErrorResponse(exception);
        }
    }

    @GetMapping("/getAllTimes")
    public ResponseEntity<ResponseDTO> getAllTimes() {
        try {
            List<TimeDTO> timeDTOList = timeService.getAllTimes();
            return buildResponseEntity(timeDTOList, VarList.RSP_SUCCESS, "Success", HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return buildErrorResponse(exception);
        }
    }

    private ResponseEntity<ResponseDTO> getResponseEntity(TimeDTO timeDTO, String result) {
        switch (result) {
            case "00":
                return buildResponse(VarList.RSP_SUCCESS, "Success", timeDTO, HttpStatus.ACCEPTED);
            case "06":
                return buildResponse(VarList.RSP_DUPLICATED, "Already added", timeDTO, HttpStatus.BAD_REQUEST);
            default:
                return buildResponse(VarList.RSP_FAIL, "Error", null, HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<ResponseDTO> buildResponseEntity(Object content, String successCode, String successMessage,
                                                            HttpStatus successStatus) {
        return buildResponse(successCode, successMessage, content, successStatus);
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
//import com.thejobs.onlineappointmentschedulingwebsite.dto.ResponseDTO;
//import com.thejobs.onlineappointmentschedulingwebsite.dto.TimeDTO;
//import com.thejobs.onlineappointmentschedulingwebsite.service.TimeService;
//import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/v1/time")
//public class TimeController {
//
//    @Autowired
//    private TimeService timeService;
//
//    @Autowired
//    private ResponseDTO responseDTO;
//
//    @PostMapping(value = "/saveTime")
//    public ResponseEntity saveTime(@RequestBody TimeDTO timeDTO){
//
//        try{
//
//            String res = timeService.saveTime(timeDTO);
//            if (res.equals("00")){
//                responseDTO.setCode(VarList.RSP_SUCCESS);
//                responseDTO.setMessage("Success");
//                responseDTO.setContent(timeDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//            }
//            else if (res.equals("06")){
//                responseDTO.setCode(VarList.RSP_DUPLICATED);
//                responseDTO.setMessage("Already added");
//                responseDTO.setContent(timeDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
//            }
//            else {
//                responseDTO.setCode(VarList.RSP_FAIL);
//                responseDTO.setMessage("Error");
//                responseDTO.setContent(null);
//                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
//            }
//        }
//
//        catch (Exception exception){
//            responseDTO.setCode(VarList.RSP_ERROR);
//            responseDTO.setMessage(exception.getMessage());
//            responseDTO.setContent(null);
//            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/getAllTimes")
//    public ResponseEntity getAllTimes() {
//
//        try {
//
//            List<TimeDTO> timeDTOList = timeService.getAllTimes();
//            responseDTO.setCode(VarList.RSP_SUCCESS);
//            responseDTO.setMessage("Success");
//            responseDTO.setContent(timeDTOList);
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
//
//
//}
