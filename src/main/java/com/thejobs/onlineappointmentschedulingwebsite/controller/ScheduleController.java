package com.thejobs.onlineappointmentschedulingwebsite.controller;

import com.thejobs.onlineappointmentschedulingwebsite.dto.ResponseDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.ScheduleDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.ScheduleSummaryDTO;
import com.thejobs.onlineappointmentschedulingwebsite.service.ScheduleService;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ResponseDTO responseDTO;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, ResponseDTO responseDTO) {
        this.scheduleService = scheduleService;
        this.responseDTO = responseDTO;
    }

    @PostMapping("/saveSchedule")
    public ResponseEntity<ResponseDTO> saveSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        try {
            String res = scheduleService.saveSchedule(scheduleDTO);
            switch (res) {
                case "00":
                    return buildResponse(VarList.RSP_SUCCESS, "Success", scheduleDTO, HttpStatus.ACCEPTED);
                case "06":
                    return buildResponse(VarList.RSP_DUPLICATED, "Already added", scheduleDTO, HttpStatus.BAD_REQUEST);
                default:
                    return buildResponse(VarList.RSP_FAIL, "Error", null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            return buildResponse(VarList.RSP_ERROR, exception.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllScheduleById/{id}")
    public ResponseEntity<ResponseDTO> getAllScheduleById(@PathVariable String id) {
        try {
            List<ScheduleSummaryDTO> scheduleDTO = scheduleService.getAllScheduleById(id);
            if (scheduleDTO != null) {
                return buildResponse(VarList.RSP_SUCCESS, "Success", scheduleDTO, HttpStatus.ACCEPTED);
            } else {
                return buildResponse(VarList.RSP_NO_DATA_FOUND, "No schedule available for this ID", null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            return buildResponse(VarList.RSP_ERROR, exception.getMessage(), exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteSchedule/{id}")
    public ResponseEntity<ResponseDTO> deleteSchedule(@PathVariable String id) {
        try {
            String res = scheduleService.deleteSchedule(id);
            if (res.equals("00")) {
                return buildResponse(VarList.RSP_SUCCESS, "Success", null, HttpStatus.ACCEPTED);
            } else {
                return buildResponse(VarList.RSP_NO_DATA_FOUND, "No schedule available for this ID", null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return buildResponse(VarList.RSP_ERROR, e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllSchedule")
    public ResponseEntity<ResponseDTO> getAllSchedule() {
        try {
            List<ScheduleSummaryDTO> scheduleDTOList = scheduleService.getAllSchedule();
            return buildResponse(VarList.RSP_SUCCESS, "Success", scheduleDTOList, HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return buildResponse(VarList.RSP_ERROR, exception.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllScheduleByCountryAndJobType/{country}/{jobType}")
    public ResponseEntity<ResponseDTO> getAllScheduleByCountryAndJobType(@PathVariable String country, @PathVariable String jobType) {
        try {
            List<ScheduleSummaryDTO> scheduleDTOs = scheduleService.getAllScheduleByCountryAndJobType(country, jobType);
            if (scheduleDTOs != null) {
                return buildResponse(VarList.RSP_SUCCESS, "Success", scheduleDTOs, HttpStatus.ACCEPTED);
            } else {
                return buildResponse(VarList.RSP_NO_DATA_FOUND, "No schedule available for this country and job type", null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            return buildResponse(VarList.RSP_ERROR, exception.getMessage(), exception, HttpStatus.INTERNAL_SERVER_ERROR);
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
//import com.thejobs.onlineappointmentschedulingwebsite.dto.ResponseDTO;
//import com.thejobs.onlineappointmentschedulingwebsite.dto.ScheduleDTO;
//import com.thejobs.onlineappointmentschedulingwebsite.dto.ScheduleSummaryDTO;
//import com.thejobs.onlineappointmentschedulingwebsite.service.ScheduleService;
//import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/v1/schedule")
//public class ScheduleController {
//
//    @Autowired
//    private ScheduleService scheduleService;
//    @Autowired
//    private ResponseDTO responseDTO;
//    @PostMapping(value = "/saveSchedule")
//    public ResponseEntity saveSchedule(@RequestBody ScheduleDTO scheduleDTO){
//
//        try{
//
//            String res = scheduleService.saveSchedule(scheduleDTO);
//            if (res.equals("00")){
//                responseDTO.setCode(VarList.RSP_SUCCESS);
//                responseDTO.setMessage("Success");
//                responseDTO.setContent(scheduleDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//            }
//            else if (res.equals("06")){
//                responseDTO.setCode(VarList.RSP_DUPLICATED);
//                responseDTO.setMessage("Already added");
//                responseDTO.setContent(scheduleDTO);
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
//    @GetMapping("/getAllScheduleById/{id}")
//    public ResponseEntity getAllProductsByID(@PathVariable String id){
//        try {
//            System.out.println(id);
//            List<ScheduleSummaryDTO> scheduleDTO = scheduleService.getAllScheduleById(id);
//            if (scheduleDTO !=null) {
//                responseDTO.setCode(VarList.RSP_SUCCESS);
//                responseDTO.setMessage("Success");
//                responseDTO.setContent(scheduleDTO);
//                System.out.println(responseDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//            } else {
//                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
//                responseDTO.setMessage("No scheduleDTO Available For this ID");
//                responseDTO.setContent(null);
//                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
//            }
//        } catch (Exception exception) {
//            responseDTO.setCode(VarList.RSP_ERROR);
//            responseDTO.setMessage(exception.getMessage());
//            responseDTO.setContent(exception);
//            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @DeleteMapping("/deleteSchedule/{id}")
//    public ResponseEntity deleteSchedule(@PathVariable String id){
//        try {
//            String res = scheduleService.deleteSchedule(id);
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
//    @GetMapping("/getAllSchedule")
//    public ResponseEntity getAllSchedule() {
//
//        try {
//            List<ScheduleSummaryDTO> scheduleDTOList = scheduleService.getAllSchedule();
//            responseDTO.setCode(VarList.RSP_SUCCESS);
//            responseDTO.setMessage("Success");
//            responseDTO.setContent(scheduleDTOList);
//            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//
//        } catch (Exception exception) {
//
//            responseDTO.setCode(VarList.RSP_ERROR);
//            responseDTO.setMessage(exception.getMessage());
//            responseDTO.setContent(null);
//            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping("/getAllScheduleByCountryAndJobType/{country}/{jobType}")
//    public ResponseEntity getAllScheduleByCountryAndJobType(@PathVariable String country ,@PathVariable String jobType){
//
//        try {
//            List<ScheduleSummaryDTO> scheduleDTOS = scheduleService.getAllScheduleByCountryAndJobType(country,jobType);
//            if (scheduleDTOS !=null) {
//                responseDTO.setCode(VarList.RSP_SUCCESS);
//                responseDTO.setMessage("Success");
//                responseDTO.setContent(scheduleDTOS);
//                System.out.println(responseDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//            } else {
//                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
//                responseDTO.setMessage("No product Available For this ID");
//                responseDTO.setContent(null);
//                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
//            }
//        } catch (Exception exception) {
//            responseDTO.setCode(VarList.RSP_ERROR);
//            responseDTO.setMessage(exception.getMessage());
//            responseDTO.setContent(exception);
//            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}