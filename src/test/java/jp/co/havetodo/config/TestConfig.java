package jp.co.havetodo.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import jp.co.havetodo.domain.repo.conv.AccountReadConverter;
import jp.co.havetodo.domain.repo.conv.TaskReadConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@EnableAutoConfiguration
public class TestConfig extends AbstractR2dbcConfiguration {

    @Value("${spring.r2dbc.url}")
    private String url;

    @Value("${spring.r2dbc.username}")
    private String username;

    @Value("${spring.r2dbc.password}")
    private String password;

    @Bean
    public R2dbcEntityTemplate template() {
        return new R2dbcEntityTemplate(this.connectionFactory());
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
    protected List<Object> getCustomConverters() {
        final var converterList = new ArrayList<>();
        converterList.add(new AccountReadConverter());
        converterList.add(new TaskReadConverter());
        return converterList;
    }

    @Override
    public ConnectionFactory connectionFactory() {
        final var splitR2dbcUrl = this.url.split("/");
        final var hostAndPort = splitR2dbcUrl[2].split(":");
        final var database = splitR2dbcUrl[3];
        final var config = PostgresqlConnectionConfiguration
            .builder()
            .host(hostAndPort[0])
            .username(this.username)
            .password(this.password)
            .database(database)
            .port(Integer.parseInt(hostAndPort[1]))
            .build();
        return new PostgresqlConnectionFactory(config);
    }
}
