package com.changxx.practice.guava;

import com.changxx.practice.hash.User;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * GuavaCacheValueTest
 *
 * @author changxiangxiang
 * @date 2017/9/29
 */
public class GuavaCacheValueTest {

    @Test
    public void test() {
        LoadingCache<String, User> cache = CacheBuilder.newBuilder().maximumSize(10).expireAfterAccess(10, TimeUnit.SECONDS).build(new CacheLoader<String, User>() {
            @Override
            public User load(String key) throws Exception {
                User user = new User();
                user.setId(Integer.parseInt(key));
                return user;
            }
        });

        List<String> keys = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            keys.add(String.valueOf(i));
        }
        Map<String, User> map = null;
        try {
            map = cache.getAll(keys);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(map);
    }

}
