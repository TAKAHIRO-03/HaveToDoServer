package jp.co.gh.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "roles")
public class Roles implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "role_label")
    private String roleLabel;

    @Column(name = "role_value")
    private String roleValue;
}
