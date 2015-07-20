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

import com.xebia.dao.CustomerRepository;
import com.xebia.dao.OrderRepository;
import com.xebia.dto.CustomerDto;
import com.xebia.exception.CustomerValidationException;
import com.xebia.model.Customer;
import com.xebia.model.Order;
import com.xebia.service.CustomerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajeshkumar on 17/07/15.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    final Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<CustomerDto> getCustomerList(String firstName, String lastName) {
        List<Customer> customerList = null;
        if ((firstName == null || firstName.isEmpty()) && (lastName == null || lastName.isEmpty())) {
            customerList = customerRepository.findAll();
        } else if (firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty()) {
            customerList = customerRepository.findByFirstNameAndLastName(firstName, lastName);
        } else if (firstName == null || firstName.isEmpty()) {
            customerList = customerRepository.findByLastName(lastName);
        } else if (lastName == null || lastName.isEmpty()) {
            customerList = customerRepository.findByFirstName(firstName);
        }
        return CollectionUtils.isEmpty(customerList) ? null : getCustomerDtos(customerList);
    }

    @Override
    public CustomerDto getCustomer(String customerId) throws CustomerValidationException {
        validateCustomerId(customerId);
        Customer customer = customerRepository.findById(customerId);
        return customer == null ? null : getCustomerDto(customer);
    }

    @Override
    public void createCustomer(Customer customer) throws CustomerValidationException {
        validateCustomer(customer);
        customerRepository.insert(customer);
    }

    @Override
    public void updateCustomer(Customer customer) throws CustomerValidationException {
        validateCustomer(customer);
        Customer persistedCustomer = customerRepository.findById(customer.getId());
        if (persistedCustomer != null) {
            persistedCustomer.setCustomer(customer);
            customerRepository.save(persistedCustomer);
        } else {
            throw new CustomerValidationException("Input customer" + customer + "does not exist");
        }
    }

    @Override
    public void deleteCustomer(String customerId) throws CustomerValidationException {
        validateCustomerId(customerId);
        customerRepository.delete(customerId);
        logger.debug("Customer Id :" + customerId + "id deleted");
    }

    private void validateCustomerId(final String customerId) throws CustomerValidationException {
        if (customerId == null || customerId.isEmpty()) {
            throw new CustomerValidationException("Input customer" + customerId + "is not valid");
        }
    }

    private void validateCustomer(Customer customer) throws CustomerValidationException {
        if (null == customer) {
            throw new CustomerValidationException("Input customer is null");
        }
        if (customer.getFirstName() == null || customer.getFirstName().isEmpty()) {
            throw new CustomerValidationException("Input customer first name is null or Empty");
        }
        if (customer.getLastName() == null || customer.getLastName().isEmpty()) {
            throw new CustomerValidationException("Input customer last name is null or Empty");
        }
    }

    private List<CustomerDto> getCustomerDtos(List<Customer> customerList) {
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer customer : customerList) {
            CustomerDto customerDto = getCustomerDto(customer);
            customerDtoList.add(customerDto);
        }
        return customerDtoList;
    }

    private CustomerDto getCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomer(customer);
        List<Order> orderList = orderRepository.findByCustomerId(customer.getId());
        customerDto.setOrderList(orderList);
        return customerDto;
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
}
