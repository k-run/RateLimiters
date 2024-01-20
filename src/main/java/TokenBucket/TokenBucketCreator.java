package TokenBucket;

import java.util.HashMap;
import java.util.Map;

public class TokenBucketCreator {
    Map<Integer, TokenBucket> tokenBucketMap;

    public TokenBucketCreator(int id) {
        this.tokenBucketMap = new HashMap<>();
        tokenBucketMap.put(id, new TokenBucket(5, 5));
    }

    void accessApplication(int id) {
        if(tokenBucketMap.get(id).grantAccess()) {
            System.out.println(Thread.currentThread().getName() + " -> " + "200 OK. Able to access app");
        }

        else {
            System.out.println(Thread.currentThread().getName() + " -> " + "429 Too Many Requests. Try again later");
        }
    }

}
