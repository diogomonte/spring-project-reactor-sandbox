package com.dom.projectreactor;

import com.dom.projectreactor.basics.flux.FluxSimpleSamples;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
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
}