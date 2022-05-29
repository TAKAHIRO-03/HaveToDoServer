package jp.co.gh.api.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jp.co.gh.api.payload.request.ReissuePassRequest;
import jp.co.gh.api.payload.response.ApiErrorResponse;
import jp.co.gh.api.payload.response.LoginResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Map;

@Validated
@RestController
@RequestMapping(path = "/api/v1.0", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReissuePassController {

    /**
     * パスワード再登録受付処理
     *
     * @param req パスワード再登録パラメータ
     * @return 無し
     */
    @PostMapping("/reissuePass/reception")
    @ApiOperation(value = "パスワード再登録受付", notes = "アカウントの登録受付処理。")
    @ApiResponses({
            @ApiResponse(code = 201, message = "パスワード再登録受付成功"),
            @ApiResponse(code = 400, message = "パラメーターが不正な時", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "サーバー内部でエラーが発生", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<Void>> reception(
            @ApiParam(required = true, value = "メールアドレス", example = "hogehoge@example.co.jp")
            @Valid @RequestBody final Map<@Pattern(regexp = "mail") String, @NotBlank @Email String> req) {
        return Mono.just(ResponseEntity.created(null).build());
    }


    /**
     * パスワード再登録認証
     *
     * @param authToken 認証トークン
     * @return 無し
     */
    @GetMapping("/auth/reissuePass")
    @ApiOperation(value = "パスワード再登録認証", notes = "パスワード再登録認証をする。")
    @ApiResponses({
            @ApiResponse(code = 200, message = "認証成功。", response = LoginResponse.class),
            @ApiResponse(code = 401, message = "検証に失敗、ユーザーが存在しない。", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<LoginResponse>> authReissuePass(@RequestParam("authToken") String authToken) {
        return Mono.just(ResponseEntity.ok(null));
    }

    /**
     * パスワード再登録処理
     *
     * @param req パスワード再登録パラメータ
     * @return 無し
     */
    @PostMapping("/reissuePass")
    @ApiOperation(value = "パスワード再登録", notes = "アカウントの登録処理。")
    @ApiResponses({
            @ApiResponse(code = 201, message = "パスワード再登録成功"),
            @ApiResponse(code = 400, message = "パラメーターが不正な時", response = ApiErrorResponse.class),
            @ApiResponse(code = 401, message = "認証・認可失敗", response = ApiErrorResponse.class),
            @ApiResponse(code = 500, message = "サーバー内部でエラーが発生", response = ApiErrorResponse.class)
    })
    public Mono<ResponseEntity<Void>> create(@Valid @RequestBody final ReissuePassRequest req) {
        return Mono.just(ResponseEntity.created(null).build());
    }

}
