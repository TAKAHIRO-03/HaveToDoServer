package jp.co.gh.api.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jp.co.gh.api.payload.request.PlannedHabitRequest;
import jp.co.gh.api.payload.response.ApiErrorResponse;
import jp.co.gh.api.payload.response.PlannedHabitResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * 計画済み習慣のCRD処理をするコントローラー
 */
@Validated
@RestController
@RequestMapping(path = "/api/v1.0/plannedHabits", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlannedHabitController {

    /**
     * 計画済みの単一の習慣取得処理
     *
     * @param id 取得対象となるID
     * @return ユーザーに紐づいた計画済み習慣（単一）
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "計画済みの単一の習慣取得", notes = "ユーザーの計画済み習慣を１つ取得する。過去の計画済み習慣は取得しない。")
    @ApiResponses({
            @ApiResponse(code = 200, message = "計画済み習慣返却", response = PlannedHabitResponse.class),
            @ApiResponse(code = 400, message = "パラメーターが不正な時", response = ApiErrorResponse.class),
            @ApiResponse(code = 401, message = "認証・認可失敗", response = ApiErrorResponse.class),
            @ApiResponse(code = 404, message = "習慣", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "サーバー内部でエラーが発生", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<PlannedHabitResponse>> get(
            @ApiParam(required = true, value = "取得対象となるID")
            @PositiveOrZero @PathVariable final Long id) {
        return Mono.just(ResponseEntity.ok(null));
    }

    /**
     * ユーザーに紐づいた計画済み習慣取得処理
     *
     * @return 計画済み習慣（複数）
     */
    @GetMapping
    @ApiOperation(value = "計画済みの習慣取得", notes = "ユーザーの計画済み習慣を全て取得する。過去の計画済み習慣は取得しない。")
    @ApiResponses({
            @ApiResponse(code = 200, message = "計画済み習慣返却", response = PlannedHabitResponse.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "パラメーターが不正な時", response = ApiErrorResponse.class),
            @ApiResponse(code = 401, message = "認証・認可失敗", response = ApiErrorResponse.class),
            @ApiResponse(code = 404, message = "計画済みの習慣が見つからなかった時", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "サーバー内部でエラーが発生", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<List<PlannedHabitResponse>>> getAll(@PageableDefault Pageable page) {
        return Mono.just(ResponseEntity.ok(null));
    }

    /**
     * 習慣登録処理
     *
     * @param req 習慣登録パラメータ
     * @return 無し
     */
    @PostMapping
    @ApiOperation(value = "習慣登録", notes = "習慣を登録する。リピートのフラグにより複数習慣を登録することが出来る。")
    @ApiResponses({
            @ApiResponse(code = 201, message = "習慣登録成功"),
            @ApiResponse(code = 400, message = "パラメーターが不正な時", response = ApiErrorResponse.class),
            @ApiResponse(code = 401, message = "認証・認可失敗", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "サーバー内部でエラーが発生", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<Void>> create(@Valid @RequestBody final PlannedHabitRequest req) {
        return Mono.just(ResponseEntity.created(null).build());
    }

    /**
     * 計画済み習慣削除処理
     *
     * @param id 削除対象となる計画済み習慣
     * @return 無し
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "計画済みの単一習慣削除", notes = "ユーザーの計画済み習慣を１つ削除する。リピートで登録した習慣の場合、複数削除することが出来る。")
    @ApiResponses({
            @ApiResponse(code = 204, message = "計画済み習慣の削除成功"),
            @ApiResponse(code = 400, message = "パラメーターが不正な時", response = ApiErrorResponse.class),
            @ApiResponse(code = 401, message = "認証・認可失敗", response = ApiErrorResponse.class),
            @ApiResponse(code = 404, message = "計画済みの習慣が見つからない", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "サーバー内部でエラーが発生", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<Void>> delete(
            @ApiParam(required = true, value = "削除対象となるID")
            @PositiveOrZero @PathVariable Long id,
            @ApiParam(required = true, value = "リピートされたものを削除するか")
            @RequestParam boolean repeatDeleteFlg) {
        return Mono.just(ResponseEntity.noContent().build());
    }

}
