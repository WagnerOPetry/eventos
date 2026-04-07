package com.desafio.eventos.service;

import com.desafio.eventos.domain.Event;
import com.desafio.eventos.dto.EventRequest;
import com.desafio.eventos.dto.EventResponse;
import com.desafio.eventos.repository.EventRepository;
import com.desafio.eventos.service.impl.EventServiceImpl;
import com.desafio.eventos.service.mapper.EventMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventServiceImplTest {

    private EventRepository repository;
    private EventMapper mapper;
    private EventServiceImpl service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(EventRepository.class);
        mapper = Mockito.mock(EventMapper.class);
        service = new EventServiceImpl(repository, mapper);
    }

    @Test
    void createEvent_shouldSaveAndReturn() {
        EventRequest req = new EventRequest();
        req.setTitle("Title");
        req.setDescription("Desc");
        req.setEventDateTime(LocalDateTime.now().plusDays(1));
        req.setLocation("Loc");

        Event event = new Event();
        event.setTitle(req.getTitle());
        event.setDescription(req.getDescription());
        event.setEventDateTime(req.getEventDateTime());
        event.setLocation(req.getLocation());

        Event saved = new Event();
        saved.setId(1L);
        saved.setTitle(req.getTitle());
        saved.setDescription(req.getDescription());
        saved.setEventDateTime(req.getEventDateTime());
        saved.setLocation(req.getLocation());

        EventResponse response = new EventResponse();
        response.setId(1L);
        response.setTitle("Title");

        when(mapper.toEntity(req)).thenReturn(event);
        when(repository.save(any(Event.class))).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        EventResponse resp = service.createEvent(req);

        assertNotNull(resp);
        assertEquals(1L, resp.getId());
        assertEquals("Title", resp.getTitle());
        verify(mapper, times(1)).toEntity(req);
        verify(repository, times(1)).save(any(Event.class));
        verify(mapper, times(1)).toResponse(saved);
    }

    @Test
    void updateEvent_whenNotFound_shouldThrow() {
        when(repository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());

        EventRequest req = new EventRequest();
        req.setTitle("T");
        req.setEventDateTime(LocalDateTime.now().plusDays(1));

        assertThrows(EntityNotFoundException.class, () -> service.updateEvent(1L, req));
    }

    @Test
    void deleteEvent_shouldMarkDeleted() {
        Event existing = new Event();
        existing.setId(2L);
        existing.setDeleted(false);
        when(repository.findByIdAndDeletedFalse(2L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Event.class))).thenAnswer(i -> i.getArguments()[0]);

        service.deleteEvent(2L);

        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
        verify(repository).save(captor.capture());
        assertTrue(captor.getValue().isDeleted());
    }

    @Test
    void listEvents_shouldDelegateToRepository() {
        when(repository.findAllActive(PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(java.util.List.of()));

        var page = service.listEvents(PageRequest.of(0,10));
        assertNotNull(page);
    }
}