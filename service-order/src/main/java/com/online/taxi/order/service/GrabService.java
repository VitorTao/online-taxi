package com.online.taxi.order.service;

import com.online.taxi.common.dto.ResponseResult;
import org.springframework.context.annotation.Scope;

/**
 * @author yueyi2019
 */

public interface GrabService {

    /**
     * 司机抢单
     * @param orderId
     * @param driverId
     * @return
     */
    public ResponseResult grabOrder(int orderId , int driverId);
}
