package com.thegather.api.domain.interfaces.services;

import com.thegather.api.domain.entities.Event;

import java.sql.SQLException;
import java.util.List;
public interface IEventService {
    List<Event> getAllEvents();
    Event createEvent(Event event);

    int updateEvent(Event event) throws SQLException;

    Event getEventById(Long id);

    boolean deleteEvent(Long id);
}