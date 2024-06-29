package by.powerssolutions.vadarod.util;

import static by.powerssolutions.vadarod.utils.TestConstant.CUR_ABBREVIATION;
import static by.powerssolutions.vadarod.utils.TestConstant.CUR_ID;
import static by.powerssolutions.vadarod.utils.TestConstant.CUR_NAME;
import static by.powerssolutions.vadarod.utils.TestConstant.CUR_OFFICIAL_RATE;
import static by.powerssolutions.vadarod.utils.TestConstant.CUR_SCALE;
import static by.powerssolutions.vadarod.utils.TestConstant.DATE;
import static by.powerssolutions.vadarod.utils.TestConstant.ID;
import static by.powerssolutions.vadarod.utils.TestConstant.STATUS;

import by.powerssolutions.vadarod.domain.Rate;
import by.powerssolutions.vadarod.dto.request.RateRequestDto;
import by.powerssolutions.vadarod.dto.response.RateResponseDto;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class RateTestBuilder {

    @Builder.Default
    private Long id = ID;

    @Builder.Default
    private Long curId = CUR_ID;

    @Builder.Default
    private LocalDate date = DATE;

    @Builder.Default
    private String curAbbreviation = CUR_ABBREVIATION;

    @Builder.Default
    private Integer curScale = CUR_SCALE;

    @Builder.Default
    private String curName = CUR_NAME;

    @Builder.Default
    private Double curOfficialRate = CUR_OFFICIAL_RATE;

    public Rate buildRate() {
        Rate rate = new Rate();
        rate.setId(id);
        rate.setCurId(curId);
        rate.setDate(date);
        rate.setCurAbbreviation(curAbbreviation);
        rate.setCurScale(curScale);
        rate.setCurName(curName);
        rate.setCurOfficialRate(curOfficialRate);
        return rate;
    }

    public RateRequestDto buildRateRequestDto() {
        RateRequestDto rate = new RateRequestDto();
        rate.setCurId(curId);
        rate.setDate(date);
        rate.setCurAbbreviation(curAbbreviation);
        rate.setCurScale(curScale);
        rate.setCurName(curName);
        rate.setCurOfficialRate(curOfficialRate);
        return rate;
    }

    public RateResponseDto buildRateResponseDto() {
        return new RateResponseDto(STATUS, curAbbreviation, curOfficialRate, date);
    }

}
