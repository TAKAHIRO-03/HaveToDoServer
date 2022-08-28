package jp.co.havetodo.service;

import java.util.Objects;
import jp.co.havetodo.domain.model.Task;
import jp.co.havetodo.domain.repo.TaskRepository;
import jp.co.havetodo.service.model.CreateTaskInputData;
import jp.co.havetodo.service.model.FindTasksInputData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @Override
    @Transactional
    public Mono<?> createTask(@NonNull @NotNull final CreateTaskInputData inputData) {

        final var task = Task.builder()
            .accountId(inputData.account().getId())
            .title(inputData.title())
            .description(inputData.description())
            .startTime(inputData.startTime())
            .endTime(inputData.endTime())
            .cost(inputData.cost())
            .isRepeat(inputData.isRepeat())
            .build();

        if (CollectionUtils.isEmpty(inputData.repeatDayOfWeek())) {
            return this.repo.save(task).mapNotNull(Task::getId);
        }

        final var tasks = task.createTasks(inputData.repeatDayOfWeek(), inputData.repeatEndDate());
        return this.repo.saveAll(tasks).collectList();
    }

}
