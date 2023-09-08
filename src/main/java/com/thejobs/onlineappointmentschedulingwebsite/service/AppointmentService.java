package com.thejobs.onlineappointmentschedulingwebsite.service;

import com.thejobs.onlineappointmentschedulingwebsite.dto.AppointmentDTO;
import com.thejobs.onlineappointmentschedulingwebsite.dto.AppointmentVerifyDTO;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Appointment;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Consultant;
import com.thejobs.onlineappointmentschedulingwebsite.repo.AppointmentRepo;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AppointmentService {
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    EmailService emailService;


    public String saveAppointment(AppointmentDTO appointmentDTO) {
        try {
            Appointment appointmentEntity = modelMapper.map(appointmentDTO, Appointment.class);

            // Check for duplicates based on the time value
            Consultant consultant = modelMapper.map(appointmentDTO.getConsultant(), Consultant.class);
            Appointment existingAppointment = appointmentRepo.findByConsultantAndTimeAndDate(consultant, appointmentDTO.getTime(), appointmentDTO.getDate());

            if (existingAppointment != null) {
                return VarList.RSP_DUPLICATED;
            }

            // Save the appointmentEntity
            appointmentRepo.save(appointmentEntity);
            return VarList.RSP_SUCCESS;
        } catch (Exception e) {
            // Handle the exception, log it, and return an appropriate response
            e.printStackTrace(); // You can replace this with proper logging
            return VarList.RSP_ERROR;
        }
    }


//    public void updateActivity(Long appointmentId, AppointmentVerifyDTO appointmentVerifyDTO) {
//        // Retrieve the appointment entity by its ID
//        Appointment appointment = appointmentRepo.findById(appointmentId)
//                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + appointmentId));
//
//        // Update the active state of the appointment entity
//        appointment.setActiveState(appointmentVerifyDTO.isConfirm());
//
//        // Save the updated appointment entity back to the database
//        appointmentRepo.save(appointment);
//    }

    public void updateActivity(Long appointmentId, AppointmentVerifyDTO appointmentVerifyDTO) {
        // Retrieve the appointment entity by its ID
        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + appointmentId));

        if (appointmentVerifyDTO.isConfirm()) {
            appointment.setActiveState(true);
//            sendConfirmationEmailToUser(appointment.getUser().getEmail());
            // Send confirmation email to the user
            emailService.sendConfirmationEmail(appointment.getUser().getEmail());
        } else if (appointmentVerifyDTO.isNonConfirm()) {
            // If 'nonConfirm' is true, delete the appointment
            appointmentRepo.delete(appointment);

            // Send cancellation email to the user
            emailService.sendCancellationEmail(appointment.getUser().getEmail());
        }
    }


//    private void sendConfirmationEmailToUser(String userEmail) {
//        // Call the email service to send the confirmation email
//        emailService.sendConfirmationEmail(userEmail);
//    }


}
