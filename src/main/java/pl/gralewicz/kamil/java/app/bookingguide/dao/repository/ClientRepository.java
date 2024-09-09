package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
// TODO: 21.08.2024 poprawić  Repository