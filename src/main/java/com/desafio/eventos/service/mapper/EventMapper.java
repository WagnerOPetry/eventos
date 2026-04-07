package com.desafio.eventos.service.mapper;

import com.desafio.eventos.domain.Event;
import com.desafio.eventos.dto.EventRequest;
import com.desafio.eventos.dto.EventResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper para converter entre Event (Domain), EventRequest (DTO entrada) e EventResponse (DTO saída).
 * 
 * Responsabilidades:
 * - Converter Domain para DTO Response (para enviar ao frontend)
 * - Converter DTO Request para Domain (para salvar no banco)
 * - Garantir que campos sensíveis não sejam expostos na API (deleted, createdAt, updatedAt)
 */
@Component
public class EventMapper {

    /**
     * Converte um Event (Domain) para EventResponse (DTO).
     * 
     * Apenas os campos públicos são mapeados:
     * - id, title, description, eventDateTime, location
     * 
     * Campos NÃO mapeados (segurança):
     * - deleted (interno do sistema)
     * - createdAt (interno do sistema)
     * - updatedAt (interno do sistema)
     * 
     * @param event Entidade do banco de dados
     * @return DTO para retornar ao frontend
     */
    public EventResponse toResponse(Event event) {
        if (event == null) {
            return null;
        }

        EventResponse response = new EventResponse();
        response.setId(event.getId());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setEventDateTime(event.getEventDateTime());
        response.setLocation(event.getLocation());

        return response;
    }

    /**
     * Converte um EventRequest (DTO entrada) para Event (Domain).
     * 
     * Campos do request que são copiados:
     * - title, description, eventDateTime, location
     * 
     * Campos que NÃO vêm do request (são inicializados pelo sistema):
     * - id (gerado pelo banco)
     * - deleted (sempre começa como false)
     * - createdAt (definido automaticamente)
     * - updatedAt (definido automaticamente)
     * 
     * @param request DTO recebido do frontend
     * @return Entidade pronta para ser salva no banco
     */
    public Event toEntity(EventRequest request) {
        if (request == null) {
            return null;
        }

        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setEventDateTime(request.getEventDateTime());
        event.setLocation(request.getLocation());

        return event;
    }

    /**
     * Atualiza um Event existente com dados de um EventRequest.
     * 
     * Campos que são atualizados:
     * - title, description, eventDateTime, location
     * 
     * Campos que NÃO são alterados (preservados):
     * - id (nunca muda)
     * - deleted (gerenciado separadamente)
     * - createdAt (nunca muda)
     * - updatedAt (gerado automaticamente pelo @PreUpdate)
     * 
     * @param request DTO com dados novos
     * @param event Entidade existente a ser atualizada
     * @return Entidade atualizada
     */
    public Event updateEntity(EventRequest request, Event event) {
        if (request == null || event == null) {
            return event;
        }

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setEventDateTime(request.getEventDateTime());
        event.setLocation(request.getLocation());

        return event;
    }
}
