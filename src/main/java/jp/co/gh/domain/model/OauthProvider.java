package jp.co.gh.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "oauth_provider")
public class OauthProvider implements Serializable {

    @Id
    @Column(name = "type")
    private String type;

}
