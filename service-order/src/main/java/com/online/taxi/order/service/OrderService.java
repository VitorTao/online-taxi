package com.online.taxi.order.service;

import com.online.taxi.common.entity.Order;

import java.util.concurrent.Future;

public interface OrderService {
	
	public boolean grab(int orderId, int driverId);
	public Order orderById(Integer id);
}
