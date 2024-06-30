package by.powerssolutions.vadarod.repository;

import by.powerssolutions.vadarod.domain.Rate;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    /**
     * Метод для поиска в базе первого объекта по заданной дате.
     *
     * @param date {@link String}, дата переданная пользователем
     * @return Объект {@link Optional <{@link Rate}>}, соответствующий заданной дате.
     */
    Optional<Rate> findFirstByDate(LocalDate date);

    /**
     * Метод для поиска в базе объекта по заданной дате и коду валюты.
     *
     * @param date date {@link String}, дата переданная пользователем
     * @param code {@link String}, буквенный код валюты
     * @return Объект {@link Optional <{@link Rate}>}, соответствующий заданной дате и коду валюты.
     */
    Optional<Rate> findByDateAndCurAbbreviation(LocalDate date, String code);
}
