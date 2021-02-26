package com.online.taxi.order.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yueyi2019
 */
@Component
public class RedisConfig {

    @Autowired
    RedisSentinelProperties properties;

    @Value("${spring.redis.database}")
    private int database;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;
    //以下为redisson锁，哨兵
//    @Bean(name = "redisson")
//    @Order(1)
//    public Redisson getRedisson(){
//
//        Config config = new Config();
//        config.useSentinelServers()
//                .setMasterName(properties.getMasterName())
//                .addSentinelAddress(properties.getAddress())
//                .setDatabase(0);
//        return (Redisson) Redisson.create(config);
//    }
    //以上为redisson锁

    //以下为红锁
//    @Bean
//    public RedissonClient redissonRed1(){
//        Config config = new Config();
//        config.useSingleServer().setAddress("10.202.80.117:7974").setDatabase(1).setPassword("cbhtzey59OM");
//        return Redisson.create(config);
//    }
//    @Bean
//    public RedissonClient redissonRed2(){
//        Config config = new Config();
//        config.useSingleServer().setAddress("62.234.212.139:6379").setDatabase(1).setPassword("KwaQ6DNJZZJRv43r");
//        return Redisson.create(config);
//    }
//    @Bean
//    public RedissonClient redissonRed3(){
//        Config config = new Config();
//        config.useSingleServer().setAddress("127.0.0.1:6379").setDatabase(0);
//        return Redisson.create(config);
//    }
    //以上为红锁
    
    
    
    // 单个redis
    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    	StringRedisTemplate redisTemplate = new StringRedisTemplate();
    	redisTemplate.setConnectionFactory(redisConnectionFactory);
    	return redisTemplate;
    	
    }
    
    /**
     * 单个redisson
     * @return
     */
    @Bean
    public RedissonClient redissonClient() {
    	Config config = new Config();
    	config.useSingleServer().setAddress(this.host+":"+this.port).setDatabase(this.database).setPassword(this.password);
    	return Redisson.create(config);
    }
}
