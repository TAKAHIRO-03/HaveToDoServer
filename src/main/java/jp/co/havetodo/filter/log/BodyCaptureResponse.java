package jp.co.havetodo.filter.log;

import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class BodyCaptureResponse extends ServerHttpResponseDecorator {

    private final StringBuilder body = new StringBuilder();

    BodyCaptureResponse(final ServerHttpResponse delegate) {
        super(delegate);
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public Mono<Void> writeWith(final Publisher<? extends DataBuffer> body) {
        final Flux<DataBuffer> buffer = Flux.from(body);
        return super.writeWith(buffer.doOnNext((x) -> this.body.append(StandardCharsets.UTF_8.decode(x.asByteBuffer()))));
    }

    String getFullBody() {
        return this.body.toString();
    }

}
