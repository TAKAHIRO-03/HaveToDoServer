package jp.co.havetodo.domain.repo;

import io.micrometer.core.instrument.util.IOUtils;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import jp.co.havetodo.config.TestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.test.context.TestPropertySource;

@Slf4j
@DataR2dbcTest
@Import({TestConfig.class})
@TestPropertySource("classpath:application.yml")
public class PlannedTaskRepositoryTest {

    @Autowired
    private R2dbcEntityTemplate template;

    @Autowired
    private PlannedTaskRepository plannedTaskRepo;

    @BeforeAll
    public static void setup(@Autowired final R2dbcEntityTemplate template) throws Exception {

        try (final var in = new ClassPathResource("schema.sql").getInputStream()) {
            template.getDatabaseClient().sql(IOUtils.toString(in, StandardCharsets.UTF_8)).fetch()
                .rowsUpdated().block(Duration.ofMillis(10000));
        }
        try (final var in = new ClassPathResource("data.sql").getInputStream()) {
            template.getDatabaseClient().sql(IOUtils.toString(in, StandardCharsets.UTF_8)).fetch()
                .rowsUpdated().block(Duration.ofMillis(10000));
        }

    }

    @Test
    public void テスト() throws Exception {

        final var accountId = 1L;
        final var page = PageRequest.of(0, 100);
        final var startTime = ZonedDateTime.of(LocalDateTime.of(2019, 3, 2, 0, 0, 0),
            ZoneId.systemDefault().normalized());
        final var endTime = ZonedDateTime.of(LocalDateTime.of(2019, 3, 2, 23, 59, 59),
            ZoneId.systemDefault().normalized());

        this.plannedTaskRepo.findToDayTasks(accountId, startTime, endTime, page.getPageSize(),
                page.getOffset())
            .log()
            .collectList()
            .block()
            .forEach(System.out::println);
    }
}
