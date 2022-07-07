package com.proj2.proj2.controller;

import java.util.List;
import java.util.Optional;

import com.proj2.proj2.request.EventCreateRequest;
import com.proj2.proj2.request.EventUpdateRequest;
import com.proj2.proj2.response.EventResponse;
import com.proj2.proj2.service.EventService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "http://localhost:4200")
public class EventController {
    
    private EventService eventService;

    public EventController(EventService eventService)
    {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventResponse> getAllEvents(@RequestParam Optional<Long> userId)
    {
        return eventService.getAllEvents(userId);
    }

    @GetMapping("/{eventId}")
    public EventResponse getEventById(@PathVariable long eventId)
    {
        return eventService.getEventResponseById(eventId);
    }

    @PostMapping
    public EventResponse addEvent(@RequestBody EventCreateRequest eventCreateRequest)
    {
        return eventService.addEvent(eventCreateRequest);
    }

    @PutMapping("/{eventId}/update/{userId}")
    public EventResponse updateEventById(@PathVariable long eventId, 
        @PathVariable long userId,
        @RequestBody EventUpdateRequest eventUpdateRequest)
    {
        return eventService.updateEventById(eventId, userId, eventUpdateRequest);
    }

    @GetMapping("/findRelatedEvents/{userId}")
    public List<EventResponse> findRelatedEvents(@PathVariable long userId)
    {
        return eventService.findRelatedEvents(userId);
    }

    @DeleteMapping("/{eventId}")
    public void deleteEventById(@PathVariable long eventId)
    {
        eventService.deleteEventById(eventId);
    }

    @PatchMapping("/updatePastEvents")
    public List<EventResponse> updatePastEvents()
    {
        return eventService.updatePastEvents();
    }

    @GetMapping("/orderedByDueAscending")
    public List<EventResponse> getAllUpcomingEvents()
    {
        return eventService.getAllUpcomingEvents();
    }
}
