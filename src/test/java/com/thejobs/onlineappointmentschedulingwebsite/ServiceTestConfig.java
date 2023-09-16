package com.thejobs.onlineappointmentschedulingwebsite;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import com.thejobs.onlineappointmentschedulingwebsite.service.AuthenticationService;

@Configuration
public class ServiceTestConfig {

    @MockBean
    private AuthenticationService authenticationService;

}
