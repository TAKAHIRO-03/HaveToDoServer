package jp.co.havetodo.api.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jp.co.havetodo.api.payload.request.AccountPasswordRequest;
import jp.co.havetodo.api.payload.request.AccountRequest;
import jp.co.havetodo.api.payload.response.ApiErrorResponse;
import jp.co.havetodo.api.payload.response.LoginResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * アカウント登録・更新のコントローラー
 */
@Validated
@RestController
@RequestMapping(path = "/api/v1.0", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    /**
     * アカウント登録処理
     *
     * @param req アカウント登録パラメータ
     * @return 無し
     */
    @PostMapping("/accounts")
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
    @DeleteMapping("/accounts")
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


    /**
     * パスワード更新処理
     *
     * @param req パスワード更新パラメータ
     * @return 無し
     */
    @PatchMapping("/accounts/password")
    @ApiOperation(value = "アカウントのパスワード更新", notes = "アカウントのパスワードを更新する。")
    @ApiResponses({
            @ApiResponse(code = 204, message = "アカウントのパスワード更新成功"),
            @ApiResponse(code = 400, message = "パラメーターが不正な時", response = ApiErrorResponse.class),
            @ApiResponse(code = 401, message = "認証・認可失敗", response = ApiErrorResponse.class),
            @ApiResponse(code = 404, message = "対象のアカウントが見つからない", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "サーバー内部でエラーが発生", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<Void>> password(@Valid @RequestBody final AccountPasswordRequest req) {
        return Mono.just(ResponseEntity.created(null).build());
    }


    /**
     * アカウント登録認証
     *
     * @param authToken 認証トークン
     * @return 無し
     */
    @GetMapping("/auth/accounts")
    @ApiOperation(value = "アカウント登録認証", notes = "アカウント本登録をする。")
    @ApiResponses({
            @ApiResponse(code = 303, message = "認証成功。"),
            @ApiResponse(code = 401, message = "検証に失敗、ユーザーが存在しない。", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<LoginResponse>> authAccounts(@RequestParam("authToken") final String authToken) {
        return Mono.just(ResponseEntity.ok(null));
    }

}
