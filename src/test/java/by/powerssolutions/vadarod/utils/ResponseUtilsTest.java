package by.powerssolutions.vadarod.utils;

import static by.powerssolutions.vadarod.utils.ResponseUtils.getExceptionResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import by.powerssolutions.vadarod.model.ExceptionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ResponseUtilsTest {

    @Test
    void getExceptionResponseShouldReturnExceptionResponseByTransmittedError() {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Something went wrong";
        Exception exception = new NullPointerException();

        ExceptionResponse actual = getExceptionResponse(status, message, exception);

        assertNotNull(actual.getTimestamp());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), actual.getStatus());
        assertEquals(message, actual.getMessage());
        assertEquals(exception.getClass().getSimpleName(), actual.getType());
    }
}
