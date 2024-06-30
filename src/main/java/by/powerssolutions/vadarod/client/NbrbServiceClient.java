package by.powerssolutions.vadarod.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Retry(name = "nbrb-service-retry")
@FeignClient(name = "nbrb-service")
@CircuitBreaker(name = "nbrb-service-breaker")
public interface NbrbServiceClient {

    /**
     * Возвращает строку (json) со списком курсов валюты на выбранную дату.
     *
     * @param ondate      {@link String}, дата переданная пользователем
     * @param periodicity {@link Integer}, периодичность установления курса (0 – ежедневно, 1 – ежемесячно)
     * @return Ответ строку (json) {@link String}, представляющая список курсов валюты за выбранную дату.
     */
    @GetMapping
    ResponseEntity<String> getAllOnDate(@RequestParam("ondate") String ondate,
                                        @RequestParam("periodicity") Integer periodicity);

    /**
     * Возвращает строку (json) с курсом указанной валюты на выбранную дату.
     *
     * @param code      {@link String},буквенный код валюты (API: Cur_Abbreviation)
     * @param ondate    {@link String}, дата переданная пользователем
     * @param parammode {@link Integer}, формат аргумента code (API: Cur_Abbreviation)
     * @return Ответ строку (json) {@link String}, представляющая курс выбранной валюты за выбранную дату.
     */
    @GetMapping("/{code}")
    ResponseEntity<String> getOneOnDate(@PathVariable String code,
                                        @RequestParam("ondate") String ondate,
                                        @RequestParam("parammode") Integer parammode);
}
