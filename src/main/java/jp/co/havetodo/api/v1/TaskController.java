package jp.co.havetodo.api.v1;

import io.r2dbc.postgresql.codec.Interval;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import jp.co.havetodo.api.mapper.TaskMapper;
import jp.co.havetodo.api.payload.request.TaskRequest;
import jp.co.havetodo.api.payload.response.ApiErrorResponse;
import jp.co.havetodo.api.payload.response.TaskResponse;
import jp.co.havetodo.domain.model.Account;
import jp.co.havetodo.domain.model.Currency;
import jp.co.havetodo.domain.model.Timezones;
import jp.co.havetodo.domain.repo.AccountRepository;
import jp.co.havetodo.service.TaskService;
import jp.co.havetodo.service.model.FindTasksInputData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 計画済みタスクのCRD処理をするコントローラー
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/v1.0/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    private final TaskMapper mapper;

    /**
     * TODO 本来はSpring Security経由でアカウント情報を取得するのが正しいが未実装のため一旦Repository経由で取得
     */
    private final AccountRepository accountRepo;

    private Account testAccount;

    /**
     * 計画済みの単一のタスク取得処理
     *
     * @param id 取得対象となるID
     * @return ユーザーに紐づいた計画済みタスク（単一）
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "計画済みの単一のタスク取得", notes = "ユーザーの計画済みタスクを１つ取得する。過去の計画済みタスクは取得しない。")
    @ApiResponses({
        @ApiResponse(code = 200, message = "計画済みタスク返却", response = TaskResponse.class),
        @ApiResponse(code = 400, message = "パラメーターが不正な時", response = ApiErrorResponse.class),
        @ApiResponse(code = 401, message = "認証・認可失敗", response = ApiErrorResponse.class),
        @ApiResponse(code = 404, message = "タスク"),
        @ApiResponse(code = 500, message = "サーバー内部でエラーが発生", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<TaskResponse>> get(
        @ApiParam(required = true, value = "取得対象となるID")
        @PositiveOrZero @PathVariable final Long id) {
        return Mono.just(ResponseEntity.ok(null));
    }

    /**
     * ユーザーに紐づいた計画済みタスク取得処理
     *
     * @return 計画済みタスク（複数）
     */
    @GetMapping
    @ApiOperation(value = "計画済みのタスク取得", notes = "ユーザーの計画済みタスクを全て取得する。過去の計画済みタスクは取得しない。")
    @ApiResponses({
        @ApiResponse(code = 200, message = "計画済みタスク返却", response = TaskResponse.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "パラメーターが不正な時", response = ApiErrorResponse.class),
        @ApiResponse(code = 401, message = "認証・認可失敗", response = ApiErrorResponse.class),
        @ApiResponse(code = 204, message = "計画済みのタスクが見つからなかった時"),
        @ApiResponse(code = 500, message = "サーバー内部でエラーが発生", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<List<TaskResponse>>> getAll(
        @ApiParam(required = true, value = "指定されたページ", example = "0")
        @RequestParam("page") final int page,
        @ApiParam(required = true, value = "ページサイズ", example = "10")
        @RequestParam("size") final int size,
        @ApiParam(value = "タスク開始時間", example = "2015-12-15T00:00:00.000")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @RequestParam(value = "startTime", required = false) final LocalDateTime startTime,
        @ApiParam(required = true, value = "タスク終了時間", example = "2015-12-15T23:59:59.999")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @RequestParam(value = "endTime") final LocalDateTime endTime) {

        //TODO JWTからaccountIdを取得する。
        final var accountId = 1L;
        final var pageReq = PageRequest.of(page, size);
        final var findTasksInputData = new FindTasksInputData(accountId, pageReq, startTime,
            endTime);

        return this.service.findTasks(findTasksInputData)
            .mapNotNull(this.mapper::taskToTaskResponse)
            .collectList()
            .mapNotNull(
                x -> x.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(x));
    }

    /**
     * タスク登録処理
     *
     * @param req タスク登録パラメータ
     * @return 無し
     */
    @PostMapping
    @ApiOperation(value = "タスク登録", notes = "タスクを登録する。リピートのフラグにより複数タスクを登録することが出来る。")
    @ApiResponses({
        @ApiResponse(code = 201, message = "タスク登録成功"),
        @ApiResponse(code = 400, message = "パラメーターが不正な時", response = ApiErrorResponse.class),
        @ApiResponse(code = 401, message = "認証・認可失敗", response = ApiErrorResponse.class),
        @ApiResponse(code = 500, message = "サーバー内部でエラーが発生", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<Object>> create(@Valid @RequestBody final TaskRequest req) {

        final var createTasksInputData = this.mapper.taskRequestToCreateTaskInputData(req,
            this.testAccount);
        return this.service.createTask(createTasksInputData)
            .mapNotNull(t -> {
                if (t.stream().allMatch(x -> x == -1)) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                } else {
                    return ResponseEntity.created(null).build();
                }
            })
            .onErrorReturn(e -> e instanceof DataIntegrityViolationException,
                new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    /**
     * 計画済みタスク削除処理
     *
     * @param id 削除対象となる計画済みタスク
     * @return 無し
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "計画済みの単一タスク削除", notes = "ユーザーの計画済みタスクを１つ削除する。リピートで登録したタスクの場合、複数削除することが出来る。")
    @ApiResponses({
        @ApiResponse(code = 204, message = "計画済みタスクの削除成功"),
        @ApiResponse(code = 400, message = "パラメーターが不正な時", response = ApiErrorResponse.class),
        @ApiResponse(code = 401, message = "認証・認可失敗", response = ApiErrorResponse.class),
        @ApiResponse(code = 404, message = "計画済みのタスクが見つからない"),
        @ApiResponse(code = 500, message = "サーバー内部でエラーが発生", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<Void>> delete(
        @ApiParam(required = true, value = "削除対象となるID")
        @PositiveOrZero @PathVariable final long id,
        @ApiParam(required = true, value = "リピートされたものを削除するか")
        @RequestParam final boolean repeatDeleteFlg) {
        return Mono.just(ResponseEntity.noContent().build());
    }

    /**
     * TODO Spring Security実装したら消す
     *
     * @return アカウント
     */
    @PostConstruct
    public void init() {
        try {
            this.testAccount = this.accountRepo.findById(1L).block();
        } catch (final Exception e) {
            log.warn(e.getLocalizedMessage());
            this.testAccount = Account.builder().id(1L)
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

        }
    }

}
