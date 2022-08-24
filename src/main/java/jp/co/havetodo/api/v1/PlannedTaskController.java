package jp.co.havetodo.api.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import jp.co.havetodo.api.payload.request.PlannedTaskRequest;
import jp.co.havetodo.api.payload.response.ApiErrorResponse;
import jp.co.havetodo.api.payload.response.PlannedTaskResponse;
import jp.co.havetodo.domain.service.PlannedTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping(path = "/api/v1.0/plannedTasks", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PlannedTaskController {

    private final PlannedTaskService service;

    /**
     * 計画済みの単一のタスク取得処理
     *
     * @param id 取得対象となるID
     * @return ユーザーに紐づいた計画済みタスク（単一）
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "計画済みの単一のタスク取得", notes = "ユーザーの計画済みタスクを１つ取得する。過去の計画済みタスクは取得しない。")
    @ApiResponses({
        @ApiResponse(code = 200, message = "計画済みタスク返却", response = PlannedTaskResponse.class),
        @ApiResponse(code = 400, message = "パラメーターが不正な時", response = ApiErrorResponse.class),
        @ApiResponse(code = 401, message = "認証・認可失敗", response = ApiErrorResponse.class),
        @ApiResponse(code = 404, message = "タスク"),
        @ApiResponse(code = 500, message = "サーバー内部でエラーが発生", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<PlannedTaskResponse>> get(
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
        @ApiResponse(code = 200, message = "計画済みタスク返却", response = PlannedTaskResponse.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "パラメーターが不正な時", response = ApiErrorResponse.class),
        @ApiResponse(code = 401, message = "認証・認可失敗", response = ApiErrorResponse.class),
        @ApiResponse(code = 404, message = "計画済みのタスクが見つからなかった時"),
        @ApiResponse(code = 500, message = "サーバー内部でエラーが発生", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<List<PlannedTaskResponse>>> getAll(
        @ApiParam(required = true, value = "指定されたページ")
        @RequestParam("page") final int page,
        @ApiParam(required = true, value = "ページサイズ")
        @RequestParam("size") final int size,
        @ApiParam(value = "タスク開始時間")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @RequestParam(value = "startTime", required = false) final LocalDateTime startTime,
        @ApiParam(required = true, value = "タスク終了時間")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @RequestParam(value = "endTime") final LocalDateTime endTime) {

        //TODO JWTからaccountIdを取得する。
        final var accountId = 1L;
        final var pageReq = PageRequest.of(page, size);

        return this.service.findPlannedTasks(accountId,
                Objects.nonNull(startTime) ? ZonedDateTime.of(startTime, ZoneId.systemDefault()) : null,
                ZonedDateTime.of(endTime, ZoneId.systemDefault()), pageReq)
            .map(x -> x.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(x));
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
    public Mono<ResponseEntity<Void>> create(@Valid @RequestBody final PlannedTaskRequest req) {
        return Mono.just(ResponseEntity.created(null).build());
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

}
