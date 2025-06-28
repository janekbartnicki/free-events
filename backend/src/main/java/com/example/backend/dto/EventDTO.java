package com.example.backend.dto;

import com.example.backend.model.Category;
import com.example.backend.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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

    @JsonProperty("event_registered_users")
    private List<User> registeredUsers;

    @JsonProperty("event_categories")
    private List<Category> eventCategories;
}
