package com.online.taxi.order.service.impl;

import com.online.taxi.common.dto.ResponseResult;
import com.online.taxi.common.entity.OrderLock;
import com.online.taxi.order.lock.RedisLock;
import com.online.taxi.order.service.GrabService;
import com.online.taxi.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author yueyi2019
 */
@Service("grabMyRedisLockService")
public class GrabMyRedisLockServiceImpl implements GrabService {

	@Autowired
	RedisLock redisLock;
	
	@Autowired
	OrderService orderService;
	
    @Override
    public ResponseResult grabOrder(int orderId , int driverId){
        //生成key
    	String lock = "order_"+(orderId+"");

    	
    	/*
    	 * 情况三：超时时间应该一次加，不应该分2行代码，
    	 * 
    	 */

			OrderLock ol = new OrderLock();
			ol.setOrderId(orderId);
			ol.setDriverId(driverId);
			redisLock.setOrderLock(ol);
			redisLock.lock();
    	try {
			System.out.println("司机:"+driverId+" 执行抢单逻辑");
			
            boolean b = orderService.grab(orderId, driverId);
            if(b) {
            	System.out.println("司机:"+driverId+" 抢单成功");
            }else {
            	System.out.println("司机:"+driverId+" 抢单失败");
            }
            
        } finally {
        	/**
        	 * 这种释放锁有，可能释放了别人的锁。
        	 */
//        	stringRedisTemplate.delete(lock.intern());
        	
        	/**
        	 * 下面代码避免释放别人的锁
        	 */
				redisLock.unlock();
        }
        return null;
    }
}
