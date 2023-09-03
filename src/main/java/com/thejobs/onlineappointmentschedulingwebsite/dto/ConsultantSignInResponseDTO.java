package com.thejobs.onlineappointmentschedulingwebsite.dto;

public class ConsultantSignInResponseDTO {

    private String token;
    private String code;

    public ConsultantSignInResponseDTO() {
    }

    public ConsultantSignInResponseDTO(String token, String code) {
        this.token = token;
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ConsultantSignInResponseDTO{" +
                "token='" + token + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
