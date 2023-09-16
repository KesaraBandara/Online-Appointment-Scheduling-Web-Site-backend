package com.thejobs.onlineappointmentschedulingwebsite.repo;

import com.thejobs.onlineappointmentschedulingwebsite.entity.Appointment;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    Appointment findByConsultantAndTimeAndDate(Consultant consultant, String time, String date);

//    List<Appointment> getAppointmentById(String id);

//    List<Appointment> getAppointmentByconsultant(String id);

    List<Appointment> getAppointmentByConsultant(Optional<Consultant> consultant );
}

