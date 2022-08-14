package jp.co.gh.filter.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingFilter implements WebFilter {

    private final ObjectMapper objMapper;

    /**
     * ${@inheritDoc}
     */
    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
        final var bodyCaptureExchange = new BodyCaptureExchange(exchange);
        return chain.filter(bodyCaptureExchange).doOnSuccess((se) -> {

            // if request body contains password, replace "*"
            final String reqBody;
            if (bodyCaptureExchange.getRequest().getFullBody().contains("password")) {
                reqBody = replacePasswordToAsterisk(bodyCaptureExchange.getRequest().getFullBody());
            } else {
                reqBody = bodyCaptureExchange.getRequest().getFullBody();
            }
            log.info("Request headers={}, method={}, path={}, body={}", exchange.getRequest().getHeaders(), exchange.getRequest().getMethod(),
                    exchange.getRequest().getPath(), reqBody);

            log.info("Response headers={}, status={}, body={}", exchange.getResponse().getHeaders(), exchange.getResponse().getStatusCode(),
                    bodyCaptureExchange.getResponse().getFullBody());
        });
    }

    /**
     * replace password to asterisk
     *
     * @param body request body
     * @return replaced asterisk
     */
    private String replacePasswordToAsterisk(final String body) {
        try {
            final var reqBodyAsJson = objMapper.readValue(body, JsonNode.class);
            final var password = reqBodyAsJson.get("password").asText();
            final var tmp = new StringBuilder();
            for (int i = 0, len = password.length(); i < len; i++) {
                tmp.append("*");
            }
            ((ObjectNode) reqBodyAsJson).put("password", tmp.toString());
            return reqBodyAsJson.toString();
        } catch (final JsonProcessingException e) {
            log.error("Catch LoggingFilter.filter", e);
            return "";
        }
    }

}