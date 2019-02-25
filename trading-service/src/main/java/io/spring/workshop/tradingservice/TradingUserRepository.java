package io.spring.workshop.tradingservice;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
interface TradingUserRepository extends ReactiveMongoRepository<TradingUser, Long> {
    Mono<TradingUser> findByUserName(String userName);
}
