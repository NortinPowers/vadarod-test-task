package by.powerssolutions.vadarod.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@FieldNameConstants
@Table(name = "rates")
public class Rate {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "cur_id")
    private Long curId;

    private LocalDate date;

    @Column(name = "cur_abbreviation")
    private String curAbbreviation;

    @Column(name = "cur_scale")
    private Integer curScale;

    @Column(name = "cur_name")
    private String curName;

    @Column(name = "cur_official_Rate")
    private Double curOfficialRate;
}
