package jp.co.gh.api.v1;

import jp.co.gh.domain.model.PlannedHabit;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * ログイン処理をするコントローラークラス
 */
@RestController
@RequestMapping(path = "/api/v1.0/plannedHabits", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlannedHabitController {

    @GetMapping("/{id}")
    public Mono<PlannedHabit> get(@PathVariable Long id) {
        return Mono.empty();
    }

    @GetMapping
    public Mono<List<PlannedHabit>> getAll() {
        return Mono.empty();
    }

    @PostMapping
    public Mono<Void> create() {
        return Mono.empty();
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return Mono.empty();
    }

}
