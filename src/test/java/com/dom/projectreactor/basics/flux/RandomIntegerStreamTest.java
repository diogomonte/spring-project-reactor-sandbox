package com.dom.projectreactor.basics.flux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import reactor.test.StepVerifier;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class RandomIntegerStreamTest {

	@Test
	public void itShouldSubscribeAndGetStringValues() {
		StepVerifier.create(RandomIntegerStream.numberToString(7, 10))
			.expectNext("7", "8", "9", "10")
			.verifyComplete();
	}
}