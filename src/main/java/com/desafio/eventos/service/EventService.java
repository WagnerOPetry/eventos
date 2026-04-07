package com.desafio.eventos.service;

import com.desafio.eventos.dto.EventRequest;
import com.desafio.eventos.dto.EventResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    Page<EventResponse> listEvents(Pageable pageable);

    EventResponse getEvent(Long id);

    EventResponse createEvent(EventRequest request);

    EventResponse updateEvent(Long id, EventRequest request);

    void deleteEvent(Long id);
}
