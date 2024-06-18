package com.thegather.api.application.controllers;
import com.google.gson.Gson;
import com.thegather.api.domain.entities.Event;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import com.thegather.api.domain.interfaces.services.IEventService;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class EventController {

    private final IEventService eventService;

    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    Gson gson = new Gson();
    @PostMapping(value = "/newEvent")
    public ResponseEntity<String> createEvent(Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Error when trying to create event, invalid event:  ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(" ");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        Event savedEvent = eventService.createEvent(event);
        String jsonString = gson.toJson(savedEvent);
        return ResponseEntity.ok(jsonString);
    }
    @PutMapping(value = "/event/{id}")
    public ResponseEntity<String> updateEvent(@PathVariable Long id, @Valid @RequestBody Event event, BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Error when trying to update event:  ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append(" ");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        event.setId(id);

        int updatedEvent = eventService.updateEvent(event);

        if (updatedEvent == 0 ) return ResponseEntity.notFound().build();

        Event eventUpdated = eventService.getEventById(id);
        String jsonString = gson.toJson(eventUpdated);
        return ResponseEntity.ok(jsonString);
    }

    @GetMapping(value = "/event/{id}")
    public ResponseEntity<String> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        String jsonString = gson.toJson(event);
        return ResponseEntity.ok(jsonString);
    }
    @GetMapping(value = "/events")
    public ResponseEntity<String> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        String jsonString = gson.toJson(events);
        return ResponseEntity.ok(jsonString);
    }

    @DeleteMapping("/deleteEvent/{id}")
    public ResponseEntity<Boolean> deleteEvent(@PathVariable Long id) {
        boolean ret = eventService.deleteEvent(id);
        if(!ret) {
            return ResponseEntity.badRequest().body(false);
        }
        else {
            return ResponseEntity.ok(true);
        }
    }

}
