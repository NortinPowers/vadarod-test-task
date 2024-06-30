package by.powerssolutions.vadarod.dto.response;

import by.powerssolutions.vadarod.model.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
public class RateResponseDto extends BaseResponse {

    @Schema(description = "currency", example = "USD")
    private String currency;

    @Schema(description = "officialRate", example = "5.1624")
    private Double officialRate;

    @Schema(description = "date", example = "2024-12-10")
    private LocalDate date;

    public RateResponseDto(Integer status, String currency, Double curOfficialRate, LocalDate date) {
        super(status);
        this.currency = currency;
        this.officialRate = curOfficialRate;
        this.date = date;
    }
}
