package by.powerssolutions.vadarod.utils;

import by.powerssolutions.vadarod.model.ExceptionResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class ResponseUtils {

    public static final String OTHER_EXCEPTION_MESSAGE = "Unexpected error";
    public static final String DATA_TIME_PARSE_EXCEPTION_MESSAGE = "An incorrect date was entered, use the yyyy-MM-dd format (for example, 2024-12-10)";

    /**
     * Возвращает объект {@link ExceptionResponse} для ответа с ошибкой, содержащий
     * указанный статус, сообщение и имя класса исключения.
     *
     * @param status    Статус ответа с ошибкой.
     * @param message   Сообщение для включения в ответ.
     * @param exception Исключение, используемое для получения имени класса.
     * @return Объект {@link ExceptionResponse} для ответа с ошибкой.
     */
    public static ExceptionResponse getExceptionResponse(HttpStatus status, String message, Exception exception) {
        return new ExceptionResponse(status, message, exception.getClass().getSimpleName());
    }
}
