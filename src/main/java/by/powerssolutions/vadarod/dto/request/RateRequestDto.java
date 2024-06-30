package by.powerssolutions.vadarod.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class RateRequestDto {

    @JsonProperty("Cur_ID")
    @Schema(description = "curId", example = "1")
    private Long curId;

    @JsonProperty("Date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Schema(description = "date", example = "2024-12-10")
    private LocalDate date;

    @JsonProperty("Cur_Abbreviation")
    @Schema(description = "curAbbreviation", example = "USD")
    private String curAbbreviation;

    @JsonProperty("Cur_Scale")
    @Schema(description = "curScale", example = "1")
    private Integer curScale;

    @JsonProperty("Cur_Name")
    @Schema(description = "curName", example = "Доллар США")
    private String curName;

    @JsonProperty("Cur_OfficialRate")
    @Schema(description = "curOfficialRate", example = "5.1624")
    private Double curOfficialRate;
}
