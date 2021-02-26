package com.online.taxi.order.controller;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.online.taxi.common.dto.BaseResponse;
import com.online.taxi.common.dto.ResponseResult;
import com.online.taxi.common.entity.Order;
import com.online.taxi.order.dao.OrderMapper;
import com.online.taxi.order.hystrixcollapse.OrderCollapseCommand;
import com.online.taxi.order.service.GrabService;
import com.online.taxi.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import sun.nio.ch.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author yueyi2019
 */
@RestController
@RequestMapping("/grab")
//@Scope("prototype")
public class GrabOrderController {

    @Autowired
    // 无锁
    @Qualifier("grabNoLockService")
    // jvm锁
//    @Qualifier("grabJvmLockService")
    // mysql锁
//    @Qualifier("grabMysqlLockService")
    // 单个redis
//    @Qualifier("grabRedisLockService")
//    @Qualifier("grabMyRedisLockService")
    //单个redisson
//    @Qualifier("grabRedisRedissonService")
    // 红锁
//    @Qualifier("grabRedisRedissonRedLockLockService")
    private GrabService grabService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper mapper;
    
    
    @GetMapping("/do/{orderId}")
    public String grab(@PathVariable("orderId") int orderId, int driverId){
//        System.out.println(grabService.toString());
//        System.out.println(grabService.hashCode());
        grabService.grabOrder(orderId,driverId);
        ResponseResult.success(new BaseResponse());
        return "";
    }
//    @GetMapping("/hysmerge/{orderId}")
//    public List<Order> hysmerge(@PathVariable("orderId") int orderId){
////        System.out.println(grabService.toString());
////        System.out.println(grabService.hashCode());
//        //请求合并
//        HystrixRequestContext context = HystrixRequestContext.initializeContext();
//        List<Order> list = new ArrayList<>();
//        Future<Order> o1 = new OrderCollapseCommand(mapper,1).queue();
//        Future<Order> o2 = new OrderCollapseCommand(mapper,2).queue();
//        Future<Order> o3 = new OrderCollapseCommand(mapper,3).queue();
//        Future<Order> o4 = new OrderCollapseCommand(mapper,4).queue();
//
//        try {
//            list.add(o1.get());
//            list.add(o2.get());
//            list.add(o3.get());
//            list.add(o4.get());
//            Thread.sleep(200);
//            Future<Order> o5 = new OrderCollapseCommand(mapper,5).queue();
//            list.add(o5.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        ResponseResult.success(list);
//        context.close();
//        return list;
//    }
    @GetMapping("/hysmerge/{orderId}")
    public Order hysmerge(@PathVariable("orderId") int orderId){
//        System.out.println(grabService.toString());
//        System.out.println(grabService.hashCode());
        //请求合并
        Order order = orderService.orderById(orderId);
        ResponseResult.success(order);
        return order;
    }

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(10,10,10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(16), Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardOldestPolicy());
    }
}
