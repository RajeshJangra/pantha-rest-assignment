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

import com.xebia.app.Application;
import com.xebia.dao.OrderRepository;
import com.xebia.exception.OrderValidationException;
import com.xebia.model.Order;
import com.xebia.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * OrderServiceImpl Tester.
 *
 * @author Rajesh Kumar
 * @version 1.0
 * @since <pre>Jul 20, 2015</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class OrderServiceImplTest {

    @InjectMocks
    @Autowired
    OrderService orderService;
    OrderRepository orderRepository;

    @Before
    public void before() throws Exception {
        orderRepository = mock(OrderRepository.class);
        orderService.setOrderRepository(orderRepository);
    }

    /**
     * Method: getOrderList()
     */
    @Test
    public void testGetOrderList() throws Exception {
        Order order = new Order("DummyCustomerId", "DummyName", 1, "DummyDescription");
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderRepository.findAll()).thenReturn(orderList);
        final List<Order> orders = orderService.getOrderList();
        assertEquals(orders, orderList);
        verify(orderRepository).findAll();
    }

    /**
     * Method: getOrder(String orderId)
     */
    @Test
    public void testGetOrder() throws Exception {
        Order order = new Order("DummyCustomerId", "DummyName", 1, "DummyDescription");
        order.setId("DummyOrderId");
        when(orderRepository.findById("DummyOrderId")).thenReturn(order);
        Order orderPersisted = orderService.getOrder("DummyOrderId");
        assertEquals(order, orderPersisted);
        verify(orderRepository).findById("DummyOrderId");
    }

    /**
     * Method: getOrder(String orderId)
     */
    @Test(expected = OrderValidationException.class)
    public void testGetOrderWhenOrderidIsNullThenThrowException() throws Exception {
        orderService.getOrder(null);
        verifyZeroInteractions(orderRepository);
    }

    /**
     * Method: getOrder(String orderId)
     */
    @Test(expected = OrderValidationException.class)
    public void testGetOrderWhenOrderidIsEmptyThenThrowException() throws Exception {
        orderService.getOrder("");
        verifyZeroInteractions(orderRepository);
    }

    /**
     * Method: createOrder(Order order)
     */
    @Test
    public void testCreateOrder() throws Exception {
        Order order = new Order("DummyCustomerId", "DummyName", 1, "DummyDescription");
        orderService.createOrder(order);
        verify(orderRepository).insert(order);
    }

    /**
     * Method: createOrder(Order order)
     */
    @Test(expected = OrderValidationException.class)
    public void testCreateOrderWhenInputOrderIsNullThenthrowsException() throws Exception {
        orderService.createOrder(null);
        verifyZeroInteractions(orderRepository);
    }

    /**
     * Method: createOrder(Order order)
     */
    @Test(expected = OrderValidationException.class)
    public void testCreateOrderWhenCustomerIdIsNullThenThrowsException() throws Exception {
        Order order = new Order(null, "DummyName", 1, "DummyDescription");
        orderService.createOrder(order);
        verifyZeroInteractions(orderRepository);
    }

    /**
     * Method: createOrder(Order order)
     */
    @Test(expected = OrderValidationException.class)
    public void testCreateOrderWhenCustomerIdIsEmptyThenThrowsException() throws Exception {
        Order order = new Order("", "DummyName", 1, "DummyDescription");
        orderService.createOrder(order);
        verifyZeroInteractions(orderRepository);
    }

    /**
     * Method: createOrder(Order order)
     */
    @Test(expected = OrderValidationException.class)
    public void testCreateOrderWhenNameIsNullThenThrowsException() throws Exception {
        Order order = new Order("DummyCustomerId", null, 1, "DummyDescription");
        orderService.createOrder(order);
        verifyZeroInteractions(orderRepository);
    }

    /**
     * Method: createOrder(Order order)
     */
    @Test(expected = OrderValidationException.class)
    public void testCreateOrderWhenNameIsEmptyThenThrowsException() throws Exception {
        Order order = new Order("DummyCustomerId", "", 1, "DummyDescription");
        orderService.createOrder(order);
        verifyZeroInteractions(orderRepository);
    }

    /**
     * Method: createOrder(Order order)
     */
    @Test(expected = OrderValidationException.class)
    public void testCreateOrderWhenDescriptionIsNullThenThrowsException() throws Exception {
        Order order = new Order("DummyCustomerId", "DummyName", 1, null);
        orderService.createOrder(order);
        verifyZeroInteractions(orderRepository);
    }

    /**
     * Method: createOrder(Order order)
     */
    @Test(expected = OrderValidationException.class)
    public void testCreateOrderWhenDescriptionIsEmptyThenThrowsException() throws Exception {
        Order order = new Order("DummyCustomerId", "DummyName", 1, "");
        orderService.createOrder(order);
        verifyZeroInteractions(orderRepository);
    }

    /**
     * Method: createOrder(Order order)
     */
    @Test(expected = OrderValidationException.class)
    public void testCreateOrderWhenQuantityIsZeroThenThrowsException() throws Exception {
        Order order = new Order("DummyCustomerId", "DummyName", 0, "DummyDescription");
        orderService.createOrder(order);
        verifyZeroInteractions(orderRepository);
    }

    /**
     * Method: createOrder(Order order)
     */
    @Test(expected = OrderValidationException.class)
    public void testCreateOrderWhenQuantityIsNegativeThenThrowsException() throws Exception {
        Order order = new Order("DummyCustomerId", "DummyName", -1, "DummyDescription");
        orderService.createOrder(order);
        verifyZeroInteractions(orderRepository);
    }

    /**
     * Method: updateOrder(Order order)
     */
    @Test
    public void testUpdateOrder() throws Exception {
        Order order = new Order("DummyCustomerId", "DummyName", 1, "DummyDescription");
        order.setId("Dummyid");
        when(orderRepository.findById(order.getId())).thenReturn(order);
        orderService.updateOrder(order);
        verify(orderRepository).findById(order.getId());
        verify(orderRepository).save(order);
    }

    /**
     * Method: updateOrder(Order order)
     */
    @Test(expected = OrderValidationException.class)
    public void testUpdateOrderWhenInputOrderIsNullThenThrowsException() throws Exception {
        orderService.updateOrder(null);
        verifyZeroInteractions(orderRepository);
    }

    /**
     * Method: updateOrder(Order order)
     */
    @Test(expected = OrderValidationException.class)
    public void testUpdateOrderWhenOrderDoesnotExistInMongoThenThrowsException() throws Exception {
        Order order = new Order("DummyCustomerId", "DummyName", 1, "DummyDescription");
        order.setId("Dummyid");
        when(orderRepository.findById(order.getId())).thenReturn(null);
        orderService.updateOrder(order);
        verify(orderRepository).findById(order.getId());
        verify(orderRepository, times(0)).save(order);
    }

    /**
     * Method: deleteOrder(String orderId)
     */
    @Test
    public void testDeleteOrder() throws Exception {
        orderService.deleteOrder("DummyId");
        verify(orderRepository, times(1)).delete("DummyId");
    }

    /**
     * Method: deleteOrder(String orderId)
     */
    @Test(expected = OrderValidationException.class)
    public void testDeleteOrderWhenOrderIdIsNullThenthrowsException() throws Exception {
        orderService.deleteOrder(null);
        verifyZeroInteractions(orderRepository);
    }

    /**
     * Method: deleteOrder(String orderId)
     */
    @Test(expected = OrderValidationException.class)
    public void testDeleteOrderWhenOrderIdIsEmptyThenthrowsException() throws Exception {
        orderService.deleteOrder("");
        verifyZeroInteractions(orderRepository);
    }
}
