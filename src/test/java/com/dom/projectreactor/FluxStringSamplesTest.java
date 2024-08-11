package com.dom.projectreactor;

import com.dom.projectreactor.basics.flux.FluxSimpleSamples;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(BlockJUnit4ClassRunner.class)
public class FluxStringSamplesTest {

	@Test
	public void itShouldConvertToUpperCase() {
		StepVerifier.create(FluxSimpleSamples.toUpperCase("a", "e", "i", "o", "u"))
				.expectNext("A", "E", "I", "O", "U")
				.verifyComplete();
	}

	@Test
	public void itShouldGetOnlyEvenNumbers() {
		StepVerifier.create(FluxSimpleSamples.even(1, 2, 3, 4, 5 ,6, 7))
				.expectNext(2, 4, 6)
				.verifyComplete();
	}

	@Test
	public void switchIfEmpty() {
		final var flux = Flux.just(1)
				.flatMap(i -> Flux.empty())
				.switchIfEmpty(Flux.just(2))
				.map(i -> (int) i + 1);

		final var flux1 = Flux.just(Flux.empty())
				.switchIfEmpty(Flux.empty())
				.map(i -> 1);

		final var flux2 = Flux.just(1)
				.switchIfEmpty(Flux.empty())
				.map(i -> i + 1);

		StepVerifier.create(flux.concatWith(flux1).concatWith(flux2))
				.expectNext(3)
				.expectNext(1)
				.expectNext(2)
				.verifyComplete();
	}

	@Test
	public void zip() {
		final var flux = Flux.zip(Flux.just(1), Flux.just(2))
				.map(tuple -> tuple.getT1() + tuple.getT2());

		StepVerifier.create(flux)
				.expectNext(3)
				.verifyComplete();
	}

}