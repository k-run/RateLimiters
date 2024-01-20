package SlidingWindow;

import TokenBucket.IRateLimiter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SlidingWindowImpl implements IRateLimiter {
    private final Queue<Long> slidingWindow;
    private final int limit;
    private final int windowTimeInSeconds;

    public SlidingWindowImpl(int limit, int windowTimeInSeconds) {
        this.limit = limit;
        this.windowTimeInSeconds = windowTimeInSeconds;
        this.slidingWindow = new ConcurrentLinkedQueue<>();
    }


    @Override
    public boolean grantAccess() {
        long currentTime = System.currentTimeMillis();
        slideWindow(currentTime);
        if(slidingWindow.size() < limit) {
            slidingWindow.add(currentTime);
            return true;
        }

        return false;
    }

    private void slideWindow(long currentTime) {
        if(slidingWindow.isEmpty()) return;

        long calculatedTime = (currentTime - slidingWindow.peek())/1000;

        while(calculatedTime >= windowTimeInSeconds) {
            slidingWindow.poll();
            if(slidingWindow.isEmpty()) break;
            calculatedTime = (currentTime - slidingWindow.peek())/1000;
        }
    }
}
