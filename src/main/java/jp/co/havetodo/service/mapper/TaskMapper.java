package jp.co.havetodo.service.mapper;

import jp.co.havetodo.api.payload.response.TaskResponse;
import jp.co.havetodo.domain.model.Task;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Converter<Task, TaskResponse> {

    @Override
    TaskResponse convert(Task task);
}