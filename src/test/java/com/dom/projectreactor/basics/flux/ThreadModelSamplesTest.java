package com.dom.projectreactor.basics.flux;

import org.junit.Test;
import reactor.test.StepVerifier;

public class ThreadModelSamplesTest {

    @Test
    public void testThread() {
        StepVerifier.create(ThreadModelSamples.simpleFlux())
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    public void threadParallel() {
        StepVerifier.create(ThreadModelSamples.threadParallel())
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    public void threadPublishOn() {
        StepVerifier.create(ThreadModelSamples.threadPublishOn())
                .expectNextCount(10)
                .verifyComplete();
    }
}