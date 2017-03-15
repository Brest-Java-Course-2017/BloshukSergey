package com.cinema.client;

import com.cinema.client.exception.ServerDataAccessException;
import com.cinema.model.Customer;

import java.util.List;

public interface CustomerClient {
    /**
     *
     * @return all customers.
     */
    List<Customer> getAllCustomers() throws ServerDataAccessException;

    /**
     *
     * @return all customers by session id.
     */
    List<Customer> getCustomersBySessionId(Integer sessionId) throws ServerDataAccessException;

    /**
     *
     * @param customerId customer's id.
     * @return customer.
     */
    Customer getCustomerById(Integer customerId) throws ServerDataAccessException;

    /**
     *
     * @param customer new customer.
     * @return customer's id.
     */
    Integer addCustomer(Customer customer) throws ServerDataAccessException;

    /**
     *
     * @param customer customer for update.
     * @return the number of update rows.
     */
    Integer updateCustomer(Customer customer) throws ServerDataAccessException;

    /**
     *
     * @param customerId customer's id.
     * @return quantity of delete customer.
     */
    Integer deleteCustomer(Integer customerId) throws ServerDataAccessException;
}
