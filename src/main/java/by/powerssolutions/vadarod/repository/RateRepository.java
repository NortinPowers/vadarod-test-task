package by.powerssolutions.vadarod.repository;

import by.powerssolutions.vadarod.domain.Rate;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    Optional<Rate> findFirstByDate(LocalDate date);

    Optional<Rate> findByDateAndCurAbbreviation(LocalDate date, String code);
}
