package io.spring.workshop.tradingservice;

import net.logstash.logback.argument.StructuredArguments;
import net.logstash.logback.marker.Markers;
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

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

    @Test
    public void loggingWithSignalLogger() {
        SignalLogger signalLogger = new SignalLogger(log);

        Mono.just("something")
                .doOnEach(signalLogger.info("Test signal logger with context ->{}<-"))
                .subscriberContext(Context.of("dataKey", "data"))
                .block();
    }

    @Test
    public void loggingWithMonoSubscriberContext() {
        Mono.just("something")
                .zipWith(Mono.subscriberContext())
                .doOnSuccess(dataWithContext -> {
                    log.info("Finished processing of {} with context {}",
                            dataWithContext.getT1(),
                            StructuredArguments.entries(contextToMap(dataWithContext.getT2())));
                })
                .subscriberContext(Context.of("dataKey", "data"))
                .block();
    }

    private static class SignalLogger {

        private Logger log;

        public SignalLogger(Logger log) {
            this.log = log;
        }

        public Consumer<Signal<?>> info(String message) {
            return signal -> {

                if (!signal.isOnNext()) return;
                final Map<Object, Object> context = contextToMap(signal.getContext());
                log.info(message, StructuredArguments.entries(context));
            };
        }
    }

    private static Map<Object, Object> contextToMap(Context context) {
        return context.stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }
}
