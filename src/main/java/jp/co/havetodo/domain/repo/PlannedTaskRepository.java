package jp.co.havetodo.domain.repo;

import jp.co.havetodo.domain.model.PlannedTask;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PlannedTaskRepository extends ReactiveCrudRepository<PlannedTask, Integer> {

}
