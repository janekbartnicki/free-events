package com.example.backend.dto;

import com.example.backend.model.Event;
import com.example.backend.model.User;

public class EventMapper {

    public static EventDTO toEventDTO(Event event) {
        EventDTO eventDTO = new EventDTO();

        eventDTO.setId(event.getId());
        eventDTO.setName(event.getName());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setLocation(event.getLocation());
        eventDTO.setEventDate(event.getEventDate());
        eventDTO.setOrganizerId(event.getOrganizer().getId());
        eventDTO.setRegisteredUsers(event.getRegisteredUsers().stream().toList());
        eventDTO.setEventCategories(event.getCategories().stream().toList());

        return eventDTO;
    }

    public static Event toEvent(EventDTO eventDTO) {
        Event event = new Event();
        event.setId(eventDTO.getId());
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setLocation(eventDTO.getLocation());
        event.setEventDate(eventDTO.getEventDate());
        event.setRegisteredUsers(eventDTO.getRegisteredUsers());
        event.setCategories(eventDTO.getEventCategories());

        User organizer = new User();
        organizer.setId(eventDTO.getOrganizerId());
        event.setOrganizer(organizer);

        return event;
    }
}
