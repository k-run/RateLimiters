package TokenBucket;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


public class TokenBucket implements IRateLimiter {
    // bucket capacity means how many tokens allowed per user per sec
    private final int bucketCapacity;
    // refresh rate means after how many sec the tokens are renewed
    private final int refreshRate;
    // currentCapacity means currently how many tokens used
    private final AtomicInteger currentCapacity;
    // lastUpdatedAt means the latest timestamp at which a token was given
    private final AtomicLong lastUpdatedAt;

    public TokenBucket(int bucketCapacity, int refreshRate) {
        this.bucketCapacity = bucketCapacity;
        this.refreshRate = refreshRate;
        this.currentCapacity = new AtomicInteger(bucketCapacity);
        this.lastUpdatedAt = new AtomicLong(System.currentTimeMillis());
    }

    @Override
    public boolean grantAccess() {
        refreshBucket();
        if(this.currentCapacity.get() > 0) {
            this.currentCapacity.decrementAndGet();
            return true;
        }

        return false;
    }

    private void refreshBucket() {
        long currentTime = System.currentTimeMillis();
        int additionalTokens = (int) ((currentTime - lastUpdatedAt.get())/1000 * refreshRate);
        System.out.println("additionalTokens = " + additionalTokens);
        int currCapacity = Math.min(currentCapacity.get() + additionalTokens, bucketCapacity);
        System.out.println("currCapacity = " + currCapacity);
        currentCapacity.getAndSet(currCapacity);
        lastUpdatedAt.getAndSet(currentTime);
    }
}
