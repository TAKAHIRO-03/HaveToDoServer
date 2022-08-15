package jp.co.havetodo.filter.log;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

public class BodyCaptureRequest extends ServerHttpRequestDecorator {

    private final StringBuilder body = new StringBuilder();

    BodyCaptureRequest(final ServerHttpRequest delegate) {
        super(delegate);
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public Flux<DataBuffer> getBody() {
        return super.getBody().doOnNext((x) -> this.body.append(StandardCharsets.UTF_8.decode(x.asByteBuffer())));
    }

    String getFullBody() {
        return this.body.toString();
    }

}
