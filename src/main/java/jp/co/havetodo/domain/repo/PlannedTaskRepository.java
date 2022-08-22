package jp.co.havetodo.domain.repo;

import jp.co.havetodo.domain.model.PlannedTask;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PlannedTaskRepository extends ReactiveCrudRepository<PlannedTask, Long> {

    Flux<PlannedTask> findByAccountId(Long accountId, Pageable page);

}
