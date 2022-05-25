package jp.co.gh.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "executed_habit_status")
public class ExecutedHabitStatus implements Serializable {

    @Id
    @Column(name = "name")
    private String name;

}
