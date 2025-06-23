package com.example.backend.repository;

import com.example.backend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Event, Long> {

    List<Event> findByEventDateBefore(LocalDateTime dateTime);

    List<Event> findByEventDateAfter(LocalDateTime dateTime);
}
