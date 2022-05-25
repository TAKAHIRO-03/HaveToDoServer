package jp.co.gh.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "executed_habit")
public class ExecutedHabit implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "planned_habit_id", insertable = false, updatable = false)
    private PlannedHabit plannedHabit;

    @Column(name = "started_time")
    private ZonedDateTime startedTime;

    @Column(name = "ended_time")
    private ZonedDateTime endedTime;

    @OneToOne
    @JoinColumn(name = "executed_habit_status_name", insertable = false, updatable = false)
    private ExecutedHabitStatus executedHabitStatus;

}
