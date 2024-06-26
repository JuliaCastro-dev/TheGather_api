package com.thegather.api.application.controllers;

import com.google.gson.Gson;
import com.thegather.api.domain.entities.Event;
import com.thegather.api.domain.interfaces.services.IEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
@Tag(name = "Event", description = "Operations related to events")
public class EventController {

    private final IEventService eventService;
    private final Gson gson = new Gson();

    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Create a new event", description = "Create a new event in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/newEvent")
    public ResponseEntity<String> createEvent(
            @Parameter(description = "Event to be created", required = true, schema = @Schema(implementation = Event.class))
            @Valid @RequestBody Event event,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(" "));
            return ResponseEntity.badRequest().body("Error when trying to create event, invalid event: " + errorMessage);
        }

        Event savedEvent = eventService.createEvent(event);
        return ResponseEntity.ok(gson.toJson(savedEvent));
    }

    @Operation(summary = "Update an existing event", description = "Update event details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/event/{id}")
    public ResponseEntity<String> updateEvent(
            @Parameter(description = "ID of the event to be updated", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated event details", required = true, schema = @Schema(implementation = Event.class))
            @Valid @RequestBody Event event,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(" "));
            return ResponseEntity.badRequest().body("Error when trying to update event: " + errorMessage);
        }

        event.setId(id);
        int updatedEvent = eventService.updateEvent(event);

        if (updatedEvent == 0) {
            return ResponseEntity.notFound().build();
        }

        Event eventUpdated = eventService.getEventById(id);
        return ResponseEntity.ok(gson.toJson(eventUpdated));
    }

    @Operation(summary = "Get an event by ID", description = "Retrieve event details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class))),
            @ApiResponse(responseCode = "404", description = "Event not found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/event/{id}")
    public ResponseEntity<String> getEventById(
            @Parameter(description = "ID of the event to be retrieved", required = true)
            @PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gson.toJson(event));
    }

    @Operation(summary = "Get all events", description = "Retrieve a list of all events")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Event.class)))
    })
    @GetMapping("/events")
    public ResponseEntity<String> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(gson.toJson(events));
    }

    @Operation(summary = "Delete an event", description = "Delete event by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping("/deleteEvent/{id}")
    public ResponseEntity<Boolean> deleteEvent(
            @Parameter(description = "ID of the event to be deleted", required = true)
            @PathVariable Long id) {
        boolean deleted = eventService.deleteEvent(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(true);
    }
}
