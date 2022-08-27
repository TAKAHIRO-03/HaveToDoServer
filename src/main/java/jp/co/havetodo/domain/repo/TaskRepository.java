package jp.co.havetodo.domain.repo;

import java.time.ZonedDateTime;
import jp.co.havetodo.domain.model.Task;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TaskRepository extends ReactiveCrudRepository<Task, Long> {

    @Query(
        "SELECT task.*, executed_task.task_id AS executed_task_task_id FROM task "
            + "LEFT JOIN executed_task ON task.id = executed_task.task_id "
            + "WHERE task.account_id = :accountId AND executed_task.task_id IS NULL "
            + "AND task.start_time BETWEEN :startTime AND :endTime "
            + "AND task.end_time BETWEEN :startTime AND :endTime "
            + "ORDER BY task.start_time "
            + "LIMIT :limit OFFSET :offset")
    Flux<Task> findToDayTasks(Long accountId, ZonedDateTime startTime, ZonedDateTime endTime,
        int limit, long offset);

    @Query(
        "SELECT task.*, executed_task.task_id AS executed_task_task_id FROM task "
            + "LEFT JOIN executed_task executed_task ON task.id = executed_task.task_id "
            + "WHERE task.account_id = :accountId AND executed_task.task_id IS NULL "
            + "AND :endTime <= task.end_time "
            + "ORDER BY task.start_time "
            + "LIMIT :limit OFFSET :offset")
    Flux<Task> findAfterTomorrowTasks(Long accountId, ZonedDateTime endTime, int limit,
        long offset);

}
