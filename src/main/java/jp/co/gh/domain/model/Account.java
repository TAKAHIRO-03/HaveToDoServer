package jp.co.gh.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String roles;

    @Column(name = "is_locked")
    private boolean isLocked;

    @OneToOne
    @JoinColumn(name = "timezones_name", insertable = false, updatable = false)
    private Timezones timezones;

    @OneToOne
    @JoinColumn(name = "currency_iso_code", insertable = false, updatable = false)
    private Currency currency;

    @OneToOne
    @JoinColumn(name = "oauth_provider_type", insertable = false, updatable = false)
    private OauthProvider oauthProvider;

    @Column(name = "created_time")
    private ZonedDateTime createdTime;

    @Column(name = "updated_time")
    private ZonedDateTime updatedTime;

    @OneToMany(mappedBy = "account", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PlannedHabit> plannedHabits;

    @OneToMany(mappedBy = "failedAuthId.account", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FailedAuth> failedAuths;

}
