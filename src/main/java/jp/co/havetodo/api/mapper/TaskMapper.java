package jp.co.havetodo.api.mapper;

import jp.co.havetodo.api.payload.request.TaskRequest;
import jp.co.havetodo.api.payload.response.TaskResponse;
import jp.co.havetodo.domain.model.Account;
import jp.co.havetodo.domain.model.Task;
import jp.co.havetodo.service.model.CreateTaskInputData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskResponse taskToTaskResponse(Task task);

    CreateTaskInputData taskRequestToCreateTaskInputData(TaskRequest task, Account account);

}