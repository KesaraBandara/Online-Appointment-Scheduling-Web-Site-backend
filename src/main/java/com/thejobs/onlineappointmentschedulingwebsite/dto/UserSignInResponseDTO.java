package com.thejobs.onlineappointmentschedulingwebsite.dto;

public class UserSignInResponseDTO {

    private String userToken;
    private String code;

    public UserSignInResponseDTO() {
    }

    public UserSignInResponseDTO(String userToken, String code) {
        this.userToken = userToken;
        this.code = code;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "UserSignInResponseDTO{" +
                "userToken='" + userToken + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
