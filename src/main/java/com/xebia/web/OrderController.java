/*
 * Copyright 2015 by Xebia IT Architects,
 *  612, 6th Floor, BPTP Park Centra, Sector 30, Gurgaon - 122001, Haryana, India
 *  All rights reserved.
 *
 *  This software is confidential and proprietary information of Xebia IT Architects ("Confidential Information").
 *  You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement
 *  you entered into with Xebia IT Architects.
 */

package com.xebia.web;

import com.xebia.exception.OrderValidationException;
import com.xebia.model.Order;
import com.xebia.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

@RestController
public class OrderController {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    List<Order> getOrderList() {
        return orderService.getOrderList();
    }

    @RequestMapping(value = "/orders/{orderId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    Order getOrder(@PathVariable String orderId) throws Exception {
        Order order = orderService.getOrder(orderId);
        if(order == null){
            throw new ValidationException("{} is not valid");
        }
        return order;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public
    @ResponseBody
    void createOrder(@RequestBody Order order) throws OrderValidationException {
        orderService.createOrder(order);
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    void updateOrder(@RequestBody Order order) throws OrderValidationException {
        orderService.updateOrder(order);
    }
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/orders/{orderId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    void deleteOrder(@PathVariable String orderId) throws OrderValidationException {
        orderService.deleteOrder(orderId);
    }

    @ExceptionHandler(OrderValidationException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
