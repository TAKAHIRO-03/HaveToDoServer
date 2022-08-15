package jp.co.havetodo.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "oauth_provider")
public class OauthProvider implements Serializable {

    @Id
    @Column(name = "type")
    private String type;

}
