package jp.co.gh.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

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

    @ManyToOne
    @JoinColumn(name = "timezones_name")
    private Timezones timezonesName;

    @ManyToOne
    @JoinColumn(name = "currency_iso_code")
    private Currency currencyIsoCode;

    @ManyToOne
    @JoinColumn(name = "oauth_provider_id")
    private OauthProvider oauthProviderId;

    @Column(name = "created_time")
    private ZonedDateTime createdTime;

    @Column(name = "updated_time")
    private ZonedDateTime updatedTime;

}
