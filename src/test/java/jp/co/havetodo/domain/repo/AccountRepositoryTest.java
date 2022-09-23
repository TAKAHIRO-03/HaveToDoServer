package jp.co.havetodo.domain.repo;

import io.micrometer.core.instrument.util.IOUtils;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import jp.co.havetodo.config.TestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.test.context.TestPropertySource;


@Slf4j
@DataR2dbcTest
@Import({TestConfig.class})
@TestPropertySource("classpath:application-test.yml")
public class AccountRepositoryTest {

    @Autowired
    private R2dbcEntityTemplate template;

    @Autowired
    private AccountRepository accountRepo;

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
        this.accountRepo.findAll().log().collectList().block().forEach(System.out::println);
    }

}
