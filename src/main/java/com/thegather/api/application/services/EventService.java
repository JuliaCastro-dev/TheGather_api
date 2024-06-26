package com.thegather.api.application.services;

import com.thegather.api.domain.entities.Event;
import com.thegather.api.domain.interfaces.repositories.IEventRepo;
import com.thegather.api.domain.interfaces.services.IEventService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class EventService implements IEventService {
    IEventRepo repository;
    public EventService(IEventRepo repository) {
        this.repository = repository;
    }
    @Override
    public Event createEvent(Event event) {
      return repository.createEvent(event);
    }

    @Override
    public int updateEvent(Event event) {
        return repository.updateEvent(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return repository.getAllEvents();
    }

    @Override
    public Event getEventById(Long id) {
      return repository.getEventById(id);
    }

    @Override
    public boolean deleteEvent(Long id) {
        return repository.deleteEventById(id);
    }
}
