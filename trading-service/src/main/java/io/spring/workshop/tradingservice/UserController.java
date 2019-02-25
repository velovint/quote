package io.spring.workshop.tradingservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final TradingUserRepository userRepository;

    public UserController(TradingUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Flux<TradingUser> listUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{username}")
    public Mono<TradingUser> showUser(@PathVariable("username") String username) {
        return userRepository.findByUserName(username);
    }
}
