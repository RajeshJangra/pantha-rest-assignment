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

import com.xebia.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String>, PagingAndSortingRepository<Order, String> {
    /**
     * @param id
     * @return
     */
    Order findById(String id);

    /**
     * @param customerId
     * @return
     */
    List<Order> findByCustomerId(String customerId);
}
