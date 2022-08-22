package jp.co.havetodo.domain.service;

import jp.co.havetodo.api.payload.response.PlannedTaskResponse;
import jp.co.havetodo.domain.repo.PlannedTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlannedTaskService {

    private final PlannedTaskRepository repo;

    private final ConversionService converter;

    public Mono<Page<PlannedTaskResponse>> findByAccountId(final Long accountId,
        final Pageable page) {
        return this.repo.findByAccountId(accountId, page)
            .mapNotNull(x -> this.converter.convert(x, PlannedTaskResponse.class))
            .collectList()
            .zipWith(this.repo.count())
            .map(t -> (Page<PlannedTaskResponse>) new PageImpl<>(t.getT1(), page, t.getT2()))
            .doOnError(RuntimeException.class, x -> log.error(""));
    }

}
