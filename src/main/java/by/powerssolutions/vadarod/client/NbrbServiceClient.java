package by.powerssolutions.vadarod.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Retry(name = "nbrb-service-retry")
@FeignClient(name = "nbrb-service")
@CircuitBreaker(name = "nbrb-service-breaker")
public interface NbrbServiceClient {

    /**
     * Возвращает строку (json) со списком курсов валюты за выбранную дату.
     *
     * @return Ответ строку (json) {@link String}, представляющая список курсов валюты за выбранную дату.
     */
    @GetMapping
    ResponseEntity<String> getAllOnDate(@RequestParam("ondate") String ondate,
                                        @RequestParam("periodicity") Integer periodicity);
}
