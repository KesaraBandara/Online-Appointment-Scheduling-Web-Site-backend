package com.thejobs.onlineappointmentschedulingwebsite.controller;

import com.thejobs.onlineappointmentschedulingwebsite.dto.CountryDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.ResponseDTO;
import com.thejobs.onlineappointmentschedulingwebsite.service.CountryService;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/country")
public class CountryController {

    private final CountryService countryService;
    private final ResponseDTO responseDTO;

    @Autowired
    public CountryController(CountryService countryService, ResponseDTO responseDTO) {
        this.countryService = countryService;
        this.responseDTO = responseDTO;
    }

    @PostMapping("/saveCountry")
    public ResponseEntity<ResponseDTO> saveCountry(@RequestBody CountryDTO countryDTO) {
        try {
            String res = countryService.saveCountry(countryDTO);
            switch (res) {
                case "00":
                    return buildResponse(VarList.RSP_SUCCESS, "Success", countryDTO, HttpStatus.ACCEPTED);
                case "06":
                    return buildResponse(VarList.RSP_DUPLICATED, "Already added", countryDTO, HttpStatus.BAD_REQUEST);
                default:
                    return buildResponse(VarList.RSP_FAIL, "Error", null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            return buildResponse(VarList.RSP_ERROR, exception.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllCountries")
    public ResponseEntity<ResponseDTO> getAllCountries() {
        try {
            List<CountryDTO> countryDTOList = countryService.getAllCountries();
            return buildResponse(VarList.RSP_SUCCESS, "Success", countryDTOList, HttpStatus.ACCEPTED);
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
//import com.thejobs.onlineappointmentschedulingwebsite.dto.CountryDTO;
//import com.thejobs.onlineappointmentschedulingwebsite.dto.ResponseDTO;
//import com.thejobs.onlineappointmentschedulingwebsite.service.CountryService;
//import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/v1/country")
//public class CountryController {
//
//    @Autowired
//    private CountryService countryService;
//    @Autowired
//    private ResponseDTO responseDTO;
//    @PostMapping(value = "/saveCountry")
//    public ResponseEntity saveCountry(@RequestBody CountryDTO countryDTO){
//
//
//        try{
//
//            String res = countryService.saveCountry(countryDTO);
//            if (res.equals("00")){
//                responseDTO.setCode(VarList.RSP_SUCCESS);
//                responseDTO.setMessage("Success");
//                responseDTO.setContent(countryDTO);
//                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//            }
//            else if (res.equals("06")){
//                responseDTO.setCode(VarList.RSP_DUPLICATED);
//                responseDTO.setMessage("Already added");
//                responseDTO.setContent(countryDTO);
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
//    @GetMapping("/getAllCountries")
//    public ResponseEntity getAllCountries() {
//
//        try {
//
//            List<CountryDTO> countryDTOList = countryService.getAllCountries();
//            responseDTO.setCode(VarList.RSP_SUCCESS);
//            responseDTO.setMessage("Success");
//            responseDTO.setContent(countryDTOList);
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
