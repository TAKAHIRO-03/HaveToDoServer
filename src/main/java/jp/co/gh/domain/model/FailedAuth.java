package jp.co.gh.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "failed_auth")
public class FailedAuth implements Serializable {

    @EmbeddedId
    private FailedAuthId failedAuthId;

}
