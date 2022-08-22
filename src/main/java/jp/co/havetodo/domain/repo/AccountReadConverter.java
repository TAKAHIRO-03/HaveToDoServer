package jp.co.havetodo.domain.repo;


import io.r2dbc.postgresql.codec.Interval;
import io.r2dbc.spi.Row;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Objects;
import jp.co.havetodo.domain.model.Account;
import jp.co.havetodo.domain.model.Currency;
import jp.co.havetodo.domain.model.OauthProvider;
import jp.co.havetodo.domain.model.Timezones;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class AccountReadConverter implements Converter<Row, Account> {

    @Override
    public Account convert(final Row r) {

        final var timezones = new Timezones(r.get("name", String.class),
            r.get("abbrev", String.class), r.get("utc_offset", Interval.class),
            r.get("is_dst", Boolean.class));

        final var oauthProvider = Objects.nonNull(r.get("type", String.class)) ?
            new OauthProvider(r.get("type", String.class)) : null;

        final var currency = new Currency(r.get("iso_code", String.class),
            r.get("country", String.class), r.get("sign", String.class),
            r.get("name", String.class));

        final var account = Account.builder()
            .id(r.get("id", Long.class))
            .username(r.get("username", String.class))
            .password(r.get("password", String.class))
            .isLocked(r.get("is_locked", Boolean.class))
            .timezones(timezones)
            .currency(currency)
            .oauthProvider(oauthProvider)
            .createdTime(r.get("created_time", ZonedDateTime.class))
            .updatedTime(r.get("updated_time", ZonedDateTime.class))
            .plannedTasks(Collections.EMPTY_LIST)
            .failedAuths(Collections.EMPTY_LIST)
            .build();

        return account;
    }
}