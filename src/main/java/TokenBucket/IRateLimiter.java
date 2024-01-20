package TokenBucket;

public interface IRateLimiter {
    boolean grantAccess();
}
