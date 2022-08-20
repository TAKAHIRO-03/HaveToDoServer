package jp.co.havetodo.domain.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "currency")
public record Currency(@Id @Column(value = "iso_code") String isoCode,
                       @Column(value = "country") String country,
                       @Column(value = "sign") String sign,
                       @Column(value = "name") String name) implements Serializable {

}
