package TokenBucket;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Bucket {
    private AtomicLong lastAccessedAt;
    private AtomicInteger tokens;

    public Bucket(long lastAccessedAt, int tokens) {
        this.lastAccessedAt = new AtomicLong(lastAccessedAt);
        this.tokens = new AtomicInteger(tokens);
    }

    public AtomicLong getLastAccessedAt() {
        return lastAccessedAt;
    }

    public void setLastAccessedAt(AtomicLong lastAccessedAt) {
        this.lastAccessedAt = lastAccessedAt;
    }

    public AtomicInteger getTokens() {
        return tokens;
    }

    public void setTokens(AtomicInteger tokens) {
        this.tokens = tokens;
    }
}
