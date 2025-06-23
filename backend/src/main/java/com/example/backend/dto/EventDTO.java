package com.example.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {

    private Long id;

    private String name;

    private String description;

    private String location;

    @JsonProperty("event_date")
    private LocalDateTime eventDate;

    @JsonProperty("organizer_id")
    private Long organizerId;
}
