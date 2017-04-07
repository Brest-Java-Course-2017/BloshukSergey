package com.cinema.controller.rest;

import com.cinema.aop.annotation.Loggable;
import com.cinema.model.Customer;
import com.cinema.service.CustomerService;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/customer")
public class CustomerController implements InitializingBean {

    @Autowired
    private CustomerService customerService;

    @Loggable
    @ResponseBody
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Customer> getAll() {
        List<Customer> customers = customerService.getAll();

        return customers;
    }

    @Loggable
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Integer add(@RequestBody Customer customer) {
        Integer customerId = customerService.add(customer);

        return customerId;
    }

    @Loggable
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Integer delete(@RequestParam("id") Integer id) {
        Integer quantity = customerService.delete(id);

        return quantity;
    }

    @Loggable
    @ResponseBody
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Integer update(@RequestBody Customer customer) {
        Integer quantity = customerService.update(customer);

        return quantity;
    }

    @Loggable
    @ResponseBody
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public Customer getById(@RequestParam("id") Integer id) {
        Customer customer = customerService.getById(id);

        return customer;
    }

    @Loggable
    @ResponseBody
    @RequestMapping(value = "/getByName", method = RequestMethod.GET)
    public List<Customer> getByName(@RequestParam("name") String name) {
        List<Customer> customers = customerService.getByName(name);

        return customers;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (customerService == null) {
            throw new BeanCreationException("customerService is null");
        }
    }

}