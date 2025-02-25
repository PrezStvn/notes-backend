package com.simplenotes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplenotes.model.Note;
import com.simplenotes.dto.NoteDTO;
import com.simplenotes.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NoteService {
    private static final Logger logger = LoggerFactory.getLogger(NoteService.class);
    
    private final NoteRepository noteRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Transactional
    public Note createNote(Note note) {
        logger.debug("Creating note with title: {}", note.getTitle());
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        Note savedNote = noteRepository.save(note);
        logger.debug("Note created with ID: {}", savedNote.getId());
        return savedNote;
    }

    public List<Note> getAllNotes() {
        logger.debug("Fetching all notes");
        List<Note> notes = noteRepository.findAllByOrderByUpdatedAtDesc();
        logger.debug("Found {} notes", notes.size());
        return notes;
    }

    public Note getNoteById(UUID id) {
        logger.debug("Fetching note with ID: {}", id);
        return noteRepository.findById(id)
            .orElseThrow(() -> {
                logger.debug("Note not found with ID: {}", id);
                return new RuntimeException("Note not found");
            });
    }

    @Transactional
    public Note updateNote(UUID id, NoteDTO noteDTO) {
        logger.debug("Updating note with ID: {}", id);
        Note note = getNoteById(id);
        note.setTitle(noteDTO.getTitle());
        note.setContent(noteDTO.getContent());
        try {
            note.setIndentLevels(objectMapper.writeValueAsString(noteDTO.getIndentLevels()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing indent levels", e);
        }
        note.setUpdatedAt(LocalDateTime.now());
        Note updatedNote = noteRepository.save(note);
        logger.debug("Note updated successfully");
        return updatedNote;
    }

    @Transactional
    public void deleteNote(UUID id) {
        logger.debug("Deleting note with ID: {}", id);
        Note note = getNoteById(id);
        noteRepository.delete(note);
        logger.debug("Note deleted successfully");
    }

    public Note createNote(NoteDTO noteDTO) {
        Note note = new Note();
        note.setTitle(noteDTO.getTitle());
        note.setContent(noteDTO.getContent());
        try {
            note.setIndentLevels(objectMapper.writeValueAsString(noteDTO.getIndentLevels()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing indent levels", e);
        }
        // ... rest of creation logic
        return note;
    }
    
    public NoteDTO convertToDTO(Note note) {
        NoteDTO dto = new NoteDTO();
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        try {
            dto.setIndentLevels(objectMapper.readValue(note.getIndentLevels(), int[].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error reading indent levels", e);
        }
        // ... rest of conversion logic
        return dto;
    }
}
