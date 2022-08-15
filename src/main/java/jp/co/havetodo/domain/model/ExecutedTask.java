package jp.co.havetodo.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "executed_task")
public class ExecutedTask implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "planned_task_id", insertable = false, updatable = false)
    private PlannedTask plannedTask;

    @Column(name = "started_time")
    private ZonedDateTime startedTime;

    @Column(name = "ended_time")
    private ZonedDateTime endedTime;

    @OneToOne
    @JoinColumn(name = "executed_task_status_name", insertable = false, updatable = false)
    private ExecutedTaskStatus executedTaskStatus;

}
