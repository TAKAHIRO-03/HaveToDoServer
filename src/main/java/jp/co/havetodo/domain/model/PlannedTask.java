package jp.co.havetodo.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "planned_task")
public class PlannedTask implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    private Account account;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "is_repeat")
    private boolean isRepeat;

    @OneToOne(mappedBy = "plannedTask", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ExecutedTask executedTask;

    @OneToOne(mappedBy = "plannedTask", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PaymentJobHistory paymentJobHistory;

}
