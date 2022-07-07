package com.proj2.proj2.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.proj2.proj2.model.Event;
import com.proj2.proj2.model.User;
import com.proj2.proj2.repository.EventRepository;
import com.proj2.proj2.request.EventCreateRequest;
import com.proj2.proj2.request.EventUpdateRequest;
import com.proj2.proj2.response.EventResponse;
import com.proj2.proj2.response.MailResponse;

import org.springframework.stereotype.Service;

@Service
public class EventService {

    private EventRepository eventRepository;
    private UserService userService;
    private MailService mailService;

    public EventService(EventRepository eventRepository, UserService userService, MailService mailService)
    {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    public List<EventResponse> getAllEvents(Optional<Long> userId) {

        if (userId.isPresent())
            return toResponse(eventRepository.findAllByAdminId(userId.get()));
        else
            return toResponse(eventRepository.findAll());
    }

    public EventResponse getEventResponseById(long eventId)
    {
        return toResponse(eventRepository.findById(eventId).orElse(null));
    }

    public Event getEventById(long eventId)
    {
        return eventRepository.findById(eventId).orElse(null);
    }

    public List<EventResponse> getEventsByUserId(long userId)
    {
        return toResponse(eventRepository.findAllByAdminId(userId));
    }

    public List<EventResponse> findRelatedEvents(long userId)
    {
        List<Event> result = eventRepository.findAllByAdminIdOrderByDueAsc(userId);
        List<MailResponse> mails = mailService.getAllRelatedMails(userId);

        for (MailResponse m : mails)
        {
            if (userId == m.getInvitedUserId())
            {
                Event e = eventRepository.findById(m.getEventId()).orElse(null);
                if (e != null)
                {
                    boolean added = false;
                    for (int i = 0; i < result.size() && !added; i++)
                    {
                        if (e.getDue().isBefore(result.get(i).getDue()))
                        {
                            result.add(i, e);
                            added = true;
                        }
                    }
                    if (!added)
                        result.add(e);
                }
            }
        }

        return toResponse(result);
    }

    public EventResponse addEvent(EventCreateRequest eventCreateRequest) 
    {
        User user = userService.getUserById(eventCreateRequest.getAdminid());
        
        if (user == null)
        {
            return null;
        }
        
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime due = LocalDateTime.of(
            eventCreateRequest.getDueYear(),
            eventCreateRequest.getDueMonth(),
            eventCreateRequest.getDueDay(),
            eventCreateRequest.getDueHour(),
            eventCreateRequest.getDueMin(),
            0);
        if (timeNow.isAfter(due))
        {
            return null;
        }

        Event eventToAdd = new Event();
        eventToAdd.setAdmin(user);
        eventToAdd.setDue(due);
        eventToAdd.setSent(timeNow);
        eventToAdd.setTitle(eventCreateRequest.getTitle());
        eventToAdd.setText(eventCreateRequest.getText());
        eventToAdd.setMonthly(eventCreateRequest.isMonthly());
        

        return toResponse(eventRepository.save(eventToAdd));
        
    }

    public EventResponse updateEventById(long eventId, long userId, EventUpdateRequest eventUpdateRequest)
    {
        Optional<Event> eventToFind = eventRepository.findById(eventId);
        
        if (!eventToFind.isPresent() || userId != eventToFind.get().getAdmin().getId())
        {
            return null;
        }

        Event eventToUpdate = eventToFind.get();
        eventToUpdate.setDue(LocalDateTime
            .of(eventUpdateRequest.getDueYear(),
                eventUpdateRequest.getDueMonth(),
                eventUpdateRequest.getDueDay(),
                eventUpdateRequest.getDueHour(),
                eventUpdateRequest.getDueMin(),
                0));
        eventToUpdate.setText(eventUpdateRequest.getText());
        eventToUpdate.setTitle(eventUpdateRequest.getTitle());

        return toResponse(eventRepository.save(eventToUpdate));
        
    }

    public void deleteEventById(long eventId) {
        
        Event event = eventRepository.findById(eventId).orElse(null);

        if (event == null)
        {
            return;
        }

        eventRepository.deleteById(eventId);
    }

    public List<EventResponse> updatePastEvents()
    {
        List<Event> pastEvents = eventRepository.findAllByDueBefore(LocalDateTime.now());

        if (pastEvents.isEmpty()) return toResponse(pastEvents);

        for (Event e : pastEvents)
        {
            if (e.isMonthly())
            {
                e.setDue(e.getDue().plusMonths(1));
                eventRepository.save(e);
            }
            else
            {
                eventRepository.delete(e);
            }
        }
        return toResponse(eventRepository.findAll());
    }

    public List<EventResponse> getAllUpcomingEvents()
    {
        return toResponse(eventRepository.findAllByOrderByDueAsc());
    }

    private EventResponse toResponse(Event event)
    {
        return new EventResponse(
            event.getId(), 
            event.getTitle(), 
            event.getSent(), 
            event.getDue(), 
            event.getText(), 
            event.getAdmin().getId(),
            event.getAdmin().getUsername(),
            event.isMonthly());
    }

    private List<EventResponse> toResponse(List<Event> events)
    {
        List<EventResponse> result = new ArrayList<>();
        for (Event e : events)
            result.add(toResponse(e));
        return result;
    }

}
