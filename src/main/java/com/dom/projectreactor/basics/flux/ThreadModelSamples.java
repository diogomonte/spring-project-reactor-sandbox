package com.dom.projectreactor.basics.flux;

import java.time.Duration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class ThreadModelSamples {

    static Flux<String> simpleFlux() {
        return Flux.range(0, 5)
                .log()
                .flatMap(i -> Mono
                        .just("flatMap on [" + Thread.currentThread().getName() + "] -> " + i)
                        .delayElement(Duration.ofMillis(50)), 1);
    }

    static Flux<String> threadPublishOn() {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 6);

        return Flux
                .range(1, 10)
                .doOnNext(x -> System.out.println("before " + x + " [" + Thread.currentThread().getName() + "]"))
                .publishOn(s)
                .flatMap(i -> Mono.just(i).map(x -> 10 + x).delayElement(Duration.ofSeconds(1)))
                .doOnNext(x -> System.out.println("after " + x + " [" + Thread.currentThread().getName() + "]"))
                .map(i -> "value " + i);
    }

    static Flux<Integer> threadParallel() {
        return Flux.range(0, 5)
                .doOnNext(x -> System.out.println("x " + x + " [" + Thread.currentThread().getName() + "]"))
                .collectList()
                .flatMapMany(i -> Flux.fromIterable(i)
                        .doOnNext(v -> System.out.println("iterable " + v + " [" + Thread.currentThread().getName() + "]"))
                        .flatMap(v -> Mono.just(v)
                                .doOnNext(a -> System.out.println("mono just" + a + " [" + Thread.currentThread().getName() + "]"))
                                .map(x -> x + 10)
                                .delayElement(Duration.ofMillis(50)), 1));
    }
}
