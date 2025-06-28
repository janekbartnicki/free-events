package com.example.backend.service;

import com.example.backend.dto.EventDTO;
import com.example.backend.dto.EventMapper;
import com.example.backend.model.Event;
import com.example.backend.model.User;
import com.example.backend.repository.EventsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventsService {

    private final EventsRepository eventsRepository;
    private final UsersService usersService;

    public List<EventDTO> getAllEvents() {
        return eventsRepository.findAll().stream().map(EventMapper::toEventDTO).toList();
    }

    public List<EventDTO> getPastEvents() {
         return eventsRepository.findByEventDateBefore(LocalDateTime.now()).stream().map(EventMapper::toEventDTO).toList();
    }

    public List<EventDTO> getUpcomingEvents() {
        return eventsRepository.findByEventDateAfter(LocalDateTime.now()).stream().map(EventMapper::toEventDTO).toList();
    }

    public EventDTO createEvent(EventDTO eventCreateRequest) throws IllegalArgumentException {
        if(eventCreateRequest.getEventDate() == null) {
            throw new IllegalArgumentException("Event date is empty");
        }

        if(eventCreateRequest.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Description is empty");
        }

        if(eventCreateRequest.getLocation().isEmpty()) {
            throw new IllegalArgumentException("Location is empty");
        }

        if(eventCreateRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Event name is empty");
        }

        User foundUserById = usersService.findUserById(eventCreateRequest.getOrganizerId());

        Event event = EventMapper.toEvent(eventCreateRequest);
        event.setOrganizer(foundUserById);
        Event createdEvent = eventsRepository.save(event);
        return EventMapper.toEventDTO(createdEvent);
    }

    @Transactional
    public void registerUserToEvent(Long eventId, Long userId) throws IllegalArgumentException {
        Event event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        User user = usersService.findUserById(userId);

        if (event.getRegisteredUsers().contains(user)) {
            throw new IllegalArgumentException("User already registered for this event");
        }

        if (event.getEventDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot register for past event");
        }

        event.getRegisteredUsers().add(user);
        eventsRepository.save(event);
    }

    public List<User> getRegisteredUsers(Long eventId) throws IllegalArgumentException {
        if (!eventsRepository.existsById(eventId)) {
            throw new IllegalArgumentException("Event not found");
        }

        return eventsRepository.findRegisteredUsersByEventId(eventId);
    }

    public void deleteEvent(Long eventId) {
        Event event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + eventId));
        eventsRepository.delete(event);
    }

}
