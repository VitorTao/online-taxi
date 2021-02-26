package com.online.taxi.order;

import com.online.taxi.common.entity.Order;
import com.online.taxi.order.dao.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceOrderApplicationTests {
	@Autowired
	private OrderMapper mapper;
	@Test
	public void contextLoads() {
//		List<Order> orders = mapper.selectByIds(Arrays.asList(1, 2, 3));
//		System.out.println(orders);
		Order order = mapper.selectByPrimaryKey(3);
		System.out.println(order.getStatus());
	}

}
