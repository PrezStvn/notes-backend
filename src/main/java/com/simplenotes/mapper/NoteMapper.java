package com.simplenotes.mapper;

import com.simplenotes.dto.NoteResponse;
import com.simplenotes.model.Note;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoteMapper {
    
    public NoteResponse toResponse(Note note) {
        return new NoteResponse(
            note.getId(),
            note.getTitle(),
            note.getContent(),
            note.getUpdatedAt()
        );
    }

    public List<NoteResponse> toResponseList(List<Note> notes) {
        return notes.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }
} 