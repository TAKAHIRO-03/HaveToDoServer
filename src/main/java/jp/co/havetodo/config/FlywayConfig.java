package jp.co.havetodo.config;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@RequiredArgsConstructor
public class FlywayConfig {

    private final Environment env;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return new Flyway(Flyway.configure()
            .baselineOnMigrate(
                Boolean.valueOf(this.env.getRequiredProperty("spring.flyway.baseline-on-migrate")))
            .dataSource(
                this.env.getRequiredProperty("spring.flyway.url"),
                this.env.getRequiredProperty("spring.flyway.user"),
                this.env.getRequiredProperty("spring.flyway.password"))
            .locations(this.env.getRequiredProperty("spring.flyway.locations"))
            .cleanOnValidationError(
                Boolean.valueOf(
                    this.env.getRequiredProperty("spring.flyway.clean-on-validation-error"))
            ));
    }
}