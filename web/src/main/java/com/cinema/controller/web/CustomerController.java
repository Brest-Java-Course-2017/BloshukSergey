package com.cinema.controller.web;

import com.cinema.client.CustomerClient;
import com.cinema.model.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController implements InitializingBean {

    private static final Logger LOGGER = LogManager.getLogger(SessionController.class);

    public static final String REDIRECT_CUSTOMER = "redirect:/customer";
    public static final String CUSTOMER_VIEW_PAGE = "customerViewPage";
    public static final String SEARCH_CUSTOMERS = "searchCustomers";
    public static final String CUSTOMERS = "customers";

    @Autowired
    private CustomerClient customerClient;

    @RequestMapping
    public String getCustomers(Model model) {
        LOGGER.debug("web: getCustomers()");

        List<Customer> customers = customerClient.getAll();
        model.addAttribute("customerList", customers);

        return CUSTOMERS;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchCustomers(@RequestParam("name") String name, @RequestParam("sessionId") Integer id, Model model) {
        LOGGER.debug("web: searchCustomer({}, {})", name, id);

        List<Customer> customers = customerClient.getByName(name + "%");
        model.addAttribute("customerList", customers);
        model.addAttribute("sessionId", id);

        return SEARCH_CUSTOMERS;
    }

    @RequestMapping("/customerView")
    public String customerView(@RequestParam(value = "id", required = false) Integer id, Model model) {
        LOGGER.debug("web: customerView({})", id);

        Customer customer = null;

        if(id == null) { customer = new Customer(id, ""); }
        else { customer = customerClient.getById(id); }
        model.addAttribute("item", customer);

        return CUSTOMER_VIEW_PAGE;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String delete(@RequestParam("id") Integer id) {
        LOGGER.debug("web: delete({})", id);

        customerClient.delete(id);

        return REDIRECT_CUSTOMER;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody Customer customer, Model model) {
        LOGGER.debug("web: add({})", customer);

        customerClient.add(customer);

        return REDIRECT_CUSTOMER;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String update(@RequestBody Customer customer, Model model) {
        LOGGER.debug("web: update({})", customer);

        customerClient.update(customer);

        return REDIRECT_CUSTOMER;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (customerClient == null) {
            throw new BeanCreationException("customerClient is null");
        }
    }
}
