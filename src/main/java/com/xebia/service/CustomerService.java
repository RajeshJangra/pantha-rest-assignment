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

import com.xebia.dao.CustomerRepository;
import com.xebia.dto.CustomerDto;
import com.xebia.exception.CustomerValidationException;
import com.xebia.model.Customer;

import java.util.List;

/**
 * Created by rajeshkumar on 17/07/15.
 */
public interface CustomerService {

    /**
     *
     * @param firstName
     * @param lastName
     * @return
     * @throws CustomerValidationException
     */
    List<CustomerDto> getCustomerList(String firstName, String lastName);

    /**
     * @param customerId
     * @return
     */
    CustomerDto getCustomer(String customerId) throws CustomerValidationException;

    /**
     * @param customer
     */
    void createCustomer(Customer customer) throws CustomerValidationException;

    /**
     * @param customer
     */
    void updateCustomer(Customer customer) throws CustomerValidationException;

    /**
     * @param customerId
     */
    void deleteCustomer(String customerId) throws CustomerValidationException;

    void setCustomerRepository(CustomerRepository customerRepository);
}
