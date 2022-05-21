package jp.co.gh.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "payment_job_history")
public class PaymentJobHistory implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "planned_habit_id")
    private PlannedHabit plannedHabitId;

    @Column(name = "executed_time")
    private ZonedDateTime executedTime;
}
