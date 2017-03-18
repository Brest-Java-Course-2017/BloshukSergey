package com.cinema.dao;

import com.cinema.model.Customer;
import com.cinema.model.Session;

public interface SessionDao {
    /**
     * @param session new session.
     * @return session's id.
     */
    Integer add(Session session);

    /**
     *
     * @param id session's id.
     * @return quantity of deleted sessions.
     */
    Integer delete(Integer id);

    /**
     *
     * @param session customer for update.
     * @return the number of update rows.
     */
    Integer update(Session session);

    /**
     * @return session by id.
     */
    Customer getById(Integer id);

}
