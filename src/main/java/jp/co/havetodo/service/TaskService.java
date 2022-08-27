package jp.co.havetodo.service;

import jp.co.havetodo.domain.model.Task;
import jp.co.havetodo.service.model.FindTasksInputData;
import reactor.core.publisher.Flux;

public interface TaskService {

    Flux<Task> findTasks(FindTasksInputData inputData);
}
