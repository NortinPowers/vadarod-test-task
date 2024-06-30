package by.powerssolutions.vadarod.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

import by.powerssolutions.vadarod.model.BaseResponse;
import by.powerssolutions.vadarod.model.ExceptionResponse;
import by.powerssolutions.vadarod.service.RateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rates")
@Tag(name = "NBRB", description = "NBRB management API")
public class RateController {

    private final RateService rateService;

    @Operation(
            summary = "Saves the exchange rates for the entered date in the database",
            description = "Loads the data, saves it to the database and returns a string in response about the successful download. If the data for the requested date has already been uploaded, it informs you about it in the response line.",
            tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), mediaType = TEXT_PLAIN_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("/{date}")
    public ResponseEntity<String> getAllOnDate(@PathVariable String date) {
        String message = rateService.checkRatesOnDate(date);
        return ResponseEntity.ok().body(message);
    }

    @Operation(
            summary = "Returns the exchange rate for a specific currency on a specific date",
            description = "Returns data from the database, if available, otherwise sends a request to the Api",
            tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = BaseResponse.class)), mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = APPLICATION_JSON_VALUE)})})

    @GetMapping("/{date}/{code}")
    public ResponseEntity<BaseResponse> getAllOnDate(@PathVariable String date,
                                                     @PathVariable String code) {
        return rateService.checkRatesOnDateByCurrency(date, code);
    }
}
