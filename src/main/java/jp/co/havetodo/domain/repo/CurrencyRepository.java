package jp.co.havetodo.domain.repo;

import jp.co.havetodo.domain.model.Currency;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CurrencyRepository extends ReactiveCrudRepository<Currency, String> {

}
