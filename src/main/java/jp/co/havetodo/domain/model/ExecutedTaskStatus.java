package jp.co.havetodo.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "executed_task_status")
public class ExecutedTaskStatus implements Serializable {

    @Id
    @Column(name = "name")
    private String name;

}
