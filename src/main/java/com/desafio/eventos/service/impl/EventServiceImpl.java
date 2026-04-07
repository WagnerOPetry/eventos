package com.desafio.eventos.service.impl;

import com.desafio.eventos.domain.Event;
import com.desafio.eventos.dto.EventRequest;
import com.desafio.eventos.dto.EventResponse;
import com.desafio.eventos.repository.EventRepository;
import com.desafio.eventos.service.EventService;
import com.desafio.eventos.service.mapper.EventMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementação da lógica de negócio para gerenciar eventos.
 * 
 * Responsabilidades:
 * - Validar dados de entrada (através dos DTOs)
 * - Aplicar regras de negócio (ex: soft delete)
 * - Orquestrar chamadas ao repositório
 * - Converter entre Domain (Event) e DTOs (EventRequest/EventResponse)
 */
@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository repository;
    private final EventMapper mapper;

    @Autowired
    public EventServiceImpl(EventRepository repository, EventMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventResponse> listEvents(Pageable pageable) {
        return repository.findAllActive(pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public EventResponse getEvent(Long id) {
        Event event = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
        return mapper.toResponse(event);
    }

    @Override
    public EventResponse createEvent(EventRequest request) {
        Event event = mapper.toEntity(request);
        Event saved = repository.save(event);
        return mapper.toResponse(saved);
    }

    @Override
    public EventResponse updateEvent(Long id, EventRequest request) {
        Event event = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
        mapper.updateEntity(request, event);
        Event updated = repository.save(event);
        return mapper.toResponse(updated);
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
        event.setDeleted(true);
        repository.save(event);
    }
}