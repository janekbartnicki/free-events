package com.example.backend.controller;

import com.example.backend.dto.EventDTO;
import com.example.backend.service.EventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class EventsController {

    public final EventsService eventsService;

    @GetMapping("/all")
    public ResponseEntity<List<EventDTO>> allEvents() {
        return ResponseEntity.ok(eventsService.getAllEvents());
    }

    @GetMapping("/past")
    public ResponseEntity<List<EventDTO>> pastEvents() {
        return ResponseEntity.ok(eventsService.getPastEvents());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<EventDTO>> upcomingEvent() {
        return ResponseEntity.ok(eventsService.getUpcomingEvents());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody EventDTO eventCreateRequest) {
        try {
            return ResponseEntity.ok(eventsService.createEvent(eventCreateRequest));
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
