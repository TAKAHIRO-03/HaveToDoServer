package jp.co.gh.api.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0")
public class AccountController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
