package by.powerssolutions.vadarod.dto.response;

import by.powerssolutions.vadarod.model.BaseResponse;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RateResponseDto extends BaseResponse {

    private String currency;
    private Double officialRate;
    private LocalDate date;

    public RateResponseDto(Integer status, String currency, Double curOfficialRate, LocalDate date) {
        super(status);
        this.currency = currency;
        this.officialRate = curOfficialRate;
        this.date = date;
    }
}
