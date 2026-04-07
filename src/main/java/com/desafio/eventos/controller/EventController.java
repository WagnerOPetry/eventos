package com.desafio.eventos.controller;

import com.desafio.eventos.dto.EventRequest;
import com.desafio.eventos.dto.EventResponse;
import com.desafio.eventos.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Events", description = "Endpoints para gerenciar eventos")
public class EventController {

    private final EventService service;

    @Autowired
    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar eventos", description = "Retorna uma página de eventos ativos")
    public Page<EventResponse> listEvents(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.listEvents(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter evento por ID", description = "Retorna um evento específico pelo seu ID")
    public EventResponse getEventsById(@PathVariable Long id) {
        return service.getEvent(id);
    }

    @PostMapping
    @Operation(summary = "Criar novo evento", description = "Cria um novo evento na base de dados")
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest request) {
        EventResponse response = service.createEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar evento", description = "Atualiza os dados de um evento existente")
    public EventResponse updateEvent(@PathVariable Long id, @Valid @RequestBody EventRequest request) {
        return service.updateEvent(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar evento", description = "Marca um evento como deletado (soft delete)")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        service.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
