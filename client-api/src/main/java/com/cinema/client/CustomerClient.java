package com.cinema.client;

import com.cinema.client.exception.ServerDataAccessException;
import com.cinema.model.Customer;

import java.util.List;

public interface CustomerClient {

    /**
     * @param customer new customer.
     * @return customer's id.
     */
    Integer add(Customer customer) throws ServerDataAccessException;

    /**
     *
     * @param id customer's id.
     * @return quantity of deleted customers.
     */
    Integer delete(Integer id) throws ServerDataAccessException;

    /**
     *
     * @param customer customer for update.
     * @return the number of update rows.
     */
    Integer update(Customer customer) throws ServerDataAccessException;

    /**
     * @return customers by name.
     */
    List<Customer> getByName(String name) throws ServerDataAccessException;

    /**
     * @return customers by id.
     */
    Customer getById(Integer id) throws ServerDataAccessException;

    /**
     * @return all customers.
     */
    List<Customer> getAll() throws ServerDataAccessException;
}
