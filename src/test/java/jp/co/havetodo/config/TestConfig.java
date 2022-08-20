package jp.co.havetodo.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@EnableAutoConfiguration
public class TestConfig extends AbstractR2dbcConfiguration {

    @Bean
    public R2dbcEntityTemplate template() {
        return new R2dbcEntityTemplate(connectionFactory());
    }

    @Bean
    public DataSource dataSource() {
        final var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5431/havetodo");
        dataSource.setUsername("havetodouser");
        dataSource.setPassword("havetodopass");
        return dataSource;
    }

    @Override
    public ConnectionFactory connectionFactory() {
        final var config = PostgresqlConnectionConfiguration
            .builder()
            .host("localhost")
            .username("havetodouser")
            .password("havetodopass")
            .database("havetodo")
            .port(5431)
            .build();
        return new PostgresqlConnectionFactory(config);
    }
}
