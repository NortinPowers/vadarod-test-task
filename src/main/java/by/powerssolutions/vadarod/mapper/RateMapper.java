package by.powerssolutions.vadarod.mapper;

import by.powerssolutions.vadarod.domain.Rate;
import by.powerssolutions.vadarod.dto.RateDto;
import org.mapstruct.Mapper;

@Mapper(config = CustomMapperConfig.class)
public interface RateMapper {

    /**
     * Преобразует объект типа {@link RateDto} в объект {@link Rate}.
     *
     * @param dto Объект типа {@link RateDto}, который требуется преобразовать в {@link Rate}.
     * @return Объект {@link Rate}, созданный на основе данных из объекта {@link RateDto}.
     */
    Rate toDomain(RateDto dto);
}
