package jp.co.gh.filter;

import jp.co.gh.filter.payload.BodyCaptureExchange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoggingFilter implements WebFilter {

    /**
     * ${@inheritDoc}
     */
    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
        final var bodyCaptureExchange = new BodyCaptureExchange(exchange);
        return chain.filter(bodyCaptureExchange).doOnSuccess((se) -> {
            log.info("Request headers={}, method={}, path={}, body={}", exchange.getRequest().getHeaders(), exchange.getRequest().getMethod(),
                    exchange.getRequest().getPath(), bodyCaptureExchange.getRequest().getFullBody());
            log.info("Response headers={}, status={}, body={}", exchange.getResponse().getHeaders(), exchange.getResponse().getStatusCode(),
                    bodyCaptureExchange.getResponse().getFullBody());
        });
    }

}