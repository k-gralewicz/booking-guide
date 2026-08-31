package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    @Query("SELECT s FROM ServiceEntity s " +
            "WHERE s.id = :serviceId " +
            "AND :targetDate BETWEEN s.startDate AND s.endDate")
    Optional<ServiceEntity> findServiceForDate(
            @Param("serviceId") Long serviceId,
            @Param("targetDate") LocalDate targetDate
    );
}