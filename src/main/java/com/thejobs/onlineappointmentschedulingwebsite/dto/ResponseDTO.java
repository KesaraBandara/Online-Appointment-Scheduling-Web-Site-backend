package com.thejobs.onlineappointmentschedulingwebsite.dto;

import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDTO {
    private String code;
    private String message;
    private Object content;
    private String sessionId;  // New field for session ID
    private String consultantCookieId;  // New field for consultant's cookie ID


}
