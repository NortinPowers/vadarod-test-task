package by.powerssolutions.vadarod.controller;

import by.powerssolutions.vadarod.model.BaseResponse;
import by.powerssolutions.vadarod.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rates")
public class RateController {

    private final RateService rateService;

    @GetMapping("/{date}")
    public ResponseEntity<String> getAllOnDate(@PathVariable String date) {
        String message = rateService.checkRatesOnDate(date);
        return ResponseEntity.ok().body(message);
    }

    @GetMapping("/{date}/{code}")
    public ResponseEntity<BaseResponse> getAllOnDate(@PathVariable String date,
                                                     @PathVariable String code) {
        return rateService.checkRatesOnDateByCurrency(date, code);
    }
}
