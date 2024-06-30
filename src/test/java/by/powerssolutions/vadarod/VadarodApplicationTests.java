package by.powerssolutions.vadarod;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import by.powerssolutions.vadarod.controller.RateController;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
class VadarodApplicationTests {

    private final RateController rateController;

    @Test
    void rateControllerMustBeNotNull_whenContextLoaded() {
        assertThat(rateController).isNotNull();
    }

}
