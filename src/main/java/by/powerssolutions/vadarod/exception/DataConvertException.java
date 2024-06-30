package by.powerssolutions.vadarod.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataConvertException extends RuntimeException {

    public DataConvertException(String message) {
        super(message);
    }

    public static DataConvertException of(Class<?> clazz) {
        return new DataConvertException("The data cannot be converted to an object " + clazz.getSimpleName());
    }
}
