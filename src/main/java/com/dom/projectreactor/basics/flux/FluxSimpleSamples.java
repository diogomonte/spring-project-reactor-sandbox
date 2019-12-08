package com.dom.projectreactor.basics.flux;

import reactor.core.publisher.*;

public class FluxSimpleSamples {

	public static Flux<String> toUpperCase(String... data) {
		return Flux.just(data)
			.doOnNext(vowel -> System.out.println("next: " + vowel))
			.map(String::toUpperCase);
	}

	public static Flux<Integer> even(Integer... numbers) {
		return Flux.just(numbers)
				.filter(n -> n % 2 == 0);
	}
}
