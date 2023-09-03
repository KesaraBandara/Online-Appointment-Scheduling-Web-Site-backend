package com.thejobs.onlineappointmentschedulingwebsite.repo;

import com.thejobs.onlineappointmentschedulingwebsite.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule,Long> {

    @Query(  "SELECT s FROM Schedule s WHERE s.token = :token " +
            "AND s.time = :time " +
            "AND s.day = :day " )
    Schedule findByTokenTimeDay(
            @Param("token") String token,
            @Param("time") String time,
            @Param("day") String day
    );

    List<Schedule> getAllProductByToken(String token);

}
