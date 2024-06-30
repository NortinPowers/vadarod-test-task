package by.powerssolutions.vadarod.service.impl;

import static by.powerssolutions.vadarod.utils.Constants.LETTER_CODE_CURRENCY;
import static by.powerssolutions.vadarod.utils.Constants.RequestConstants.SUCCESS_RATES_UPLOAD_MESSAGE;
import static by.powerssolutions.vadarod.utils.Constants.RequestConstants.SUCCESS_RATES_UPLOAD_MESSAGE_IF_EXIST;
import static by.powerssolutions.vadarod.utils.TestConstant.CUR_ABBREVIATION;
import static by.powerssolutions.vadarod.utils.TestConstant.DATE;
import static by.powerssolutions.vadarod.utils.TestConstant.DATE_STRING;
import static by.powerssolutions.vadarod.utils.TestConstant.PERIODICITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.powerssolutions.vadarod.client.NbrbServiceClient;
import by.powerssolutions.vadarod.domain.Rate;
import by.powerssolutions.vadarod.dto.request.RateRequestDto;
import by.powerssolutions.vadarod.dto.response.RateResponseDto;
import by.powerssolutions.vadarod.exception.DataConvertException;
import by.powerssolutions.vadarod.mapper.RateMapper;
import by.powerssolutions.vadarod.model.BaseResponse;
import by.powerssolutions.vadarod.repository.RateRepository;
import by.powerssolutions.vadarod.service.RateService;
import by.powerssolutions.vadarod.util.RateTestBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
class RateServiceImplIntegrationTest {

    private final RateService rateService;
    private final ObjectMapper mapper = new ObjectMapper();

    {
        mapper.registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @MockBean
    private RateRepository rateRepository;

    @MockBean
    private RateMapper rateMapper;

    @MockBean
    private NbrbServiceClient nbrbServiceClient;

    @Nested
    class SaveAllOnDate {

        @Test
        @SneakyThrows
        void saveAllOnDateShouldSaveRates_whenAllCorrect() {
            RateRequestDto requestDto = RateTestBuilder.builder()
                    .build()
                    .buildRateRequestDto();
            List<RateRequestDto> dtos = List.of(requestDto);
            String data = mapper.writeValueAsString(dtos);
            Rate rate = RateTestBuilder.builder()
                    .build()
                    .buildRate();
            List<Rate> rates = List.of(rate);

            when(rateMapper.toDomain(requestDto))
                    .thenReturn(rate);
            when(rateRepository.saveAll(rates))
                    .thenReturn(rates);

            rateService.saveAllOnDate(data);
        }

        @Test
        void saveAllOnDateShouldThrowDataConvertException_whenIncorrectData() {
            DataConvertException expectedException = DataConvertException.of(RateRequestDto.class);
            String data = "Incorrect";

            DataConvertException actualException = assertThrows(DataConvertException.class, () -> rateService.saveAllOnDate(data));

            assertEquals(expectedException.getMessage(), actualException.getMessage());
            verify(rateMapper, never()).toDomain(any(RateRequestDto.class));
            verify(rateRepository, never()).saveAll(any(List.class));
        }

    }

    @Nested
    class CheckRatesOnDate {

        @Test
        void checkRatesOnDateShouldReturnSuccessUploadDbMessage_whenAllCorrectAndRateInDb() {
            Rate rate = RateTestBuilder.builder()
                    .build()
                    .buildRate();
            Optional<Rate> optionalRate = Optional.of(rate);

            when(rateRepository.findFirstByDate(DATE))
                    .thenReturn(optionalRate);
            String expected = SUCCESS_RATES_UPLOAD_MESSAGE_IF_EXIST;

            String actual = rateService.checkRatesOnDate(DATE_STRING);

            assertEquals(expected, actual);
            verify(nbrbServiceClient, never()).getAllOnDate(any(), any());
        }

        @Test
        void checkRatesOnDateShouldThrowDateTimeParseException_whenIncorrectDate() {
            String date = "Incorrect";
            DateTimeParseException expectedException = new DateTimeParseException("Text '" + date + "' could not be parsed at index 0", date, 0);

            DateTimeParseException actualException = assertThrows(DateTimeParseException.class, () -> rateService.checkRatesOnDate(date));

            assertEquals(expectedException.getMessage(), actualException.getMessage());
            verify(rateRepository, never()).findFirstByDate(any(LocalDate.class));
            verify(nbrbServiceClient, never()).getAllOnDate(any(), any());
            verify(rateMapper, never()).toDomain(any(RateRequestDto.class));
            verify(rateRepository, never()).saveAll(any(List.class));
        }

        @Test
        @SneakyThrows
        void checkRatesOnDateShouldReturnSuccessUploadApiMessage_whenAllCorrect() {
            RateRequestDto rate = RateTestBuilder.builder()
                    .build()
                    .buildRateRequestDto();
            List<RateRequestDto> rates = List.of(rate);
            Optional<Rate> optionalRate = Optional.empty();
            String response = mapper.writeValueAsString(rates);
            Optional<String> optionalResponse = Optional.of(response);
            ResponseEntity<String> responseEntity = ResponseEntity.of(optionalResponse);

            when(rateRepository.findFirstByDate(DATE))
                    .thenReturn(optionalRate);
            when(nbrbServiceClient.getAllOnDate(DATE_STRING, PERIODICITY))
                    .thenReturn(responseEntity);
            String expected = SUCCESS_RATES_UPLOAD_MESSAGE;

            String actual = rateService.checkRatesOnDate(DATE_STRING);

            assertEquals(expected, actual);
        }
    }

    @Nested
    class CheckRatesOnDateByCurrency {

        @Test
        void checkRatesOnDateByCurrencyShouldThrowDateTimeParseException_whenIncorrectDate() {
            String date = "Incorrect";
            String code = CUR_ABBREVIATION;
            DateTimeParseException expectedException = new DateTimeParseException("Text '" + date + "' could not be parsed at index 0", date, 0);

            DateTimeParseException actualException = assertThrows(DateTimeParseException.class, () -> rateService.checkRatesOnDateByCurrency(date, code));

            assertEquals(expectedException.getMessage(), actualException.getMessage());
            verify(rateRepository, never()).findFirstByDate(any(LocalDate.class));
            verify(nbrbServiceClient, never()).getOneOnDate(any(), any(), any());
            verify(rateRepository, never()).findByDateAndCurAbbreviation(any(), any());
            verify(rateMapper, never()).toResponse(any(Rate.class));
        }

        @Test
        void checkRatesOnDateByCurrencyShouldThrowRuntimeException_whenObjectShouldBeInDbButNotFound() {
            RuntimeException expectedException = new RuntimeException("The exchange rate for the specified date was not found");
            Rate rate = RateTestBuilder.builder()
                    .build()
                    .buildRate();
            Optional<Rate> optionalRate = Optional.of(rate);
            LocalDate localDate = DATE;
            String date = DATE_STRING;
            String code = CUR_ABBREVIATION;

            when(rateRepository.findFirstByDate(localDate))
                    .thenReturn(optionalRate);
            when(rateRepository.findByDateAndCurAbbreviation(localDate, code))
                    .thenReturn(Optional.empty());

            RuntimeException actualException = assertThrows(RuntimeException.class, () -> rateService.checkRatesOnDateByCurrency(date, code));

            assertEquals(expectedException.getMessage(), actualException.getMessage());
            verify(nbrbServiceClient, never()).getOneOnDate(any(), any(), any());
            verify(rateMapper, never()).toResponse(any(Rate.class));
        }

        @Test
        @SneakyThrows
        void checkRatesOnDateByCurrencyShouldThrowDataConvertException_whenIncorrectDataFromApi() {
            DataConvertException expectedException = DataConvertException.of(RateRequestDto.class);
            Optional<String> optionalResponse = Optional.of("Incorrect");
            ResponseEntity<String> responseEntity = ResponseEntity.of(optionalResponse);
            LocalDate localDate = DATE;
            String date = DATE_STRING;
            String code = CUR_ABBREVIATION;

            when(rateRepository.findFirstByDate(localDate))
                    .thenReturn(Optional.empty());
            when(nbrbServiceClient.getOneOnDate(code, date, LETTER_CODE_CURRENCY))
                    .thenReturn(responseEntity);

            RuntimeException actualException = assertThrows(RuntimeException.class, () -> rateService.checkRatesOnDateByCurrency(date, code));

            assertEquals(expectedException.getMessage(), actualException.getMessage());
            verify(rateRepository, never()).findByDateAndCurAbbreviation(any(), any());
            verify(rateMapper, never()).toResponse(any(Rate.class));
        }

        @Test
        void checkRatesOnDateByCurrencyShouldReturnOkResponseEntity_whenAllCorrectAndRateInDb() {
            Rate rate = RateTestBuilder.builder()
                    .build()
                    .buildRate();
            Optional<Rate> optionalRate = Optional.of(rate);
            LocalDate localDate = DATE;
            String date = DATE_STRING;
            String code = CUR_ABBREVIATION;
            RateResponseDto expected = RateTestBuilder.builder()
                    .build()
                    .buildRateResponseDto();

            when(rateRepository.findFirstByDate(localDate))
                    .thenReturn(optionalRate);
            when(rateRepository.findByDateAndCurAbbreviation(localDate, code))
                    .thenReturn(optionalRate);
            when(rateMapper.toResponse(optionalRate.get()))
                    .thenReturn(expected);

            ResponseEntity<BaseResponse> response = rateService.checkRatesOnDateByCurrency(date, code);

            RateResponseDto actual = (RateResponseDto) response.getBody();
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(RateResponseDto.Fields.currency, expected.getCurrency())
                    .hasFieldOrPropertyWithValue(RateResponseDto.Fields.officialRate, expected.getOfficialRate())
                    .hasFieldOrPropertyWithValue(RateResponseDto.Fields.date, expected.getDate());
            assertEquals(actual.getStatus(), expected.getStatus());
        }

        @Test
        @SneakyThrows
        void checkRatesOnDateByCurrencyShouldReturnOkResponseEntity_whenAllCorrectAndRateFromApi() {
            RateRequestDto rate = RateTestBuilder.builder()
                    .build()
                    .buildRateRequestDto();
            Optional<RateRequestDto> optionalRate = Optional.of(rate);
            LocalDate localDate = DATE;
            String date = DATE_STRING;
            String code = CUR_ABBREVIATION;
            RateResponseDto responseDto = RateTestBuilder.builder()
                    .build()
                    .buildRateResponseDto();
            String responseBody = mapper.writeValueAsString(rate);
            Optional<String> optionalResponse = Optional.of(responseBody);
            ResponseEntity<String> responseEntity = ResponseEntity.of(optionalResponse);

            when(rateRepository.findFirstByDate(localDate))
                    .thenReturn(Optional.empty());
            when(nbrbServiceClient.getOneOnDate(code, date, LETTER_CODE_CURRENCY))
                    .thenReturn(responseEntity);
            when(rateMapper.toResponse(optionalRate.get()))
                    .thenReturn(responseDto);

            ResponseEntity<BaseResponse> response = rateService.checkRatesOnDateByCurrency(date, code);

            RateResponseDto actual = (RateResponseDto) response.getBody();
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(RateResponseDto.Fields.currency, responseDto.getCurrency())
                    .hasFieldOrPropertyWithValue(RateResponseDto.Fields.officialRate, responseDto.getOfficialRate())
                    .hasFieldOrPropertyWithValue(RateResponseDto.Fields.date, responseDto.getDate());
            assertEquals(actual.getStatus(), responseDto.getStatus());
        }
    }
}
