package io.spring.workshop.stockquotes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class QuoteRouter {
    @Bean
    RouterFunction<ServerResponse> router(QuoteHandler quoteHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/hello"), quoteHandler::hello);
    }
}
