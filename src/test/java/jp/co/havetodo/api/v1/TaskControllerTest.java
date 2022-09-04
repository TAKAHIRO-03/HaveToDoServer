package jp.co.havetodo.api.v1;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.r2dbc.postgresql.codec.Interval;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import jp.co.havetodo.api.mapper.TaskMapper;
import jp.co.havetodo.api.mapper.TaskMapperImpl;
import jp.co.havetodo.api.payload.request.TaskRequest;
import jp.co.havetodo.domain.model.Account;
import jp.co.havetodo.domain.model.Currency;
import jp.co.havetodo.domain.model.Task;
import jp.co.havetodo.domain.model.Timezones;
import jp.co.havetodo.domain.repo.AccountRepository;
import jp.co.havetodo.filter.log.LoggingFilter;
import jp.co.havetodo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@MockBeans({@MockBean(TaskService.class), @MockBean(AccountRepository.class)})
@WebFluxTest(TaskController.class)
@SpringJUnitConfig(TaskControllerTest.Config.class)
public class TaskControllerTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private TaskService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Configuration
    static class Config {

        @Bean
        TaskController taskController(final TaskMapper taskMapper, final TaskService service,
            final AccountRepository accountRepo) {

            final var account = Account.builder().id(1L)
                .username("username")
                .password("password")
                .isLocked(false)
                .timezones(new Timezones("japan", "japan", Interval.ZERO, false))
                .currency(new Currency("yen", "yen", "yen", "yen"))
                .oauthProvider(null)
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .tasks(Collections.emptyList())
                .failedAuths(Collections.emptyList())
                .build();

            when(accountRepo.findById(1L)).thenReturn(Mono.just(account));

            return new TaskController(service, taskMapper, accountRepo);
        }

        @Bean
        TaskMapper taskMapper() {
            return new TaskMapperImpl();
        }


        @Bean
        ObjectMapper objectMapper() {

            final var jtm = new JavaTimeModule();
            jtm.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

            return JsonMapper.builder()
                .addModule(jtm)
                .build();
        }

        @Bean
        LoggingFilter loggingFilter(final ObjectMapper objectMapper) {
            return new LoggingFilter(objectMapper);
        }

    }


    @Test
    public void タスクが存在しないときにリクエストを送信_204_正常系() throws Exception {

        when(this.service.findTasks(any())).thenReturn(Flux.empty());

        this.client.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/v1.0/tasks")
                .queryParam("size", "10")
                .queryParam("page", "0")
                .queryParam("startTime", "2015-12-15T23:30:59.999")
                .queryParam("endTime", "2015-12-16T23:30:59.999")
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNoContent();

    }


    @Test
    public void タスクが存在するときにリクエストを送信_200_正常系() throws Exception {

        when(this.service.findTasks(any())).thenReturn(generateTasks(100));

        this.client.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/v1.0/tasks")
                .queryParam("size", "10")
                .queryParam("page", "0")
                .queryParam("startTime", "2015-12-15T23:30:59.999")
                .queryParam("endTime", "2015-12-16T23:30:59.999")
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(List.class);

    }

    @Test
    public void タスク登録を送信_201_正常系() throws Exception {

        when(this.service.createTask(any())).thenReturn(Flux.just(1).collectList());

        final var requestAsJson = this.objectMapper.writeValueAsString(
            new TaskRequest("title", "description",
                LocalDateTime.now().plus(1, ChronoUnit.MINUTES),
                LocalDateTime.now().plus(2, ChronoUnit.HOURS),
                BigDecimal.valueOf(1000),
                false,
                Collections.emptySet(), null));

        this.client.post()
            .uri(uriBuilder -> uriBuilder
                .path("/api/v1.0/tasks")
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestAsJson))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Void.class);

    }

    @Test
    public void タスク登録を送信_リピート付き_201_正常系() throws Exception {

        when(this.service.createTask(any())).thenReturn(Flux.just(1, 2, 3, 4).collectList());

        final var requestAsJson = this.objectMapper.writeValueAsString(
            new TaskRequest("title", "description",
                LocalDateTime.now().plus(1, ChronoUnit.MINUTES),
                LocalDateTime.now().plus(2, ChronoUnit.HOURS),
                BigDecimal.valueOf(1000),
                true,
                Set.of(DayOfWeek.MONDAY), LocalDate.now().plus(365, ChronoUnit.DAYS)));

        this.client.post()
            .uri(uriBuilder -> uriBuilder
                .path("/api/v1.0/tasks")
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestAsJson))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Void.class);

    }

    private static Flux<Task> generateTasks(final int amount) {
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
