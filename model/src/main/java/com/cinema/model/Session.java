package com.cinema.model;

import java.time.LocalDate;
import java.util.Objects;


/**
 * Session is bean class.
 */
public class Session {

    private Integer sessionId;

    private String movieName;

    private LocalDate date;

    public Session() {
    }

    public Session(String movieName, LocalDate date) {
        this.movieName = movieName;
        this.date = date;
    }

    public Session(Integer sessionId, String movieName, LocalDate date) {
        this.sessionId = sessionId;
        this.movieName = movieName;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(sessionId, session.sessionId) &&
                Objects.equals(movieName, session.movieName) &&
                Objects.equals(date, session.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, movieName, date);
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId=" + sessionId +
                ", movieName='" + movieName + '\'' +
                ", date=" + date +
                '}';
    }
}
