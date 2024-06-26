package com.thegather.api.domain.interfaces.dao;

import com.thegather.api.domain.entities.Event;
import java.util.List;

public interface IEventDAO {
    Event createEvent(Event event);
    int updateEvent(Event event);
    List<Event> getAllEvents();
    Event getById(Long id);

    Boolean delete(Long id);
}
