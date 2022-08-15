package jp.co.havetodo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class FailedAuthId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    private Account account;

    @Column(name = "auth_ts")
    private ZonedDateTime authTs;

}
