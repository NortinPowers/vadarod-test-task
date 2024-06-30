package by.powerssolutions.vadarod.utils;

import static by.powerssolutions.vadarod.utils.Constants.DATE_FORMAT;

import by.powerssolutions.vadarod.dto.request.RateRequestDto;
import by.powerssolutions.vadarod.exception.DataConvertException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class ObjectUtils {

    private final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Метод для извлечения списка объектов типа {@link RateRequestDto} из строкового представления ответа.
     *
     * @param response {@link String} Строковое представление ответа, из которого необходимо извлечь список объектов.
     * @return Список объектов {@link RateRequestDto}, полученный из переданного строкового представления ответа.
     * @throws DataConvertException В случае ошибки преобразования объекта с помощью {@link ObjectMapper}.
     */
    public static List<RateRequestDto> extractResponseListDto(String response) {
        try {
            return objectMapper.readValue(response, new TypeReference<>() {
            });
        } catch (IOException exception) {
            log.error("ObjectMapper error during list conversion", exception);
            throw DataConvertException.of(RateRequestDto.class);
        }
    }

    /**
     * Метод для извлечения объекта типа {@link RateRequestDto} из строкового представления ответа.
     *
     * @param response {@link String} Строковое представление ответа, из которого необходимо извлечь объект.
     * @return Объект {@link RateRequestDto}, полученный из переданного строкового представления ответа.
     * @throws DataConvertException В случае ошибки преобразования объекта с помощью {@link ObjectMapper}.
     */
    public static RateRequestDto extractResponseDto(String response) {
        try {
            return objectMapper.readValue(response, new TypeReference<>() {
            });
        } catch (IOException exception) {
            log.error("ObjectMapper error during conversion", exception);
            throw DataConvertException.of(RateRequestDto.class);
        }
    }

    /**
     * Метод для конвертации строки в объект класса {@link LocalDate}.
     *
     * @param date Строка, содержащая дату в формате, определенном переменной DATE_FORMAT.
     * @return Объект класса {@link LocalDate}, представляющий дату из переданной строки.
     */
    public static LocalDate convertDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.parse(date, formatter);
    }
}
