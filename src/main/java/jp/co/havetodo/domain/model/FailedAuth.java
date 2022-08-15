package jp.co.havetodo.domain.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "failed_auth")
public class FailedAuth implements Serializable {

    @EmbeddedId
    private FailedAuthId failedAuthId;

}
