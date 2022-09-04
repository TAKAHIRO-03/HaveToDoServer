package jp.co.havetodo.service;

import java.util.List;
import jp.co.havetodo.domain.model.Task;
import jp.co.havetodo.service.model.CreateTaskInputData;
import jp.co.havetodo.service.model.FindTasksInputData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {

    Flux<Task> findTasks(FindTasksInputData inputData);

    Mono<List<Integer>> createTask(CreateTaskInputData inputData);

}
