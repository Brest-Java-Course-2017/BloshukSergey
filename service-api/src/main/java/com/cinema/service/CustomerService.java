package com.cinema.service;

import com.cinema.model.Customer;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface CustomerService {

    /**
     * @param customer new customer.
     * @return customer's id.
     */
    Integer add(Customer customer) throws DataAccessException;

    /**
     *
     * @param id customer's id.
     * @return quantity of deleted customers.
     */
    Integer delete(Integer id) throws DataAccessException;

    /**
     *
     * @param customer customer for update.
     * @return the number of update rows.
     */
    Integer update(Customer customer) throws DataAccessException;

    /**
     * @return customers by name.
     */
    List<Customer> getByName(String name) throws DataAccessException;

    /**
     * @return customers by id.
     */
    Customer getById(Integer id) throws DataAccessException;

    /**
     * @return all customers.
     */
    List<Customer> getAll() throws DataAccessException;
}
