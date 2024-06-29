package by.powerssolutions.vadarod.service.impl;

import static by.powerssolutions.vadarod.utils.Constants.RequestConstants.SUCCESS_RATES_UPLOAD_MESSAGE;
import static by.powerssolutions.vadarod.utils.Constants.RequestConstants.SUCCESS_RATES_UPLOAD_MESSAGE_IF_EXIST;
import static by.powerssolutions.vadarod.utils.ObjectUtils.convertDate;
import static by.powerssolutions.vadarod.utils.ObjectUtils.extractResponse;

import by.powerssolutions.vadarod.client.NbrbServiceClient;
import by.powerssolutions.vadarod.domain.Rate;
import by.powerssolutions.vadarod.dto.RateDto;
import by.powerssolutions.vadarod.mapper.RateMapper;
import by.powerssolutions.vadarod.repository.RateRepository;
import by.powerssolutions.vadarod.service.RateService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
        List<RateDto> rateDtos = extractResponse(data);
        List<Rate> rates = rateDtos.stream()
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
}
