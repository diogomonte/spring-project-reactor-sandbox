package com.dom.projectreactor.basics.flux;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.Map;

public class CustomEmitters {
    public static class Item {
        public Long next;
        public String result;

        public Item() {

        }

        public Item(Long next, String result) {
            this.next = next;
            this.result = result;
        }
    }

    public class CustomPublisher<T> implements Publisher<T> {
        private final T[] items;

        public CustomPublisher(T[] items) {
            this.items = items;
        }

        @Override
        public void subscribe(Subscriber<? super T> subscriber) {
            subscriber.onSubscribe(new Subscription() {
                int index = 0;
                boolean cancelled = false;

                @Override
                public void request(long n) {

                }

                @Override
                public void cancel() {
                    cancelled = true;
                }
            });
        }
    }
    public Flux<Item> getMyValue() {
        final var flux = Flux.from((Publisher<Item>) s -> s.onSubscribe(new Subscription() {
            Map<Long, Item> map = Map.of(1L, new Item(3L, ""), 2L, new Item(0L, ""), 3L, new Item(-1L, "STOP"));

            @Override
            public void request(long n) {
                if (n == -1 || n == 0) {
                    s.onComplete();
                }
                if (map.containsKey(n)) {
                    s.onNext(map.get(n));
                } else {
                    s.onError(new IllegalArgumentException());
                }
            }

            @Override
            public void cancel() {

            }
        }));

        flux.takeUntil(item -> item.next <= 0)
                .doOnNext(item -> System.out.println("Running next " + item.next))
                .take(1)
                .repeat();
        return flux;
    }

    public static void main(String[] args) throws InterruptedException {
        new CustomEmitters().getMyValue().subscribe();
    }
}
