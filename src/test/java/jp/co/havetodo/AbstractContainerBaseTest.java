package jp.co.havetodo;

import org.testcontainers.containers.PostgreSQLContainer;

public abstract class AbstractContainerBaseTest {

    public static PostgreSQLContainer postgresqlContainer;

    static {
        postgresqlContainer = new PostgreSQLContainer(
            "postgres:14.4-alpine3.16")
            .withDatabaseName("havetodo")
            .withUsername("havetodouser")
            .withPassword("havetodopass");

        postgresqlContainer.start();
    }

}
