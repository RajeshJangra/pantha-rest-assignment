/*
 * Copyright 2015 by Xebia IT Architects,
 *  612, 6th Floor, BPTP Park Centra, Sector 30, Gurgaon - 122001, Haryana, India
 *  All rights reserved.
 *
 *  This software is confidential and proprietary information of Xebia IT Architects ("Confidential Information").
 *  You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement
 *  you entered into with Xebia IT Architects.
 */

package com.xebia.service.impl;

import com.xebia.dao.OrderRepository;
import com.xebia.exception.OrderValidationException;
import com.xebia.model.Order;
import com.xebia.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rajeshkumar on 19/07/15.
 */
@Service
public class OrderServiceImpl implements OrderService {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<Order> getOrderList() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(String orderId) throws OrderValidationException {
        validateOrderId(orderId);
        return orderRepository.findById(orderId);
    }

    @Override
    public void createOrder(Order order) throws OrderValidationException {
        validateOrder(order);
        orderRepository.insert(order);
    }

    @Override
    public void updateOrder(Order order) throws OrderValidationException {
        validateOrder(order);
        Order persistedOrder = orderRepository.findById(order.getId());
        if (persistedOrder != null) {
            persistedOrder.setOrder(order);
            orderRepository.save(persistedOrder);
        } else {
            throw new OrderValidationException("Input order" + order + "does not exist");
        }
    }

    @Override
    public void deleteOrder(String orderId) throws OrderValidationException {
        validateOrderId(orderId);
        orderRepository.delete(orderId);
    }

    @Override
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private void validateOrderId(final String orderId) throws OrderValidationException {
        if (orderId == null || orderId.isEmpty()) {
            throw new OrderValidationException("Input order" + orderId + "is not valid");
        }
    }

    private void validateOrder(Order order) throws OrderValidationException {
        if (null == order) {
            throw new OrderValidationException("Input order is null");
        }
        if (order.getName() == null || order.getName().isEmpty()) {
            throw new OrderValidationException("Input order name is null or empty");
        }
        if (order.getDescription() == null || order.getDescription().isEmpty()) {
            throw new OrderValidationException("Input order description is null or empty");
        }
        if (order.getQuantity() < 1) {
            throw new OrderValidationException("Input order quantity must be greater than 0");
        }
        if (order.getCustomerId() == null || order.getCustomerId().isEmpty()) {
            throw new OrderValidationException("Input order custmer id is null or empty");
        }
    }
}
