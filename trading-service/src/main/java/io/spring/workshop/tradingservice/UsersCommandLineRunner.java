package io.spring.workshop.tradingservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;

@Component
public class UsersCommandLineRunner implements CommandLineRunner {
    private final TradingUserRepository userRepository;

    public UsersCommandLineRunner(TradingUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository
                .insert(Arrays.asList(
                    new TradingUser("jhoeller", "Juergen Hoeller"),
                    new TradingUser("wilkinsona", "Andy Wilkinson")))
                .blockLast(Duration.ofSeconds(1));
    }
}
