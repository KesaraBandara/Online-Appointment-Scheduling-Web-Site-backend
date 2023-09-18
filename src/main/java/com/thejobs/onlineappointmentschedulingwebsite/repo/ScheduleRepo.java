package com.thejobs.onlineappointmentschedulingwebsite.repo;

import com.thejobs.onlineappointmentschedulingwebsite.entity.Consultant;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule,Long> {



    List<Schedule> getAllScheduleByConsultant(Optional<Consultant> consultant);


    List<Schedule> getScheduleByCountryAndJobType(String country, String jobType);


    Schedule findByTimeAndDayAndConsultant(String time, String day, Consultant consultant);
}
