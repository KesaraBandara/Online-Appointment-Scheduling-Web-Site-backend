package com.thejobs.onlineappointmentschedulingwebsite.repo;

import com.thejobs.onlineappointmentschedulingwebsite.dto.ConsultantDTO;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Appointment;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    Appointment findByConsultantAndTimeAndDate(Consultant consultant, String time, String date);
}

