package com.thejobs.onlineappointmentschedulingwebsite.dto;

public class TimeDTO {
    private long id;
    private String time;
    private ConsultantDTO consultantId;

    public TimeDTO() {
    }

    public TimeDTO(long id, String time, ConsultantDTO consultantId) {
        this.id = id;
        this.time = time;
        this.consultantId = consultantId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ConsultantDTO getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(ConsultantDTO consultantId) {
        this.consultantId = consultantId;
    }

    @Override
    public String toString() {
        return "TimeDTO{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", consultantId=" + consultantId +
                '}';
    }
}
