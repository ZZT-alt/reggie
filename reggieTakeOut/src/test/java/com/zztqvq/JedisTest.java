package com.zztqvq;

import org.junit.jupiter.api.Test;
//import org.junit.Test
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class JedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test01() {
        Jedis jedis = new Jedis("localhost", 6379);

//        jedis.set("username", "XiaoMing");

        String value = jedis.get("name");

        System.out.println(value);

//        jedis.hset("1107","age","20");


        for (String key : jedis.keys("*")) {
            System.out.println(key);
        }


//        jedis.del("username");
        jedis.close();
    }

    @Test
    public void testString() {

        ValueOperations valueOperations = redisTemplate.opsForValue();

        redisTemplate.opsForValue().set("city123", "beijing");

        String value = (String) redisTemplate.opsForValue().get("city123");

        valueOperations.set("a", "zztqvq", 10L, TimeUnit.SECONDS);

        Boolean absent = valueOperations.setIfAbsent("city", "111");

        System.out.println(absent);

        System.out.println(value);

    }

    @Test
    public void testHash() {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("testHash", "name", "xiaoming");
        hashOperations.put("testHash", "age", "20");

//        String name = (String) hashOperations.get("testHash", "name");
//        System.out.println(name);
//
//        for (Object testHash : hashOperations.keys("testHash")) {
//            System.out.println(testHash);
//        }
//
//        for (Object testHash : hashOperations.values("testHash")) {
//            System.out.println(testHash);
//        }

        Map<String, String> testHash = hashOperations.entries("testHash");
        System.out.println(testHash);
        for (Map.Entry<String, String> entry : testHash.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    @Test
    public void testList() {
        ListOperations listOperations = redisTemplate.opsForList();
//        listOperations.leftPush("testList", "a");
//        listOperations.leftPushAll("testList", "b", "c");
        List<String> list = listOperations.range("testList", 0, -1);
        for (String o : list) {
            System.out.println(o);
        }
        String value = (String) listOperations.rightPop("testList");
        System.out.println(value);
    }

    @Test
    public void testSet() {
        SetOperations setOperations = redisTemplate.opsForSet();

//        setOperations.add("testSet","1","2","3","4","5");


        setOperations.remove("testSet", "3", "99");
        Set<String> testSet = setOperations.members("testSet");

        for (String s : testSet) {
            System.out.println(s);
        }
    }

    @Test
    public void testZSet() {
//        System.out.println("hello world");
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        //存值
//        zSetOperations.add("testZSet", "a", 10);
//        zSetOperations.add("testZSet", "b", 1);
//        zSetOperations.add("testZSet", "c", 100);
//        zSetOperations.add("testZSet", "a", 7);
        zSetOperations.incrementScore("testZSet", "c", 99);

        Set<String> testZSet = zSetOperations.range("testZSet", 0, -1);

        for (String s : testZSet) {
            System.out.println(s);
        }

        zSetOperations.remove("testZSet", "a", "b");

    }

    @Test
    public void testCommon() {


        for (Object key : redisTemplate.keys("*")) {
            redisTemplate.delete(key.toString());
            System.out.println(key.toString());

        }

    }
}
