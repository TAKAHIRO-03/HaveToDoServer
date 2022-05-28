package jp.co.gh.api.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jp.co.gh.api.payload.request.AccountRequest;
import jp.co.gh.api.payload.response.ApiErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(path = "/api/v1.0/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    /**
     * アカウント登録処理
     *
     * @param req アカウント登録パラメータ
     * @return 無し
     */
    @PostMapping
    @ApiOperation(value = "アカウント登録", notes = "アカウントの登録処理。")
    @ApiResponses({
            @ApiResponse(code = 201, message = "アカウント登録成功"),
            @ApiResponse(code = 400, message = "パラメーターが不正な時", response = ApiErrorResponse.class),
            @ApiResponse(code = 409, message = "アカウントが重複した時", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "サーバー内部でエラーが発生", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<Void>> create(@Valid @RequestBody final AccountRequest req) {
        return Mono.just(ResponseEntity.created(null).build());
    }


    /**
     * アカウント削除処理
     *
     * @return 無し
     */
    @DeleteMapping
    @ApiOperation(value = "アカウント削除", notes = "アカウントを削除する。")
    @ApiResponses({
            @ApiResponse(code = 204, message = "アカウントの削除成功"),
            @ApiResponse(code = 400, message = "パラメーターが不正な時", response = ApiErrorResponse.class),
            @ApiResponse(code = 401, message = "認証・認可失敗", response = ApiErrorResponse.class),
            @ApiResponse(code = 404, message = "対象のアカウントが見つからない", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "サーバー内部でエラーが発生", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<Void>> delete() {
        return Mono.just(ResponseEntity.created(null).build());
    }

}
