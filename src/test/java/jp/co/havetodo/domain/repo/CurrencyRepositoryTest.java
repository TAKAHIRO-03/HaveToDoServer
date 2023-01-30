package jp.co.havetodo.domain.repo;

import io.micrometer.core.instrument.util.IOUtils;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import jp.co.havetodo.AbstractContainerBaseTest;
import jp.co.havetodo.config.TestConfig;
import lombok.extern.slf4j.Slf4j;
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


@Slf4j
@DataR2dbcTest
@Import({TestConfig.class})
@TestPropertySource("classpath:application.yml")
@Testcontainers
public class CurrencyRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private R2dbcEntityTemplate template;

    @Autowired
    private CurrencyRepository currencyRepo;

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
    public void test() throws Exception {
        currencyRepo.findAll().log().collectList().block().forEach(System.out::println);
    }

}
