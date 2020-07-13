package com.longi.mlp.gateway.exception;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.util.function.Supplier;
import com.longi.mlp.gateway.exception.code.GatewayErrorCodes;

import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 网关限流异常处理类
 */
@Slf4j
public class JsonSentinelGatewayBlockExceptionHandler implements WebExceptionHandler {
    private List<ViewResolver> viewResolvers;
    private List<HttpMessageWriter<?>> messageWriters;
    private final Supplier<ServerResponse.Context> contextSupplier = () -> {
        return new ServerResponse.Context() {
            public List<HttpMessageWriter<?>> messageWriters() {
                return JsonSentinelGatewayBlockExceptionHandler.this.messageWriters;
            }

            public List<ViewResolver> viewResolvers() {
                return JsonSentinelGatewayBlockExceptionHandler.this.viewResolvers;
            }
        };
    };

    public JsonSentinelGatewayBlockExceptionHandler(List<ViewResolver> viewResolvers, ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolvers;
        this.messageWriters = serverCodecConfigurer.getWriters();
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        } else {
            return !BlockException.isBlockException(ex) ? Mono.error(ex) :
                    this.handleBlockedRequest(exchange, ex).flatMap((response) ->
                            Mono.error(new ApiLimitAccessException(GatewayErrorCodes.LIMIT_API,
                                    exchange.getRequest().getPath().toString()))
                    );
        }
    }

    private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable) {
        return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
    }
}
