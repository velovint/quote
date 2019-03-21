package io.spring.workshop.tradingservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.MarkerFactory;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.util.context.Context;

import java.util.Optional;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class LoggingTest {
    private static Logger log = LoggerFactory.getLogger(LoggingTest.class);

    @Test
    public void testLogging() {
        log.info("Test blocking messasge with context {}", "data");
        Mono.just("something")
                .doOnEach(signal -> {
                    if (!signal.isOnNext()) return;
                    final String contextData = signal.getContext().getOrDefault("dataKey", "null");
                    log.info("Test reactive message with context {}", contextData);
                })
                .subscriberContext(Context.of("dataKey", "data"))
                .block();
    }

    private static <T> Consumer<Signal<T>> logOnNext(Consumer<T> logStatement) {
        return signal -> {
            if (!signal.isOnNext()) return;
            Optional<String> apiIDMaybe = signal.getContext().getOrEmpty("apiID");
            if (apiIDMaybe.isPresent()) {
                try (MDC.MDCCloseable closeable = MDC.putCloseable("apiID", apiIDMaybe.get())) {
                    logStatement.accept(signal.get());
                }
            } else {
                logStatement.accept(signal.get());
            }
        };
    }
}