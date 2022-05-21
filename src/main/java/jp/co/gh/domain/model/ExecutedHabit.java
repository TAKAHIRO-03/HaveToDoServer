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
    @ManyToOne
    @JoinColumn(name = "planned_habit_id")
    private PlannedHabit plannedHabitId;

    @Column(name = "started_time")
    private ZonedDateTime startedTime;

    @Column(name = "ended_time")
    private ZonedDateTime endedTime;

    @Column(name = "is_achieved")
    private Boolean isAchieved;

    @Column(name = "is_cancelled")
    private Boolean isCancelled;

}
