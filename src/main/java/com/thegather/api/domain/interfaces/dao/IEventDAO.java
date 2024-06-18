package com.thegather.api.domain.interfaces.dao;

import com.thegather.api.domain.entities.Event;

import java.sql.SQLException;
import java.util.List;

public interface IEventDAO {
    Event createEvent(Event event);
    int updateEvent(Event event) throws SQLException;
    List<Event> getAllEvents();
    Event getById(Long id);

    Boolean delete(Long id);
}
