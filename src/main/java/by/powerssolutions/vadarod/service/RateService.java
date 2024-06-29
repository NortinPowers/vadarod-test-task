package by.powerssolutions.vadarod.service;

import by.powerssolutions.vadarod.model.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface RateService {

    void saveAllOnDate(String data);

    String checkRatesOnDate(String date);

    ResponseEntity<BaseResponse> checkRatesOnDateByCurrency(String date, String code);
}
