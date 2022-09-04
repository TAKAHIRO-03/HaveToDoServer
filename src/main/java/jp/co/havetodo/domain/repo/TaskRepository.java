package jp.co.havetodo.domain.repo;

import java.time.LocalDateTime;
import jp.co.havetodo.domain.model.Task;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskRepository extends ReactiveCrudRepository<Task, Long> {

    @Query(
        "SELECT task.*, executed_task.task_id AS executed_task_task_id FROM task "
            + "LEFT JOIN executed_task ON task.id = executed_task.task_id "
            + "WHERE task.account_id = :accountId AND executed_task.task_id IS NULL "
            + "AND task.start_time BETWEEN :startTime AND :endTime "
            + "AND task.end_time BETWEEN :startTime AND :endTime "
            + "ORDER BY task.start_time "
            + "LIMIT :limit OFFSET :offset")
    Flux<Task> findToDayTasks(Long accountId, LocalDateTime startTime, LocalDateTime endTime,
        int limit, long offset);

    @Query(
        "SELECT task.*, executed_task.task_id AS executed_task_task_id FROM task "
            + "LEFT JOIN executed_task executed_task ON task.id = executed_task.task_id "
            + "WHERE task.account_id = :accountId AND executed_task.task_id IS NULL "
            + "AND :endTime <= task.end_time "
            + "ORDER BY task.start_time "
            + "LIMIT :limit OFFSET :offset")
    Flux<Task> findAfterTomorrowTasks(Long accountId, LocalDateTime endTime, int limit,
        long offset);


    @Override
    @Transactional
    Mono<Integer> save(Task entity);

}
