package by.powerssolutions.vadarod.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    @UtilityClass
    public class RequestConstants {

        public static final String SUCCESS_RATES_UPLOAD_MESSAGE = "Currency exchange rates for the selected date have been successfully uploaded";
        public static final String SUCCESS_RATES_UPLOAD_MESSAGE_IF_EXIST = "The exchange rates for the selected currency have already been uploaded";
    }

    @UtilityClass
    public class HandlerConstants {

        public static final String STATUS = "status";
        public static final String MESSAGE = "message";
        public static final String TYPE = "type";
    }
}
