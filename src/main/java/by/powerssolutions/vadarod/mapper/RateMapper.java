package by.powerssolutions.vadarod.mapper;

import by.powerssolutions.vadarod.domain.Rate;
import by.powerssolutions.vadarod.dto.request.RateRequestDto;
import by.powerssolutions.vadarod.dto.response.RateResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CustomMapperConfig.class)
public interface RateMapper {

    /**
     * Преобразует объект типа {@link RateRequestDto} в объект {@link Rate}.
     *
     * @param dto Объект типа {@link RateRequestDto}, который требуется преобразовать в {@link Rate}.
     * @return Объект {@link Rate}, созданный на основе данных из объекта {@link RateRequestDto}.
     */
    Rate toDomain(RateRequestDto dto);

    @Mapping(source = "curAbbreviation", target = "currency")
    @Mapping(source = "curOfficialRate", target = "officialRate")
    RateResponseDto toResponse(RateRequestDto dto);

    @Mapping(source = "curAbbreviation", target = "currency")
    RateResponseDto toResponse(Rate rate);
}
