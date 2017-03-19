package com.cinema.service;

import com.cinema.model.Customer;

import java.util.List;

public interface CustomerService {

    /**
     * @param customer new customer.
     * @return customer's id.
     */
    Integer add(Customer customer);

    /**
     *
     * @param id customer's id.
     * @return quantity of deleted customers.
     */
    Integer delete(Integer id);

    /**
     *
     * @param customer customer for update.
     * @return the number of update rows.
     */
    Integer update(Customer customer);

    /**
     * @return customers by name.
     */
    List<Customer> getByName(String name);

    /**
     * @return customers by id.
     */
    Customer getById(Integer id);

    /**
     * @return all customers.
     */
    List<Customer> getAll();
}
