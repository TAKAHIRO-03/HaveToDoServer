package jp.co.havetodo.service;

import jp.co.havetodo.domain.model.PlannedTask;
import jp.co.havetodo.service.model.FindPlannedTasksInputData;
import reactor.core.publisher.Flux;

public interface PlannedTaskService {

    Flux<PlannedTask> findPlannedTasks(FindPlannedTasksInputData inputData);
}
