package com.rdcentermrzhi.java.demo.redis.lock;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
 

public class MsTest {
    
    static ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("spring-redis.xml");
    

     public static void main(String[] args) {
          RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>)appCtx.getBean("redisTemplate",RedisTemplate.class);
          
          
          //RedisUtil redisUtil=(RedisUtil) appCtx.getBean("redisUtil");  
          System.out.println("开始");
          
            MsService service = new MsService();
            
            for (int i = 0; i < 200; i++) {
                ThreadB threadA = new ThreadB(service, redisTemplate, "MSKEY");
                threadA.start();
               
            }
           
    }
}