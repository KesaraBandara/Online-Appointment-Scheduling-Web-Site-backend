package com.thejobs.onlineappointmentschedulingwebsite.controller;

import com.thejobs.onlineappointmentschedulingwebsite.dto.JobDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.ResponseDTO;
import com.thejobs.onlineappointmentschedulingwebsite.service.JobTypeService;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/jobType")
public class JobTypeController {

    private final JobTypeService jobTypeService;
    private final ResponseDTO responseDTO;

    @Autowired
    public JobTypeController(JobTypeService jobTypeService, ResponseDTO responseDTO) {
        this.jobTypeService = jobTypeService;
        this.responseDTO = responseDTO;
    }

    @PostMapping("/saveJobType")
    public ResponseEntity<ResponseDTO> saveJobType(@RequestBody JobDTO jobDTO) {
        try {
            String res = jobTypeService.saveJobType(jobDTO);
            switch (res) {
                case "00":
                    return buildResponse(VarList.RSP_SUCCESS, "Success", jobDTO, HttpStatus.ACCEPTED);
                case "06":
                    return buildResponse(VarList.RSP_DUPLICATED, "Already added", jobDTO, HttpStatus.BAD_REQUEST);
                default:
                    return buildResponse(VarList.RSP_FAIL, "Error", null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            return buildResponse(VarList.RSP_ERROR, exception.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllJobs")
    public ResponseEntity<ResponseDTO> getAllJobs() {
        try {
            List<JobDTO> jobDTOList = jobTypeService.getAllJobs();
            return buildResponse(VarList.RSP_SUCCESS, "Success", jobDTOList, HttpStatus.ACCEPTED);
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
//import com.thejobs.onlineappointmentschedulingwebsite.dto.JobDTO;
//import com.thejobs.onlineappointmentschedulingwebsite.dto.ResponseDTO;
//import com.thejobs.onlineappointmentschedulingwebsite.service.JobTypeService;
//import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/v1/jobType")
//public class JobTypeController {
//
//    @Autowired
//    private JobTypeService jobTypeService;
//
//    @Autowired
//    private ResponseDTO responseDTO;
//
//    @PostMapping(value = "/saveJobType")
//    public ResponseEntity saveJobType(@RequestBody JobDTO jobDTO){
//
//        try{
//
//            String res = jobTypeService.saveJobType(jobDTO);
//            if (res.equals("00")){
//                responseDTO.setCode(VarList.RSP_SUCCESS);
//                responseDTO.setMessage("Success");
//                responseDTO.setContent(jobDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//            }
//            else if (res.equals("06")){
//                responseDTO.setCode(VarList.RSP_DUPLICATED);
//                responseDTO.setMessage("Already added");
//                responseDTO.setContent(jobDTO);
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
//    @GetMapping("/getAllJobs")
//    public ResponseEntity getAllJobs() {
//
//        try {
//
//            List<JobDTO> jobDTOList = jobTypeService.getAllJobs();
//            responseDTO.setCode(VarList.RSP_SUCCESS);
//            responseDTO.setMessage("Success");
//            responseDTO.setContent(jobDTOList);
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
