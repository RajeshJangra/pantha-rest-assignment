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

import com.xebia.dto.CustomerDto;
import com.xebia.exception.CustomerValidationException;
import com.xebia.model.Customer;
import com.xebia.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    List<CustomerDto> getCustomerList(@RequestParam(value = "firstName", required = false) String firstName, @RequestParam(value = "lastName", required = false) String lastName) throws CustomerValidationException {
        return customerService.getCustomerList(firstName, lastName);
    }

    @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    CustomerDto getCustomer(@PathVariable String customerId) throws CustomerValidationException {
        return customerService.getCustomer(customerId);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public
    @ResponseBody
    void createCustomer(@RequestBody Customer customer) throws CustomerValidationException {
        customerService.createCustomer(customer);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    void updateCustomer(@RequestBody Customer customer) throws CustomerValidationException {
        customerService.updateCustomer(customer);
    }

    @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    void deleteCustomer(@PathVariable String customerId) throws CustomerValidationException {
        customerService.deleteCustomer(customerId);
    }

    @ExceptionHandler(CustomerValidationException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
