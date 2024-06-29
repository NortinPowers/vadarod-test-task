package by.powerssolutions.vadarod.utils;

import by.powerssolutions.vadarod.dto.RateDto;
import by.powerssolutions.vadarod.exception.DataConvertException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@UtilityClass
@Log4j2
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
}
