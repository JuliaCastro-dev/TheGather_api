package com.thegather.api.infrastructure.repositories;

import com.thegather.api.domain.entities.Event;
import com.thegather.api.domain.interfaces.dao.IEventDAO;
import com.thegather.api.domain.interfaces.repositories.IEventRepo;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
@Repository
public class EventRepository implements IEventRepo {

    private final IEventDAO eventDAO;

    public EventRepository(IEventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public Event createEvent(Event event) {
        Event savedEvent = eventDAO.createEvent(event);
        return savedEvent;
    }

    @Override
    public int updateEvent(Event event) throws SQLException {
        return eventDAO.updateEvent(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    @Override
    public Event getEventById(long id) {
        return eventDAO.getById(id);
    }

    @Override
    public boolean deleteEventById(long id) {
        return eventDAO.delete(id);
    }
}
