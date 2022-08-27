package jp.co.havetodo.service;

import java.util.Objects;
import jp.co.havetodo.domain.model.Task;
import jp.co.havetodo.domain.repo.TaskRepository;
import jp.co.havetodo.service.model.FindTasksInputData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repo;

    @Override
    public Flux<Task> findTasks(
        @NonNull @NotNull final FindTasksInputData inputData) {

        if (Objects.isNull(inputData.startTime())) {
            return this.repo.findAfterTomorrowTasks(inputData.accountId(), inputData.endTime(),
                inputData.page().getPageSize(),
                inputData.page().getOffset() * inputData.page().getPageSize());
        }

        return this.repo.findToDayTasks(inputData.accountId(), inputData.startTime(),
            inputData.endTime(),
            inputData.page().getPageSize(),
            inputData.page().getOffset() * inputData.page().getPageSize());
    }
}
