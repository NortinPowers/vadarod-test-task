package by.powerssolutions.vadarod.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Retry(name = "nbrb-service-retry")
@FeignClient(name = "nbrb-service")
@CircuitBreaker(name = "nbrb-service-breaker")
public interface NbrbServiceClient {

    @GetMapping
    ResponseEntity<List<Object>> getAll();
}
