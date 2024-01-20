package SlidingWindow;

import java.util.HashMap;
import java.util.Map;

public class SlidingWindowBucketCreator {
    private Map<Integer, SlidingWindowImpl> userBucketMap;

    public SlidingWindowBucketCreator(int userId) {
        userBucketMap = new HashMap<>();
        userBucketMap.put(userId, new SlidingWindowImpl(5, 1));
    }

    void accessApplication(int id) {
        if(userBucketMap.get(id).grantAccess()) {
            System.out.println("200 OK !...");
        }
        else System.out.println("429 Too Many Requests!.. Try again later.");
    }
}
