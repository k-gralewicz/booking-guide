package pl.gralewicz.kamil.java.app.bookingguide.controller.model;

import pl.gralewicz.kamil.java.app.bookingguide.api.VisitStatusType;

import java.time.LocalDateTime;

public class VisitStatus {

    private VisitStatusType status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public VisitStatus() {
    }

    public VisitStatus(VisitStatusType status, LocalDateTime startDate, LocalDateTime endDate) {
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public VisitStatusType getStatus() {
        return status;
    }

    public void setStatus(VisitStatusType status) {
        this.status = status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "VisitStatus{" +
                "status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}