package pl.gralewicz.kamil.java.app.bookingguide.dao.entity;

import jakarta.persistence.*;
import pl.gralewicz.kamil.java.app.bookingguide.api.VisitStatusType;

import java.time.LocalDateTime;

@Entity
@Table(name = "VISIT_STATUSES")
public class VisitStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false)
    private VisitEntity visit;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private VisitStatusType status;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    public VisitStatusEntity() {
    }

    public VisitStatusEntity(VisitEntity visit, VisitStatusType status, LocalDateTime startDate) {
        this.visit = visit;
        this.status = status;
        this.startDate = startDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VisitEntity getVisit() {
        return visit;
    }

    public void setVisit(VisitEntity visit) {
        this.visit = visit;
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
}