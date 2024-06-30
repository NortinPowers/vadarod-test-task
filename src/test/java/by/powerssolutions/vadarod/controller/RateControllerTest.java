package by.powerssolutions.vadarod.controller;

import static by.powerssolutions.vadarod.utils.Constants.RequestConstants.SUCCESS_RATES_UPLOAD_MESSAGE;
import static by.powerssolutions.vadarod.utils.TestConstant.CUR_ABBREVIATION;
import static by.powerssolutions.vadarod.utils.TestConstant.DATE;
import static by.powerssolutions.vadarod.utils.TestConstant.DATE_STRING;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.common.ContentTypes.APPLICATION_JSON;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.OK;

import by.powerssolutions.vadarod.dto.request.RateRequestDto;
import by.powerssolutions.vadarod.dto.response.RateResponseDto;
import by.powerssolutions.vadarod.model.BaseResponse;
import by.powerssolutions.vadarod.repository.RateRepository;
import by.powerssolutions.vadarod.util.RateTestBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@ActiveProfiles("test")
@RequiredArgsConstructor
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RateControllerTest {

    private final RateController rateController;
    private ObjectMapper mapper;

    @LocalServerPort
    private Integer port;

    @MockBean
    private RateRepository rateRepository;

    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.openfeign.client.config.nbrb-service.url", wireMock::baseUrl);
    }

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    @SneakyThrows
    void getAllOnDateShouldReturnUploadApiMessage_whenCalledAndDbEmpty() {
        RateRequestDto rate = RateTestBuilder.builder()
                .build()
                .buildRateRequestDto();
        List<RateRequestDto> rates = List.of(rate);

        wireMock.stubFor(get(urlEqualTo("/?ondate=2024-12-10&periodicity=0"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(mapper.writeValueAsString(rates))));

        ResponseEntity<String> actual = rateController.getAllOnDate(DATE_STRING);

        assertThat(actual.getStatusCode()).isEqualTo(OK);
        assertThat(actual.getBody()).isEqualTo(SUCCESS_RATES_UPLOAD_MESSAGE);
    }

    @Test
    @SneakyThrows
    void testGetAllOnDateShouldReturnCorrectResponse_whenCalledAndDbEmpty() {
        String date = DATE_STRING;
        String code = CUR_ABBREVIATION;
        RateResponseDto expected = RateTestBuilder.builder()
                .build()
                .buildRateResponseDto();
        RateRequestDto requestDto = RateTestBuilder.builder()
                .build()
                .buildRateRequestDto();

        wireMock.stubFor(get(urlEqualTo("/USD?ondate=2024-12-10&parammode=2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(mapper.writeValueAsString(requestDto))));

        when(rateRepository.findFirstByDate(DATE))
                .thenReturn(Optional.empty());

        ResponseEntity<BaseResponse> actual = rateController.getAllOnDate(date, code);

        RateResponseDto body = (RateResponseDto) actual.getBody();
        assertThat(body)
                .hasFieldOrPropertyWithValue(RateResponseDto.Fields.currency, expected.getCurrency())
                .hasFieldOrPropertyWithValue(RateResponseDto.Fields.officialRate, expected.getOfficialRate())
                .hasFieldOrPropertyWithValue(RateResponseDto.Fields.date, expected.getDate());
        assertEquals(body.getStatus(), expected.getStatus());
    }
}
