package io.spring.workshop.stockquotes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class QuoteRouter {
    @Bean
    RouterFunction<ServerResponse> router(QuoteHandler quoteHandler) {
        return RouterFunctions
                .route(GET("/hello"), quoteHandler::echo)
                .andRoute(
                        POST("/echo")
                                .and(accept(MediaType.TEXT_PLAIN))
                                .and(contentType(MediaType.TEXT_PLAIN)),
                        quoteHandler::echo)
                .andRoute(
                        GET("/quotes").and(accept(MediaType.APPLICATION_STREAM_JSON)),
                        quoteHandler::streamQuotes)
                .andRoute(GET("/quotes"), quoteHandler::getQuotes);
    }
}
