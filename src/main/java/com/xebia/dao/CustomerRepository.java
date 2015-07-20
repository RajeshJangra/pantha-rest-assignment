/*
 * Copyright 2015 by Xebia IT Architects,
 *  612, 6th Floor, BPTP Park Centra, Sector 30, Gurgaon - 122001, Haryana, India
 *  All rights reserved.
 *
 *  This software is confidential and proprietary information of Xebia IT Architects ("Confidential Information").
 *  You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the license agreement
 *  you entered into with Xebia IT Architects.
 */

package com.xebia.dao;

import com.xebia.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String>, PagingAndSortingRepository<Customer, String> {
    /**
     * @param id
     * @return
     */
    Customer findById(String id);

    /**
     * @param firstName
     * @return
     */
    List<Customer> findByFirstName(String firstName);

    /**
     * @param lastName
     * @return
     */
    List<Customer> findByLastName(String lastName);

    /**
     * @param lastName
     * @return
     */
    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
