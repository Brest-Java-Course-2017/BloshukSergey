package com.cinema.model;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;


/**
 * Session is bean class.
 */
public class Session {

    private Integer sessionId;

    private String movieName;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate sessionDate;

    public Session() {
    }

    public Session(String movieName, LocalDate sessionDate) {
        this.movieName = movieName;
        this.sessionDate = sessionDate;
    }

    public Session(Integer sessionId, String movieName, LocalDate sessionDate) {
        this.sessionId = sessionId;
        this.movieName = movieName;
        this.sessionDate = sessionDate;
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

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(sessionId, session.sessionId) &&
                Objects.equals(movieName, session.movieName) &&
                Objects.equals(sessionDate, session.sessionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, movieName, sessionDate);
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId=" + sessionId +
                ", movieName='" + movieName + '\'' +
                ", sessionDate=" + sessionDate +
                '}';
    }
}
