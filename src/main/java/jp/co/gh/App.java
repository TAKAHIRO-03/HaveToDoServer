package jp.co.gh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBootのエントリーポイントとなるクラス
 */
@SpringBootApplication
public class App {

    /**
     * エントリーポイント
     *
     * @param args 引数
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
