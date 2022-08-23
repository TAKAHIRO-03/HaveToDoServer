package jp.co.havetodo.domain.service;

import java.time.ZonedDateTime;
import java.util.Objects;
import jp.co.havetodo.api.payload.response.PlannedTaskResponse;
import jp.co.havetodo.domain.repo.PlannedTaskRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

    public Mono<Page<PlannedTaskResponse>> findPlannedTasks(@NonNull @NotNull final Long accountId,
        @Nullable final ZonedDateTime startTime, @NonNull @NotNull final ZonedDateTime endTime,
        @NonNull @NotNull final Pageable page) {

        if (Objects.isNull(startTime)) {
            return this.repo.findAfterTomorrowTasks(accountId, endTime, page.getPageSize(),
                    page.getOffset())
                .mapNotNull(x -> this.converter.convert(x, PlannedTaskResponse.class))
                .collectList()
                .zipWith(this.repo.count())
                .map(t -> new PageImpl<>(t.getT1(), page, t.getT2()));
        }

        return this.repo.findToDayTasks(accountId, startTime, endTime, page.getPageSize(),
                page.getOffset())
            .mapNotNull(x -> this.converter.convert(x, PlannedTaskResponse.class))
            .collectList()
            .zipWith(this.repo.count())
            .map(t -> new PageImpl<>(t.getT1(), page, t.getT2()));
    }

}
