package com.thejobs.onlineappointmentschedulingwebsite.dto;

public class JobDTO {
    private long id;
    private String jobType;
    private ConsultantDTO consultantId;
    private TimeDTO timeId;

    public JobDTO() {
    }

    public JobDTO(long id, String jobType, ConsultantDTO consultantId, TimeDTO timeId) {
        this.id = id;
        this.jobType = jobType;
        this.consultantId = consultantId;
        this.timeId = timeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public ConsultantDTO getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(ConsultantDTO consultantId) {
        this.consultantId = consultantId;
    }

    public TimeDTO getTimeId() {
        return timeId;
    }

    public void setTimeId(TimeDTO timeId) {
        this.timeId = timeId;
    }

    @Override
    public String toString() {
        return "JobDTO{" +
                "id=" + id +
                ", jobType='" + jobType + '\'' +
                ", consultantId=" + consultantId +
                ", timeId=" + timeId +
                '}';
    }
}
