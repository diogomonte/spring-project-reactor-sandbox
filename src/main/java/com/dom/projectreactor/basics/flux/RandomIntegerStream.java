package com.dom.projectreactor.basics.flux;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomIntegerStream {

	/**
	 * Creates a stream of integers
	 */
	private static Flux<Integer> createRandomInteger(int origin, int bound) {
		if (bound <= origin) {
			throw new IllegalArgumentException("Origin must be bigger than bound");
		}
		
		return Flux.generate(() -> origin, (number, sink) -> {
			sink.next(number);
			if (number >= bound) {
				sink.complete();
			}
			return number + 1;
		});
	}

	/**
	 * Subscribes to stream an convert values to string
	 */
	public static Flux<String> numberToString(int origin, int bound) {
		return createRandomInteger(origin, bound)
				.doOnNext(n -> System.out.println("Next number: " + n))
				.map(String::valueOf);
	}

	public static void main(String[] args) throws InterruptedException {
		RandomIntegerStream.numberToString(0, 15).onErrorStop().subscribe();
		while (true) {
			Thread.sleep(5000);
		}
		
	}

}
