package com.cinema.model;

import java.util.Date;
import java.util.Objects;

public class Session {

    private Integer sessionId;

    private String movieName;

    private Date sessionDate;

    private Integer totalSeats;

    public Session() {
    }

    public Session(String movieName, Date sessionDate, Integer totalSeats) {
        this.movieName = movieName;
        this.sessionDate = sessionDate;
        this.totalSeats = totalSeats;
    }

    public Session(Integer sessionId, String movieName, Date sessionDate, Integer totalSeats) {
        this.sessionId = sessionId;
        this.movieName = movieName;
        this.sessionDate = sessionDate;
        this.totalSeats = totalSeats;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(sessionId, session.sessionId) &&
                Objects.equals(movieName, session.movieName) &&
                Objects.equals(sessionDate, session.sessionDate) &&
                Objects.equals(totalSeats, session.totalSeats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, movieName, sessionDate, totalSeats);
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId=" + sessionId +
                ", movieName='" + movieName + '\'' +
                ", sessionDate=" + sessionDate +
                ", totalSeats=" + totalSeats +
                '}';
    }
}
