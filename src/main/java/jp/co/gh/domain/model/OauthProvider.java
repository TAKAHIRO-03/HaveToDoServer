package jp.co.gh.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "oauth_provider")
public class OauthProvider implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "types")
    private String types;

}
