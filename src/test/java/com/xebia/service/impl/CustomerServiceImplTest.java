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
import com.xebia.dao.CustomerRepository;
import com.xebia.dto.CustomerDto;
import com.xebia.exception.CustomerValidationException;
import com.xebia.model.Customer;
import com.xebia.service.CustomerService;
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
 * CustomerServiceImpl Tester.
 *
 * @author Rajesh Kumar
 * @version 1.0
 * @since <pre>Jul 19, 2015</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class CustomerServiceImplTest {
    @InjectMocks
    @Autowired
    CustomerService customerService;
    CustomerRepository customerRepository;

    @Before
    public void before() throws Exception {
        customerRepository = mock(CustomerRepository.class);
        customerService.setCustomerRepository(customerRepository);
    }

    /**
     * Method: getCustomerList()
     */
    @Test
    public void testCreateCustomer() throws Exception {
        Customer customer = new Customer("DummyFirstName", "DummyLastName");
        customerService.createCustomer(customer);
        verify(customerRepository).insert(customer);
    }

    /**
     * Method: getCustomerList()
     */
    @Test(expected = CustomerValidationException.class)
    public void testCreateCustomerWhenInputEntityIsNullThenAnExceptionIsThrown() throws Exception {
        Customer customer = null;
        customerService.createCustomer(customer);
        verify(customerRepository).insert(customer);
        verifyZeroInteractions(customerRepository);
    }

    /**
     * Method: getCustomerList()
     */
    @Test(expected = CustomerValidationException.class)
    public void testCreateCustomerWhenFirstNameIsNullThenAnExceptionIsThrown() throws Exception {
        Customer customer = new Customer(null, "DummyLastName");
        customerService.createCustomer(customer);
        verifyZeroInteractions(customerRepository);
    }

    /**
     * Method: getCustomerList()
     */
    @Test(expected = CustomerValidationException.class)
    public void testCreateCustomerWhenFirstNameIsEmptyThenAnExceptionIsThrown() throws Exception {
        Customer customer = new Customer("", "DummyLastName");
        customerService.createCustomer(customer);
        verifyZeroInteractions(customerRepository);
    }

    /**
     * Method: getCustomerList()
     */
    @Test(expected = CustomerValidationException.class)
    public void testCreateCustomerWhenLastNameIsNullThenAnExceptionIsThrown() throws Exception {
        Customer customer = new Customer("DummyFirstName", null);
        customerService.createCustomer(customer);
        verifyZeroInteractions(customerRepository);
    }

    /**
     * Method: getCustomerList()
     */
    @Test(expected = CustomerValidationException.class)
    public void testCreateCustomerWhenLastNameIsEmptyThenAnExceptionIsThrown() throws Exception {
        Customer customer = new Customer("DummyFirstName", "");
        customerService.createCustomer(customer);
        verifyZeroInteractions(customerRepository);
    }

    /**
     * Method: getCustomerList()
     */
    @Test(expected = Exception.class)
    public void testCreateCustomerWhenMongoRepositoryThrowsException() throws Exception {
        Exception exception = new Exception("Mongo repository issue");
        Customer customer = null;
        when(customerRepository.insert(customer)).thenThrow(exception);
        customerService.createCustomer(customer);
        verify(customerRepository).insert(customer);
    }

    /**
     * Method: getCustomer(String customerId)
     */
    @Test(expected = CustomerValidationException.class)
    public void testGetCustomerWhenInputCustomerisNullThenThrowsException() throws Exception {
        String customer = null;
        customerService.getCustomer(customer);
        verifyZeroInteractions(customerRepository);
    }

    /**
     * Method: getCustomer(String customerId)
     */
    @Test
    public void testGetCustomer() throws Exception {
        String customer = "dummyCustomer";
        customerService.getCustomer(customer);
        verify(customerRepository).findById(customer);
    }

    /**
     * Method: getCustomerList()
     */
    @Test
    public void testGetCustomerListWhenFirstNameAndLastNameAreNullThenAllCustomersAreRetrieved() throws Exception {
        Customer customer = new Customer("DummyFirstName", "DummyLastName");
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomer(customer);
        customerDto.setOrderList(new ArrayList<>());
        List<CustomerDto> customerListDtoInput = new ArrayList<>();
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        customerListDtoInput.add(customerDto);
        when(customerRepository.findAll()).thenReturn(customerList);
        final List<CustomerDto> customerListDto = customerService.getCustomerList(null, null);
        assertEquals(customerListDtoInput, customerListDto);
        verify(customerRepository).findAll();
        verify(customerRepository, times(0)).findByFirstName(anyString());
        verify(customerRepository, times(0)).findByLastName(anyString());
        verify(customerRepository, times(0)).findByFirstNameAndLastName(anyString(), anyString());
    }

    /**
     * Method: getCustomerList()
     */
    @Test
    public void testGetCustomerListWhenFirstNameIsNotNullAndLastNameIsNullThenAllCustomersWithMatchingFirstNameAreRetrieved() throws Exception {
        Customer customer = new Customer("DummyFirstName", "DummyLastName");
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomer(customer);
        customerDto.setOrderList(new ArrayList<>());
        List<CustomerDto> customerListDtoInput = new ArrayList<>();
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        customerListDtoInput.add(customerDto);
        when(customerRepository.findByFirstName("firstName")).thenReturn(customerList);
        final List<CustomerDto> customerListDto = customerService.getCustomerList("firstName", null);
        assertEquals(customerListDtoInput, customerListDto);
        verify(customerRepository, times(0)).findAll();
        verify(customerRepository).findByFirstName(anyString());
        verify(customerRepository, times(0)).findByLastName(anyString());
        verify(customerRepository, times(0)).findByFirstNameAndLastName(anyString(), anyString());
    }

    /**
     * Method: getCustomerList()
     */
    @Test
    public void testGetCustomerListWhenLastNameIsNotNullAndFirstNameIsNullThenAllCustomersWithMatchingLastNameAreRetrieved() throws Exception {
        Customer customer = new Customer("DummyFirstName", "DummyLastName");
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomer(customer);
        customerDto.setOrderList(new ArrayList<>());
        List<CustomerDto> customerListDtoInput = new ArrayList<>();
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        customerListDtoInput.add(customerDto);
        when(customerRepository.findByLastName("lastName")).thenReturn(customerList);
        final List<CustomerDto> customerListDto = customerService.getCustomerList(null, "lastName");
        assertEquals(customerListDtoInput, customerListDto);
        verify(customerRepository, times(0)).findAll();
        verify(customerRepository, times(0)).findByFirstName(anyString());
        verify(customerRepository).findByLastName(anyString());
        verify(customerRepository, times(0)).findByFirstNameAndLastName(anyString(), anyString());
    }

    /**
     * Method: getCustomerList()
     */
    @Test
    public void testGetCustomerListWhenFirstNameAndLastNameAreNotNullThenAllCustomersWithMatchingFirstNameAndLastNameAreRetrieved() throws Exception {
        Customer customer = new Customer("DummyFirstName", "DummyLastName");
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomer(customer);
        customerDto.setOrderList(new ArrayList<>());
        List<CustomerDto> customerListDtoInput = new ArrayList<>();
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        customerListDtoInput.add(customerDto);
        when(customerRepository.findByFirstNameAndLastName("firstName", "lastName")).thenReturn(customerList);
        final List<CustomerDto> customerListDto = customerService.getCustomerList("firstName", "lastName");
        assertEquals(customerListDtoInput, customerListDto);
        verify(customerRepository, times(0)).findAll();
        verify(customerRepository, times(0)).findByFirstName(anyString());
        verify(customerRepository, times(0)).findByLastName(anyString());
        verify(customerRepository).findByFirstNameAndLastName(anyString(), anyString());
    }

    /**
     * Method: updateCustomer(Customer customer)
     */
    @Test
    public void testUpdateCustomer() throws Exception {
        Customer customer = new Customer("DummyFirstName", "DummyLastName");
        customer.setId("DummyId");
        Customer customerPersisted = new Customer("DummyFirstName", "DummyLastName");
        customerPersisted.setId("DummyId");
        when(customerRepository.findById(anyString())).thenReturn(customerPersisted);
        customerService.updateCustomer(customer);
        verify(customerRepository).save(customerPersisted);
    }

    /**
     * Method: updateCustomer(Customer customer)
     */
    @Test(expected = CustomerValidationException.class)
    public void testUpdateCustomerWhenCustomerDoesNotExistInDBThenExceptionIsThrown() throws Exception {
        Customer customer = new Customer("DummyFirstName", "DummyLastName");
        customer.setId("DummyId");
        when(customerRepository.findById(anyString())).thenReturn(null);
        customerService.updateCustomer(customer);
    }

    /**
     * Method: deleteCustomer(String customerId)
     */
    @Test
    public void testDeleteCustomer() throws Exception {
        customerService.deleteCustomer("DummyId");
        verify(customerRepository).delete("DummyId");
    }

    /**
     * Method: deleteCustomer(String customerId)
     */
    @Test(expected = CustomerValidationException.class)
    public void testDeleteCustomerWhenInputCustomeridIsNullThenThrowException() throws Exception {
        customerService.deleteCustomer(null);
        verifyZeroInteractions(customerRepository);
    }
}
