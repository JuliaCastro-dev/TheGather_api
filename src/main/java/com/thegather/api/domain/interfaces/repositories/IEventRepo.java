package com.thegather.api.domain.interfaces.repositories;

import com.thegather.api.domain.entities.Event;

import java.sql.SQLException;
import java.util.List;
public interface IEventRepo {
    List<Event> getAllEvents();
    Event createEvent(Event event);
    int updateEvent(Event event);
    Event getEventById(long id);
    boolean deleteEventById(long id);
}
