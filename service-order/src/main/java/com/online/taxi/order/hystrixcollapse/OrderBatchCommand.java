package com.online.taxi.order.hystrixcollapse;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.online.taxi.common.entity.Order;
import com.online.taxi.order.dao.OrderMapper;
import com.online.taxi.order.service.OrderService;
import static com.netflix.hystrix.HystrixCommandGroupKey.Factory.asKey;
import java.util.List;

public class OrderBatchCommand extends HystrixCommand<List<Order>> {

  private OrderMapper mapper;
  private List<Integer> orderIds;
  public OrderBatchCommand(OrderMapper mapper, List<Integer> orderIds) {
    super(Setter.withGroupKey(asKey("orderBatchCommand")));
    this.orderIds = orderIds;
    this.mapper = mapper;
  }
  /**
   * 调用批量处理的方法
   */
  @Override
  protected List<Order> run() throws Exception {
    List<Order> orders = mapper.selectByIds(orderIds);
    System.out.println("orderIds="+orderIds.toString());
    return orders;
  }
  /**
   * Fallback回调方法，如果没有会报错
   */
  @Override
  protected List<Order> getFallback(){
    System.out.println("OrderBatchCommand的run方法，调用失败");
    return null;
  }
}
