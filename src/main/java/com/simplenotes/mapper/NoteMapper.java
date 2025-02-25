package com.simplenotes.mapper;

import com.simplenotes.dto.NoteDTO;
import com.simplenotes.model.Note;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoteMapper {
    private static final Logger logger = LoggerFactory.getLogger(NoteMapper.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public NoteDTO toDTO(Note note) {
        NoteDTO dto = new NoteDTO();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        dto.setCreatedAt(note.getCreatedAt());
        dto.setUpdatedAt(note.getUpdatedAt());
        
        try {
            if (note.getIndentLevels() != null) {
                dto.setIndentLevels(objectMapper.readValue(note.getIndentLevels(), int[].class));
            }
        } catch (JsonProcessingException e) {
            logger.error("Error converting indent levels to array", e);
            dto.setIndentLevels(new int[0]);
        }
        
        return dto;
    }

    public List<NoteDTO> toDTOList(List<Note> notes) {
        return notes.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
} 