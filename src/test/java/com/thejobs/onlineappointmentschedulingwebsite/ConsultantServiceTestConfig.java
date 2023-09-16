package com.thejobs.onlineappointmentschedulingwebsite;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.thejobs.onlineappointmentschedulingwebsite.service.AuthenticationService;

@Configuration
public class ConsultantServiceTestConfig {

    @MockBean
    private AuthenticationService authenticationService;

    // You can add more mock beans if needed
}
