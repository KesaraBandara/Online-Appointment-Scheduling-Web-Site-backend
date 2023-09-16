package com.thejobs.onlineappointmentschedulingwebsite;
import com.thejobs.onlineappointmentschedulingwebsite.dto.AppointmentDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.AppointmentVerifyDTO;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Appointment;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Consultant;
import com.thejobs.onlineappointmentschedulingwebsite.repo.AppointmentRepo;
import com.thejobs.onlineappointmentschedulingwebsite.repo.ConsultantRepo;
import com.thejobs.onlineappointmentschedulingwebsite.service.AppointmentService;
import com.thejobs.onlineappointmentschedulingwebsite.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepo appointmentRepo;

    @Mock
    private EmailService emailService;

    @Mock
    private ConsultantRepo consultantRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveAppointmentSuccess() {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        // Set appointmentDTO properties

        Consultant consultant = new Consultant();
        // Set consultant properties

        when(appointmentRepo.findByConsultantAndTimeAndDate(any(), any(), any())).thenReturn(null);
        when(appointmentRepo.save(any())).thenReturn(new Appointment());

        String result = appointmentService.saveAppointment(appointmentDTO);

        assertEquals("00", result); // Check the expected result code
        verify(appointmentRepo, times(1)).save(any()); // Verify that save was called once
    }

    @Test
    public void testSaveAppointmentDuplicate() {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        // Set appointmentDTO properties

        Consultant consultant = new Consultant();
        // Set consultant properties

        when(appointmentRepo.findByConsultantAndTimeAndDate(any(), any(), any())).thenReturn(new Appointment());

        String result = appointmentService.saveAppointment(appointmentDTO);

        assertEquals("06", result); // Check the expected result code for duplicate
        verify(appointmentRepo, never()).save(any()); // Verify that save was never called
    }

    @Test
    public void testSaveAppointmentError() {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        // Set appointmentDTO properties

        Consultant consultant = new Consultant();
        // Set consultant properties

        when(appointmentRepo.findByConsultantAndTimeAndDate(any(), any(), any())).thenReturn(null);
        when(appointmentRepo.save(any())).thenThrow(new RuntimeException());

        String result = appointmentService.saveAppointment(appointmentDTO);

        assertEquals("99", result); // Check the expected result code for error
        verify(appointmentRepo, times(1)).save(any()); // Verify that save was called once
    }

    // Add more test methods for other service methods as needed
}
