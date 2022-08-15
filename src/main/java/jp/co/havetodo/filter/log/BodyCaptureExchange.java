package jp.co.havetodo.filter.log;

import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

public class BodyCaptureExchange extends ServerWebExchangeDecorator {

    private BodyCaptureRequest bodyCaptureRequest;
    private BodyCaptureResponse bodyCaptureResponse;

    BodyCaptureExchange(final ServerWebExchange exchange) {
        super(exchange);
        this.bodyCaptureRequest = new BodyCaptureRequest(exchange.getRequest());
        this.bodyCaptureResponse = new BodyCaptureResponse(exchange.getResponse());
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public BodyCaptureRequest getRequest() {
        return bodyCaptureRequest;
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public BodyCaptureResponse getResponse() {
        return bodyCaptureResponse;
    }

}