package jp.co.havetodo.domain.repo;

import jp.co.havetodo.domain.model.Account;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveCrudRepository<Account, Long> {

    @Override
    @Query(
        "SELECT account.*, timezones.*, oauth_provider.*, currency.* "
            + "FROM account JOIN timezones ON timezones.name = account.timezones_name "
            + "LEFT JOIN oauth_provider ON oauth_provider.type = account.oauth_provider_type "
            + "JOIN currency ON currency.iso_code = account.currency_iso_code ")
    Flux<Account> findAll();

    @Override
    @Query(
        "SELECT account.*, timezones.*, oauth_provider.*, currency.* "
            + "FROM account JOIN timezones ON timezones.name = account.timezones_name "
            + "LEFT JOIN oauth_provider ON oauth_provider.type = account.oauth_provider_type "
            + "JOIN currency ON currency.iso_code = account.currency_iso_code "
            + "WHERE account.id = :id")
    Mono<Account> findById(Long id);

}
