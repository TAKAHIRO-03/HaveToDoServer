package jp.co.gh.api.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jp.co.gh.api.payload.request.LoginRequest;
import jp.co.gh.api.payload.response.ApiErrorResponse;
import jp.co.gh.api.payload.response.LoginResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * ログイン・ログアウト処理をするコントローラー
 */
@RestController
@RequestMapping(path = "/api/v1.0", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    /**
     * ログイン処理
     *
     * @param loginReq ログイン時のパラメーター
     * @return JWT等の認証情報
     */
    @PostMapping("/login")
    @ApiOperation(value = "ログイン", notes = "ユーザーの検証を行い、アクセストークンを返却する。")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ログイン成功。", response = LoginResponse.class),
            @ApiResponse(code = 401, message = "検証に失敗、ユーザーが存在しない。", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<LoginResponse>> login(@RequestBody final LoginRequest loginReq) {
        return Mono.just(ResponseEntity.ok(null));
    }

    /**
     * ログアウト処理
     *
     * @return 無し
     */
    @DeleteMapping("/logout")
    @ApiOperation(value = "ログアウト", notes = "アクセストークン、リフレッシュトークンを削除する")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ログアウト成功。"),
            @ApiResponse(code = 401, message = "認証・認可失敗", response = ApiErrorResponse.class),
    })
    public Mono<ResponseEntity<Void>> logout() {
        return Mono.just(ResponseEntity.ok(null));
    }
}
