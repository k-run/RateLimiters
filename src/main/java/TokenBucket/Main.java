package TokenBucket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        TokenBucketImpl tokenBucketimpl = new TokenBucketImpl(1);


        //TokenBucketCreator tokenBucketCreator = new TokenBucketCreator(1);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.execute(()-> tokenBucketimpl.hitAPI(1));
        }

        executorService.shutdown();
    }
}
