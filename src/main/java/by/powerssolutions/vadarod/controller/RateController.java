package by.powerssolutions.vadarod.controller;

import by.powerssolutions.vadarod.client.NbrbServiceClient;
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

    private final NbrbServiceClient nbrbServiceClient;
    private final RateService rateService;

    @GetMapping("/{date}")
    public ResponseEntity<String> getAllOnDate(@PathVariable String date) {
        ResponseEntity<String> response = nbrbServiceClient.getAllOnDate(date, 1);
        rateService.saveAllOnDate(response.getBody());
        return ResponseEntity.ok().body("Currency exchange rates for the selected date have been successfully uploaded");
    }
}
