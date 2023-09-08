package com.thejobs.onlineappointmentschedulingwebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScheduleSummaryDTO {
    private long scheduleId;
    private String country;
    private String day;
    private String jobType;
    private String time;
    private long consultantId;

    // Constructors, getters, and setters for these fields
}

