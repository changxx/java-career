package com.changxx.practice.localcache;

public class Test {

    public Cache<String, UserInfo> cache = CacheBuilder.newBuilder().setExpireAfterWrite(2000L * 60 * 5).build(new CacheLoader<String, UserInfo>() {
        public UserInfo load(String userId) {
            // 从库中获取一个key对应的值
            return new UserInfo(userId, "userName:" + System.currentTimeMillis());
        }
    }, SegmentLocalCache.class);

    // 是否初始化
    private volatile boolean init = false;

    /**
     * 初始化用户信息缓存
     */
    public void initUserInfoCache() {
        if (!init) {
            synchronized (Test.class) {
                try {
                    // 从库中获取
                } finally {
                    init = true;
                }
            }
        }
    }

    /**
     * 根据用户id从缓存中获取用户信息
     */
    public UserInfo getUserInfoFromCacheById(String userId) {
        return cache.get(userId);
    }

    /**
     * 根据用户id更新缓存用户信息
     */
    public void updateUserInfoCache(String userId) {
        // 从库中获取
        cache.reload(userId);
    }

    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < 4; i++) {
                        UserInfo.cache.get("sdfs" + i);
                    }
                }
            });
            thread.start();
        }

        Thread.sleep(300);

        for (int i = 2; i > 0; i--) {
            System.out.println(UserInfo.cache.getIfPersent("sdfs" + i));
        }
    }

}
