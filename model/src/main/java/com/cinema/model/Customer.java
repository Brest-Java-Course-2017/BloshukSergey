package com.cinema.model;

import java.util.Objects;

/**
 * Customer is bean class.
 */

public class Customer {

    private Integer customerId;

    private Integer sessionId;

    private String firstName;

    private String lastName;

    private Integer quantityTickets;

    public Customer() {
    }

    public Customer(Integer sessionId, String firstName, String lastName, Integer quantityTickets) {
        this.sessionId = sessionId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.quantityTickets = quantityTickets;
    }

    public Customer(Integer customerId, Integer sessionId, String firstName, String lastName, Integer quantityTickets) {
        this.customerId = customerId;
        this.sessionId = sessionId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.quantityTickets = quantityTickets;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getQuantityTickets() {
        return quantityTickets;
    }

    public void setQuantityTickets(Integer quantityTickets) {
        this.quantityTickets = quantityTickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId) &&
                Objects.equals(sessionId, customer.sessionId) &&
                Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(quantityTickets, customer.quantityTickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, sessionId, firstName, lastName, quantityTickets);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", sessionId=" + sessionId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", quantityTickets=" + quantityTickets +
                '}';
    }
}
