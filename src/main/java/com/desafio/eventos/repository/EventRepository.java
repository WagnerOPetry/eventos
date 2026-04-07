package com.desafio.eventos.repository;

import com.desafio.eventos.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select e from Event e where e.deleted = false")
    Page<Event> findAllActive(Pageable pageable);

    // Find a non-deleted event by id
    Optional<Event> findByIdAndDeletedFalse(Long id);

}