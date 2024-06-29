package by.powerssolutions.vadarod.exception;

import static by.powerssolutions.vadarod.utils.Constants.HandlerConstants.MESSAGE;
import static by.powerssolutions.vadarod.utils.Constants.HandlerConstants.STATUS;
import static by.powerssolutions.vadarod.utils.Constants.HandlerConstants.TYPE;
import static by.powerssolutions.vadarod.utils.ResponseUtils.DATA_TIME_PARSE_EXCEPTION_MESSAGE;
import static by.powerssolutions.vadarod.utils.ResponseUtils.OTHER_EXCEPTION_MESSAGE;
import static by.powerssolutions.vadarod.utils.ResponseUtils.getExceptionResponse;

import by.powerssolutions.vadarod.model.BaseResponse;
import by.powerssolutions.vadarod.model.ExceptionResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import feign.FeignException;
import java.time.format.DateTimeParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключение {@link FeignException} и возвращает соответствующий ResponseEntity с {@link BaseResponse}.
     *
     * @param exception Исключение {@link FeignException}, которое требуется обработать.
     * @return ResponseEntity с {@link BaseResponse} и кодом состояния HTTP из перехваченной ошибки внешнего сервиса.
     */
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<BaseResponse> handleException(FeignException exception) {
        RerouteExceptionResponse rerouteExceptionResponse = getRerouteExceptionResponse(exception);
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.valueOf(rerouteExceptionResponse.status),
                rerouteExceptionResponse.message,
                rerouteExceptionResponse.type
        );
        return new ResponseEntity<>(response, HttpStatus.valueOf(exception.status()));
    }

    /**
     * Обрабатывает исключение {@link DataConvertException} и возвращает соответствующий ResponseEntity с {@link BaseResponse}.
     *
     * @param exception Исключение {@link DataConvertException}, которое требуется обработать.
     * @return ResponseEntity с {@link BaseResponse} и кодом состояния HTTP INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(DataConvertException.class)
    public ResponseEntity<BaseResponse> handleException(DataConvertException exception) {
        ExceptionResponse response = getExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Обрабатывает исключение {@link DateTimeParseException} и возвращает соответствующий ResponseEntity с {@link BaseResponse}.
     *
     * @param exception Исключение {@link DateTimeParseException}, которое требуется обработать.
     * @return ResponseEntity с {@link BaseResponse} и кодом состояния HTTP INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(DateTimeParseException.class)
    private ResponseEntity<BaseResponse> handleException(DateTimeParseException exception) {
        ExceptionResponse response = getExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                DATA_TIME_PARSE_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Обрабатывает исключение {@link RuntimeException} и возвращает соответствующий ResponseEntity с {@link BaseResponse}.
     *
     * @param exception Исключение {@link RuntimeException}, которое требуется обработать.
     * @return ResponseEntity с {@link BaseResponse} и кодом состояния HTTP INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> handleException(RuntimeException exception) {
        ExceptionResponse response = getExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                OTHER_EXCEPTION_MESSAGE,
                exception
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Создает объект {@link RerouteExceptionResponse} на основе данных из исключения Feign.
     *
     * @param exception Исключение Feign.
     * @return Объект {@link RerouteExceptionResponse} с данными об ошибке.
     */
    private RerouteExceptionResponse getRerouteExceptionResponse(FeignException exception) {
        String errorResponse = exception.contentUTF8();
        JsonObject jsonObject = JsonParser.parseString(errorResponse).getAsJsonObject();
        int status = jsonObject.get(STATUS).getAsInt();
        String message = jsonObject.get(MESSAGE).getAsString();
        String type = jsonObject.get(TYPE).getAsString();
        return new RerouteExceptionResponse(status, message, type);
    }

    record RerouteExceptionResponse(int status, String message, String type) {
    }
}
