package by.powerssolutions.vadarod.controller;

import by.powerssolutions.vadarod.client.NbrbServiceClient;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rates")
public class CurrencyController {

    private final NbrbServiceClient nbrbServiceClient;

    @GetMapping
    public ResponseEntity<List<Object>> getAll() {
        return nbrbServiceClient.getAll();
    }

}
