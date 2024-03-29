package jp.co.havetodo.api.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jp.co.havetodo.api.payload.response.ApiErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

/**
 * 実行済みタスクの開始と終了の更新処理をするコントローラー
 */
@Validated
@RestController
@RequestMapping(path = "/api/v1.0/executedTasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExecutedTaskController {

    /**
     * 実行時済みタスクの更新処理
     *
     * @param id
     * @param status 実行済みタスクの更新パラメータ
     * @return 無し
     */
    @PatchMapping("/{id}/{status}")
    @ApiOperation(value = "実行済みタスクデータの更新", notes = "実行済みタスクデータを開始または終了する。")
    @ApiResponses({
            @ApiResponse(code = 204, message = "実行済みタスク更新成功"),
            @ApiResponse(code = 400, message = "パラメーターが不正な時", response = ApiErrorResponse.class),
            @ApiResponse(code = 401, message = "認証・認可失敗", response = ApiErrorResponse.class),
            @ApiResponse(code = 404, message = "実行済みのタスクが見つからない", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "サーバー内部でエラーが発生", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<Void>> update(
            @ApiParam(required = true, value = "更新対象となるID")
            @PositiveOrZero @PathVariable final long id,
            @ApiParam(required = true, value = "開始または終了")
            @Pattern(regexp = "start|end") @PathVariable final String status
    ) {
        return Mono.just(ResponseEntity.created(null).build());
    }

}
