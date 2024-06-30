package by.powerssolutions.vadarod.service;

import by.powerssolutions.vadarod.model.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface RateService {

    /**
     * Интерфейс метода для сохранения всех курсов валюты в базе данных для указанной даты.
     *
     * @param data {@link String}, дата определяющая данные курсов валюты для сохранения
     */
    void saveAllOnDate(String data);

    /**
     * Интерфейс метода, который сохраняет полученные значения на основе проверки наличия данных о сохраненных курсах валюты на переданную дату в БД.
     *
     * @param date {@link String}, дата переданная пользователем
     * @return сообщение для отображения в зависимости от наличия данных о курсе на переданную дату в базе
     */
    String checkRatesOnDate(String date);

    /**
     * Интерфейс метода для возврата ответ со значением курса валюты на переданную дату из API или БД.
     *
     * @param date {@link String}, дата переданная пользователем
     * @param code {@link String}, буквенный код валюты
     * @return ответ содержащий курс переданной валюты на переданную дату
     */
    ResponseEntity<BaseResponse> checkRatesOnDateByCurrency(String date, String code);
}
