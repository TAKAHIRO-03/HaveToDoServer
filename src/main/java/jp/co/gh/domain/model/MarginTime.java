package jp.co.gh.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;

@Data
@Entity
@Table(name = "margin_time")
public class MarginTime implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "completed_margin")
    private Duration completedMargin;

    @Column(name = "cancel_margin")
    private Duration cancelMargin;

}
