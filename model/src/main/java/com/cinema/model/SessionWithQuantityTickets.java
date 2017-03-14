package com.cinema.model;

import java.util.Date;
import java.util.Objects;

public class SessionWithQuantityTickets extends Session {

    private Integer quantityTickets;

    public SessionWithQuantityTickets() {

    }

    public SessionWithQuantityTickets(String movieName, Date date, Integer quantityTickets) {
        super(movieName, date);
        this.quantityTickets = quantityTickets;
    }

    public SessionWithQuantityTickets(Integer sessionId, String movieName, Date date, Integer quantityTickets) {
        super(sessionId, movieName, date);
        this.quantityTickets = quantityTickets;
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
        if (!super.equals(o)) return false;
        SessionWithQuantityTickets that = (SessionWithQuantityTickets) o;
        return Objects.equals(quantityTickets, that.quantityTickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), quantityTickets);
    }

    @Override
    public String toString() {
        return "SessionWithQuantityTickets{" +
                "quantityTickets=" + quantityTickets +
                '}';
    }
}
