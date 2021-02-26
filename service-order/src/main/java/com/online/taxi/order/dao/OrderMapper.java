package com.online.taxi.order.dao;

import org.apache.ibatis.annotations.Mapper;

import com.online.taxi.common.entity.Order;

import java.util.List;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Integer orderid);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer orderid);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> selectByIds(List<Integer> ids);
}