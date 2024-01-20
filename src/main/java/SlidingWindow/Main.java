package SlidingWindow;

import SlidingWindow.SlidingWindowImpl;
import TokenBucket.TokenBucketImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        SlidingWindowBucketCreator userBucketCreator = new SlidingWindowBucketCreator(1);
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 100; i++) {
            executorService.execute(()-> userBucketCreator.accessApplication(1));
        }

        executorService.shutdown();
    }
}
