package jp.co.gh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger（API仕様書自動生成ライブラリ）の設定情報を定義するクラス
 */
@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    /**
     * Swaggerの各種設定をするBeanを定義。
     *
     * @return {@link springfox.documentation.spring.web.plugins.Docket} bean
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }
}
