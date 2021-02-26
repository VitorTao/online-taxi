package com.online.taxi.order.hystrixcollapse;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import com.online.taxi.common.entity.Order;
import com.online.taxi.order.dao.OrderMapper;
import com.online.taxi.order.service.OrderService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import static com.netflix.hystrix.HystrixCollapserKey.Factory.asKey;
public class OrderCollapseCommand extends HystrixCollapser<List<Order>,Order,Integer> {

  private Integer orderId;
  private OrderMapper mapper;
  /**
   * 构造方法，主要是用来设置合并器的时间，多长时间合并一次请求
   */
  public OrderCollapseCommand(OrderMapper mapper,Integer orderId){
    super(Setter.withCollapserKey(asKey("orderCollapseCommand")).andCollapserPropertiesDefaults(HystrixCollapserProperties.defaultSetter().withTimerDelayInMilliseconds(100)));
    this.orderId = orderId;
    this.mapper = mapper;
  }
  @Override
  public Integer getRequestArgument() {
    return orderId;
  }
//获取传入的业务参数集合，调用执行方法执行批量请求
  @Override
  protected HystrixCommand<List<Order>> createCommand(Collection<CollapsedRequest<Order, Integer>> collection) {
    System.out.println("HystrixCommandHystrixCommand========>");
    //按请求数声名UserId的集合
    List<Integer> orderIds = new ArrayList<>(collection.size());
//通过请求将100毫秒中的请求参数取出来装进集合中
    orderIds.addAll(collection.stream().map(req -> req.getArgument()).collect(Collectors.toList()));
    //返回UserBatchCommand对象，自动执行UserBatchCommand的run方法
    return new OrderBatchCommand(mapper, orderIds);
  }
  /**
   * 返回请求的执行结果，并根据对应的request将结果返回到对应的request相应体中
   * 通过循环所有被合并的请求依次从批处理的结果集中获取对应的结果
   */
  @Override
  protected void mapResponseToRequests(List<Order> orders, Collection<CollapsedRequest<Order, Integer>> collection) {
    int i=0;
    for (CollapsedRequest<Order, Integer> req:collection){
      req.setResponse(orders.get(i++));
    }
  }
}
