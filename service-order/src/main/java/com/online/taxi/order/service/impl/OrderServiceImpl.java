package com.online.taxi.order.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.online.taxi.common.entity.Order;
import com.online.taxi.order.dao.OrderMapper;
import com.online.taxi.order.service.OrderService;

import java.util.List;
import java.util.concurrent.Future;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderMapper mapper;
	
	public boolean grab(int orderId, int driverId) {
		Order order = mapper.selectByPrimaryKey(orderId);
		 try {
             Thread.sleep(1000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
		if(order.getStatus().intValue() == 0) {
			order.setStatus(1);
			mapper.updateByPrimaryKeySelective(order);
			
			return true;
		}
		return false;
		
	}

	@HystrixCollapser(batchMethod = "batchMergeOrder",scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
					//请求时间间隔在 20ms 之内的请求会被合并为一个请求,默认为 10ms
					collapserProperties = {
									 @HystrixProperty(name = "timerDelayInMilliseconds",value = "20"),
									//设置触发批处理执行之前，在批处理中允许的最大请求数。
									 @HystrixProperty(name = "maxRequestsInBatch",value = "200" )
	})
	public Order orderById(Integer id){
		System.out.println("orderById==");
		return null;
	}
	/**
	 * 1.@HystrixCommand:表示当前的方法开启熔断
	 2.请求合并完毕后触发的方法，要和batchMethod 内的名字一致
	 3.在请求合并完毕后会将合并的参数的使用list集合的方式进行传递
	 *
	 * @return
	 */
	@HystrixCommand
	public List<Order> batchMergeOrder(List<Integer> ids){
		List<Order> orders = mapper.selectByIds(ids);
		System.out.println(orders.size());
		System.out.println("batchMergeOrder==");
		return orders;
	}
}
