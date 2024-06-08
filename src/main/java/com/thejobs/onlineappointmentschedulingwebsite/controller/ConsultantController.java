package com.thejobs.onlineappointmentschedulingwebsite.controller;

import com.thejobs.onlineappointmentschedulingwebsite.dto.ConsultantDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.ConsultantSignInResponseDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.ResponseDTO;
import com.thejobs.onlineappointmentschedulingwebsite.service.ConsultantService;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/consultant")
public class ConsultantController {

    private final ConsultantService consultantService;
    private final ResponseDTO responseDTO;

    @Autowired
    public ConsultantController(ConsultantService consultantService, ResponseDTO responseDTO) {
        this.consultantService = consultantService;
        this.responseDTO = responseDTO;
    }

    @PostMapping("/saveConsultant")
    public ResponseEntity<ResponseDTO> saveConsultant(@RequestBody ConsultantDTO consultantDTO) {
        try {
            String res = consultantService.saveConsultant(consultantDTO);
            switch (res) {
                case "00":
                    return buildResponse(VarList.RSP_SUCCESS, "Success", consultantDTO, HttpStatus.ACCEPTED);
                case "06":
                    return buildResponse(VarList.RSP_DUPLICATED, "Already added", consultantDTO, HttpStatus.BAD_REQUEST);
                default:
                    return buildResponse(VarList.RSP_FAIL, "Error", null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            return buildResponse(VarList.RSP_ERROR, exception.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllConsultants")
    public ResponseEntity<ResponseDTO> getAllConsultants() {
        try {
            List<ConsultantDTO> consultantDTOList = consultantService.getAllConsultants();
            return buildResponse(VarList.RSP_SUCCESS, "Success", consultantDTOList, HttpStatus.ACCEPTED);
        } catch (Exception exception) {
            return buildResponse(VarList.RSP_ERROR, exception.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateConsultant")
    public ResponseEntity<ResponseDTO> updateConsultant(@RequestBody ConsultantDTO consultantDTO) {
        try {
            String res = consultantService.updateConsultant(consultantDTO);
            switch (res) {
                case "00":
                    return buildResponse(VarList.RSP_SUCCESS, "Success", consultantDTO, HttpStatus.ACCEPTED);
                case "01":
                    return buildResponse(VarList.RSP_DUPLICATED, "Not a registered consultant", consultantDTO, HttpStatus.BAD_REQUEST);
                default:
                    return buildResponse(VarList.RSP_FAIL, "Error", null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            return buildResponse(VarList.RSP_ERROR, exception.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteConsultant/{id}")
    public ResponseEntity<ResponseDTO> deleteConsultant(@PathVariable String id) {
        try {
            String res = consultantService.deleteConsultant(id);
            if ("00".equals(res)) {
                return buildResponse(VarList.RSP_SUCCESS, "Success", null, HttpStatus.ACCEPTED);
            } else {
                return buildResponse(VarList.RSP_NO_DATA_FOUND, "No Consultant Available For this ID", null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return buildResponse(VarList.RSP_ERROR, e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signIn")
    public ResponseEntity<ConsultantSignInResponseDTO> signInConsultant(@RequestBody ConsultantDTO consultantDTO) {
        return ResponseEntity.ok(consultantService.signInConsultant(consultantDTO));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Perform logout actions, such as invalidating the session or clearing tokens
        // ...
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/getConsultantByToken/{token}")
    public ResponseEntity<ResponseDTO> getConsultantByToken(@PathVariable String token) {
        try {
            Long consultantId = consultantService.getConsultantByToken(token);
            return buildResponse(VarList.RSP_SUCCESS, "Success", consultantId, HttpStatus.ACCEPTED);
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
//import com.thejobs.onlineappointmentschedulingwebsite.dto.ConsultantDTO;
//import com.thejobs.onlineappointmentschedulingwebsite.dto.ResponseDTO;
//import com.thejobs.onlineappointmentschedulingwebsite.service.ConsultantService;
//import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/v1/consultant")
//public class ConsultantController {
//
//
//    @Autowired
//    public ConsultantService consultantService;
//
//    @Autowired
//    private ResponseDTO responseDTO;
//
//    @PostMapping(value = "/saveConsultant")
//    public ResponseEntity saveConsultant(@RequestBody ConsultantDTO consultantDTO) {
//
//        try {
//
//            String res = consultantService.saveConsultant(consultantDTO);
//            if (res.equals("00")) {
//                responseDTO.setCode(VarList.RSP_SUCCESS);
//                responseDTO.setMessage("Success");
//                responseDTO.setContent(consultantDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//            } else if (res.equals("06")) {
//                responseDTO.setCode(VarList.RSP_DUPLICATED);
//                responseDTO.setMessage("Already added");
//                responseDTO.setContent(consultantDTO);
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
//    @GetMapping("/getAllConsultants")
//    public ResponseEntity getAllConsultants() {
//
//        try {
//
//            List<ConsultantDTO> ConsultantDTOList = consultantService.getAllConsultants();
//            responseDTO.setCode(VarList.RSP_SUCCESS);
//            responseDTO.setMessage("Success");
//            responseDTO.setContent(ConsultantDTOList);
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
//    @PutMapping(value = "/updateConsultant")
//    public ResponseEntity updateConsultant(@RequestBody ConsultantDTO consultantDTO) {
//
//        try {
//            String res = consultantService.updateConsultant(consultantDTO);
//            if (res.equals("00")) {
//                responseDTO.setCode(VarList.RSP_SUCCESS);
//                responseDTO.setMessage("Success");
//                responseDTO.setContent(consultantDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//            } else if (res.equals("01")) {
//                responseDTO.setCode(VarList.RSP_DUPLICATED);
//                responseDTO.setMessage("Not A Registered consultant");
//                responseDTO.setContent(consultantDTO);
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
//    @DeleteMapping("/deleteConsultant/{id}")
//    public ResponseEntity deleteConsultant(@PathVariable String id){
//        try {
//            String res = consultantService.deleteConsultant(id);
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
//    public ResponseEntity signInConsultant(@RequestBody ConsultantDTO consultantDTO) {
//
//        return ResponseEntity.ok(consultantService.signInConsultant(consultantDTO));
//
//
//    }
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout() {
//        // Perform logout actions, such as invalidating the session or clearing tokens
//        // ...
//
//        // Return a response indicating successful logout
//        return ResponseEntity.ok("Logged out successfully");
//    }
//
//    @GetMapping("/getConsultantByToken/{token}")
//    public ResponseEntity getConsultantByToken(@PathVariable String token) {
//
//        try {
//
//            Long ConsultantId = consultantService.getConsultantByToken(token);
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
//
//
//}
