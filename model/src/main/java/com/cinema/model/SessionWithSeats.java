package com.cinema.model;

import java.util.Date;
import java.util.Objects;

public class SessionWithSeats extends Session {

    private Integer seats;

    public SessionWithSeats() {
    }

    public SessionWithSeats(Integer seats) {
        this.seats = seats;
    }

    public SessionWithSeats(String movieName, Date sessionDate, Integer totalSeats, Integer seats) {
        super(movieName, sessionDate, totalSeats);
        this.seats = seats;
    }

    public SessionWithSeats(Integer sessionId, String movieName, Date sessionDate, Integer totalSeats, Integer seats) {
        super(sessionId, movieName, sessionDate, totalSeats);
        this.seats = seats;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SessionWithSeats that = (SessionWithSeats) o;
        return Objects.equals(seats, that.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), seats);
    }

    @Override
    public String toString() {
        return "SessionWithSeats{" +
                "seats=" + seats +
                '}';
    }
}
