package by.powerssolutions.vadarod.dto;

import java.time.LocalDateTime;
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
public class CurrencyResponseDto {

    private Long curId;
    private Long curParentId;
    private String curCode;
    private String curAbbreviation;
    private String curName;
    private String curNameBel;
    private String curNameEng;
    private String curQuotName;
    private String curQuotNameBel;
    private String curQuotNameEng;
    private String curNameMulti;
    private String curNameBelMulti;
    private String curNameEngMulti;
    private Long curScale;
    private Integer curPeriodicity;
    private LocalDateTime curDateStart;
    private LocalDateTime curDateEnd;
}
