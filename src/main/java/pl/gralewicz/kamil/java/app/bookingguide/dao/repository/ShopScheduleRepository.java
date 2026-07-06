package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopScheduleEntity;

@Repository
public interface ShopScheduleRepository extends JpaRepository <ShopScheduleEntity, Long> {
}
