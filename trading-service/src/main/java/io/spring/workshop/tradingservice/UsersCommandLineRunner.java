package io.spring.workshop.tradingservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class UsersCommandLineRunner implements CommandLineRunner {
    private final TradingUserRepository userRepository;

    public UsersCommandLineRunner(TradingUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.insert(new TradingUser("Dow", "Jones"))
                .block(Duration.ofSeconds(1));
    }
}
