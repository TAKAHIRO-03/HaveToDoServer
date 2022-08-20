package jp.co.havetodo.domain.repo.spec;

import jp.co.havetodo.domain.model.PlannedTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public final class PlannedTaskSpecifications {

    public static Specification<PlannedTask> idEqual(final Long id) {
        return Objects.isNull(id) ? null : (root, cq, cb) -> cb.equal(root.get("id"), id);
    }

    public static Specification<PlannedTask> accountIdEqual(final String accountId) {
        return StringUtils.isBlank(accountId) ? null : (root, cq, cb) -> cb.equal(root.get("accountId"), accountId);
    }

}
