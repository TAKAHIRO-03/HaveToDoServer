package jp.co.havetodo.domain.repo;

import java.time.ZonedDateTime;
import jp.co.havetodo.domain.model.PlannedTask;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PlannedTaskRepository extends ReactiveCrudRepository<PlannedTask, Long> {

    @Query(
        "SELECT planned_task.*, executed_task.planned_task_id AS executed_task_planned_task_id FROM planned_task "
            + "LEFT JOIN executed_task ON planned_task.id = executed_task.planned_task_id "
            + "WHERE planned_task.account_id = :accountId AND executed_task.planned_task_id IS NULL "
            + "AND planned_task.start_time BETWEEN :startTime AND :endTime "
            + "AND planned_task.end_time BETWEEN :startTime AND :endTime "
            + "ORDER BY planned_task.start_time "
            + "LIMIT :limit OFFSET :offset")
    Flux<PlannedTask> findToDayTasks(Long accountId, ZonedDateTime startTime, ZonedDateTime endTime,
        int limit, long offset);

    @Query(
        "SELECT planned_task.*, executed_task.planned_task_id AS executed_task_planned_task_id FROM planned_task "
            + "LEFT JOIN executed_task executed_task ON planned_task.id = executed_task.planned_task_id "
            + "WHERE planned_task.account_id = :accountId AND executed_task.planned_task_id IS NULL "
            + "AND :endTime <= planned_task.end_time "
            + "ORDER BY planned_task.start_time "
            + "LIMIT :limit OFFSET :offset")
    Flux<PlannedTask> findAfterTomorrowTasks(Long accountId, ZonedDateTime endTime, int limit,
        long offset);

}
