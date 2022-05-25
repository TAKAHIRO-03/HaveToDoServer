package jp.co.gh.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "roles")
public class Roles implements Serializable {

    @Id
    @Column(name = "name")
    private String name;

}
