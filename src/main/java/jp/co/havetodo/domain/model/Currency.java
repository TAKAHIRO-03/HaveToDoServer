package jp.co.havetodo.domain.model;

import java.io.Serializable;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "currency")
public record Currency(@Id @Column(value = "iso_code") @NonNull @NotNull String isoCode,
                       @Column(value = "country") @NonNull @NotNull String country,
                       @Column(value = "sign") @NonNull @NotNull String sign,
                       @Column(value = "name") @NonNull @NotNull String name) implements
    Serializable {

}
