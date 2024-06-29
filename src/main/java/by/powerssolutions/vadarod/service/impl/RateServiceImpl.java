package by.powerssolutions.vadarod.service.impl;

import static by.powerssolutions.vadarod.utils.ObjectUtils.extractResponse;

import by.powerssolutions.vadarod.dto.RateDto;
import by.powerssolutions.vadarod.service.RateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RateServiceImpl implements RateService {

    @Override
    @SuppressWarnings("checkstyle:UnusedLocalVariable")
    public void saveAllOnDate(String data) {
        List<RateDto> rates = extractResponse(data);
        //TODO
    }
}
