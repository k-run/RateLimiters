package TokenBucket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TokenBucketImpl {

    private final Map<Integer, Bucket> tokenBucket;
    private final AtomicInteger LIMIT;

    public TokenBucketImpl(int userId) {
        this.LIMIT = new AtomicInteger();
        this.LIMIT.set(5);
        Bucket bucket = new Bucket(System.currentTimeMillis(), this.LIMIT.get());
        System.out.println("user createdAt = " + bucket.getLastAccessedAt());
        this.tokenBucket = new ConcurrentHashMap<>();
        tokenBucket.put(userId, bucket);
    }

    public boolean hitAPI(int userId) {
            // refresh the tokens if diff btwn current time and last accessed time > 1s
            Bucket bucket = tokenBucket.get(userId);
            if(System.currentTimeMillis() - bucket.getLastAccessedAt().get() > 1000) {
                System.out.println("currentTime() - lastAccessedAt = " + (System.currentTimeMillis() - bucket.getLastAccessedAt().get()));
                System.out.println("bucket.getTokens() = " + bucket.getTokens());
                bucket.setLastAccessedAt(new AtomicLong(System.currentTimeMillis()));
                bucket.setTokens(new AtomicInteger(LIMIT.get()-1));
                tokenBucket.put(userId, bucket);
                System.out.println("200 OK");
                return true;
            }

            else {
                // if user hits API within the same sec
                int currentTokens = bucket.getTokens().get();
                if(currentTokens > 0) {
                    // if user has enough tokens
                    System.out.println("bucket.getTokens() = " + bucket.getTokens());
                    bucket.setTokens(new AtomicInteger(currentTokens-1));
                    bucket.setLastAccessedAt(new AtomicLong(System.currentTimeMillis()));
                    tokenBucket.put(userId, bucket);
                    System.out.println(Thread.currentThread().getName() + " -> " + "200 OK");
                    return true;
                }

                else {
                    System.out.println(Thread.currentThread().getName() + " -> " + "429 Too many requests. Try again later..");
                    return false;
                }
            }
        }
    }



