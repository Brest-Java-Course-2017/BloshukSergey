package com.cinema.model;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;


/**
 * Session is bean class.
 */
public class Session {

    private Integer sessionId;

    private String movieName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sessionDate;

    public Session() {
    }

    public Session(String movieName, Date sessionDate) {
        this.movieName = movieName;
        this.sessionDate = sessionDate;
    }

    public Session(Integer sessionId, String movieName, Date sessionDate) {
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

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
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
