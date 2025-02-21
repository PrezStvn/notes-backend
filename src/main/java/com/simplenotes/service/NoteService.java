package com.simplenotes.service;

import com.simplenotes.model.Note;
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
    public Note updateNote(UUID id, Note noteDetails) {
        logger.debug("Updating note with ID: {}", id);
        Note note = getNoteById(id);
        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());
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
}
