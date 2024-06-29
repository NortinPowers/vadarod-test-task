package by.powerssolutions.vadarod.utils;

import static by.powerssolutions.vadarod.utils.Constants.DATE_FORMAT;

import by.powerssolutions.vadarod.dto.RateDto;
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

    public static List<RateDto> extractResponse(String response) {
        try {
            return objectMapper.readValue(response, new TypeReference<>() {
            });
        } catch (IOException exception) {
            log.error("ObjectMapper error during conversion", exception);
            throw DataConvertException.of(RateDto.class);
        }
    }

    public static LocalDate convertDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.parse(date, formatter);
    }
}
