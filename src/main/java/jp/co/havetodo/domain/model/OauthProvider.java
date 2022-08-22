package jp.co.havetodo.domain.model;

import java.io.Serializable;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "oauth_provider")
public record OauthProvider(@Id @Column(value = "type") @NonNull @NotNull String type) implements
    Serializable {

}
