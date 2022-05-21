package jp.co.gh.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "currency")
public class Currency implements Serializable {

    @Id
    @Column(name = "iso_code")
    private String isoCode;

    @Column(name = "country")
    private String country;

    @Column(name = "sign")
    private String sign;

    @Column(name = "name")
    private String name;
}
