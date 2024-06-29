package by.powerssolutions.vadarod.repository;

import static by.powerssolutions.vadarod.utils.TestConstant.CUR_ABBREVIATION;
import static by.powerssolutions.vadarod.utils.TestConstant.DATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import by.powerssolutions.vadarod.domain.Rate;
import by.powerssolutions.vadarod.util.RateTestBuilder;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
@RequiredArgsConstructor
class RateRepositoryTest {

    private final RateRepository rateRepository;

    @Nested
    class FindFirstByDate {

        @Test
        void findFirstByDateShouldReturnRate_whenItExistInDb() {
            Rate rate = RateTestBuilder.builder()
                    .build()
                    .buildRate();
            Rate saved = rateRepository.save(rate);
            Optional<Rate> expected = Optional.of(saved);

            Optional<Rate> actual = rateRepository.findFirstByDate(DATE);

            assertEquals(expected.get(), actual.get());
        }

        @Test
        void findFirstByDateShouldReturnEmptyOptional_whenItMissInDb() {
            Optional<Rate> actual = rateRepository.findFirstByDate(DATE);

            assertTrue(actual.isEmpty());
        }
    }

    @Nested
    class FindByDateAndCurAbbreviation {
        @Test
        void findByDateAndCurAbbreviationShouldReturnRate_whenItExistInDb() {
            Rate rate = RateTestBuilder.builder()
                    .build()
                    .buildRate();
            Rate saved = rateRepository.save(rate);
            Optional<Rate> expected = Optional.of(saved);

            Optional<Rate> actual = rateRepository.findByDateAndCurAbbreviation(DATE, CUR_ABBREVIATION);

            assertEquals(expected.get(), actual.get());
        }

        @Test
        void findByDateAndCurAbbreviationShouldReturnEmptyOptional_whenItMissInDb() {
            Optional<Rate> actual = rateRepository.findByDateAndCurAbbreviation(DATE, CUR_ABBREVIATION);

            assertTrue(actual.isEmpty());
        }
    }
}
