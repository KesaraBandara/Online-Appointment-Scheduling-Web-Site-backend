package com.thejobs.onlineappointmentschedulingwebsite.repo;

import com.thejobs.onlineappointmentschedulingwebsite.entity.Consultant;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule,Long> {

//    @Query(  "SELECT s FROM Schedule s WHERE s.consultant = :consultant " +
//            "AND s.time = :time " +
//            "AND s.day = :day " )
//    Schedule findByTokenTimeDay(
//            @Param("consultant") String token,
//            @Param("time") String time,
//            @Param("day") String day
//    );

    List<Schedule> getAllScheduleByConsultant(Optional<Consultant> consultant);


    List<Schedule> getScheduleByCountryAndJobType(String country, String jobType);

//    Schedule findByConsultantTimeDay(String time, String day, ConsultantDTO consultant);

    Schedule findByTimeAndDayAndConsultant(String time, String day, Consultant consultant);
}
