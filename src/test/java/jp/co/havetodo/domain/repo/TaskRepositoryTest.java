package jp.co.havetodo.domain.repo;

import io.micrometer.core.instrument.util.IOUtils;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import jp.co.havetodo.AbstractContainerBaseTest;
import jp.co.havetodo.config.TestConfig;
import jp.co.havetodo.domain.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
@DataR2dbcTest
@Import({TestConfig.class})
@TestPropertySource("classpath:application-test.yml")
@Testcontainers
public class TaskRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private TaskRepository taskRepo;

    @DynamicPropertySource
    static void postgresqlProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://"
            + postgresqlContainer.getHost() + ":"
            + postgresqlContainer.getFirstMappedPort()
            + "/" + postgresqlContainer.getDatabaseName());
        registry.add("spring.r2dbc.username", () -> postgresqlContainer.getUsername());
        registry.add("spring.r2dbc.password", () -> postgresqlContainer.getPassword());
    }

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

        this.taskRepo.saveAll(this.generateTasks(50))
            .count()
            .as(StepVerifier::create)
            .expectNextMatches(updatedRows -> updatedRows.equals(50L))
            .verifyComplete();
    }

    @AfterAll
    public static void tearDown() {
        postgresqlContainer.close();
    }

    private Flux<Task> generateTasks(final int amount) {
        return Flux.range(1, amount)
            .map(id -> Task.builder()
                .accountId(1L)
                .title("title-%d".formatted(id))
                .description("description-%d".formatted(id))
                .startTime(LocalDateTime.of(2022, 1, 2, 10, 10, 10).plus(id, ChronoUnit.DAYS))
                .endTime(LocalDateTime.of(2022, 1, 2, 13, 10, 10).plus(id, ChronoUnit.DAYS))
                .isRepeat(false)
                .cost(BigDecimal.valueOf(1000))
                .build()
            );
    }

}
