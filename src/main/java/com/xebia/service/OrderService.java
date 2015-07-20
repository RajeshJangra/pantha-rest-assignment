/*
 * Copyright 2015 by Xebia IT Architects,
 *  612, 6th Floor, BPTP Park Centra, Sector 30, Gurgaon - 122001, Haryana, India
 *  All rights reserved.
 *
 *  This software is confidential and proprietary information of Xebia IT Architects ("Confidential Information").
 *  You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement
 *  you entered into with Xebia IT Architects.
 */

package com.xebia.service;

import com.xebia.dao.OrderRepository;
import com.xebia.exception.OrderValidationException;
import com.xebia.model.Order;

import java.util.List;

/**
 * Created by rajeshkumar on 17/07/15.
 */
public interface OrderService {
    /**
     * @return
     */
    List<Order> getOrderList();

    /**
     * @param orderId
     * @return
     */
    Order getOrder(String orderId) throws OrderValidationException;

    /**
     * @param order
     */
    void createOrder(Order order) throws OrderValidationException;

    /**
     * @param order
     */
    void updateOrder(Order order) throws OrderValidationException;

    /**
     * @param orderId
     */
    void deleteOrder(String orderId) throws OrderValidationException;

    void setOrderRepository(OrderRepository orderRepository);
}
