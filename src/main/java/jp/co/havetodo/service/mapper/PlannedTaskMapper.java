package jp.co.havetodo.service.mapper;

import jp.co.havetodo.api.payload.response.PlannedTaskResponse;
import jp.co.havetodo.domain.model.PlannedTask;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface PlannedTaskMapper extends Converter<PlannedTask, PlannedTaskResponse> {

    @Override
    PlannedTaskResponse convert(PlannedTask plannedTask);
}