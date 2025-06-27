package com.example.backend.repository;

import com.example.backend.model.Event;
import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Event, Long> {

    List<Event> findByEventDateBefore(LocalDateTime dateTime);

    List<Event> findByEventDateAfter(LocalDateTime dateTime);

    @Query("SELECT u FROM Event e JOIN e.registeredUsers u WHERE e.id = :eventId")
    List<User> findRegisteredUsersByEventId(@Param("eventId") Long eventId);
}
