package by.powerssolutions.vadarod.service.impl;

import static by.powerssolutions.vadarod.utils.Constants.LETTER_CODE_CURRENCY;
import static by.powerssolutions.vadarod.utils.Constants.RequestConstants.SUCCESS_RATES_UPLOAD_MESSAGE;
import static by.powerssolutions.vadarod.utils.Constants.RequestConstants.SUCCESS_RATES_UPLOAD_MESSAGE_IF_EXIST;
import static by.powerssolutions.vadarod.utils.ObjectUtils.convertDate;
import static by.powerssolutions.vadarod.utils.ObjectUtils.extractResponseDto;
import static by.powerssolutions.vadarod.utils.ObjectUtils.extractResponseListDto;

import by.powerssolutions.vadarod.client.NbrbServiceClient;
import by.powerssolutions.vadarod.domain.Rate;
import by.powerssolutions.vadarod.dto.request.RateRequestDto;
import by.powerssolutions.vadarod.dto.response.RateResponseDto;
import by.powerssolutions.vadarod.mapper.RateMapper;
import by.powerssolutions.vadarod.model.BaseResponse;
import by.powerssolutions.vadarod.repository.RateRepository;
import by.powerssolutions.vadarod.service.RateService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;
    private final RateMapper rateMapper;
    private final NbrbServiceClient nbrbServiceClient;

    @Value("${request.value.periodicity}")
    private Integer periodicity;

    /**
     * Метод для сохранения всех курсов валюты в базе данных для указанной даты.
     *
     * @param data String, дата определяющая данные курсов валюты для сохранения
     */
    @Override
    @Transactional
    public void saveAllOnDate(String data) {
        List<RateRequestDto> rateRequestDtos = extractResponseListDto(data);
        List<Rate> rates = rateRequestDtos.stream()
                .map(rateMapper::toDomain)
                .toList();
        rateRepository.saveAll(rates);
    }

    /**
     * Сохраняет полученные значения на основе проверки наличия данных о сохраненных курсах валюты на переданную дату в БД.
     *
     * @param date String, дата переданная пользователем
     * @return сообщение для отображения в зависимости от наличия данных о курсе на переданную дату в базе
     */
    @Override
    public String checkRatesOnDate(String date) {
        LocalDate responseDate = convertDate(date);
        Optional<Rate> firstByDate = rateRepository.findFirstByDate(responseDate);
        if (firstByDate.isEmpty()) {
            ResponseEntity<String> response = nbrbServiceClient.getAllOnDate(date, periodicity);
            saveAllOnDate(response.getBody());
            return SUCCESS_RATES_UPLOAD_MESSAGE;
        } else {
            return SUCCESS_RATES_UPLOAD_MESSAGE_IF_EXIST;
        }
    }

    /**
     * Возвращает ответ со значением курса валюты на переданную дату из API или БД.
     *
     * @param date String, дата переданная пользователем
     * @param code String, буквенный код валюты
     * @return ответ содержащий курс переданной валюты на переданную дату
     */
    @Override
    public ResponseEntity<BaseResponse> checkRatesOnDateByCurrency(String date, String code) {
        LocalDate responseDate = convertDate(date);
        Optional<Rate> firstByDate = rateRepository.findFirstByDate(responseDate);
        RateResponseDto responseDto;
        if (firstByDate.isEmpty()) {
            responseDto = getRateResponseDtoFromApi(date, code);
        } else {
            responseDto = getRateResponseDtoFromDb(code, responseDate);
        }
        responseDto.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Возвращает ответ со значением курса валюты на переданную дату из БД.
     *
     * @param code         String, буквенный код валюты
     * @param responseDate LocalDate, дата для которой нужен курс
     * @return responseDto RateResponseDto объект содержащий данные о курсе переданной валюты на переданную дату
     * @throws RuntimeException Если объект с указанными параметрами кода и даты не найден.
     */
    private RateResponseDto getRateResponseDtoFromDb(String code, LocalDate responseDate) {
        RateResponseDto responseDto;
        Optional<Rate> optionalRate = rateRepository.findByDateAndCurAbbreviation(responseDate, code);
        if (optionalRate.isPresent()) {
            responseDto = rateMapper.toResponse(optionalRate.get());
        } else {
            throw new RuntimeException("The exchange rate for the specified date was not found");
        }
        return responseDto;
    }

    /**
     * Возвращает ответ со значением курса валюты на переданную дату из API.
     *
     * @param date String, дата переданная пользователем
     * @param code String, буквенный код валюты
     * @return responseDto RateResponseDto объект содержащий данные о курсе переданной валюты на переданную дату
     */
    private RateResponseDto getRateResponseDtoFromApi(String date, String code) {
        ResponseEntity<String> response = nbrbServiceClient.getOneOnDate(code, date, LETTER_CODE_CURRENCY);
        RateRequestDto rateRequestDto = extractResponseDto(response.getBody());
        return rateMapper.toResponse(rateRequestDto);
    }
}
