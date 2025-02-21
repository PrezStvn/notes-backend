package com.simplenotes.controller;

import com.simplenotes.model.Note;
import com.simplenotes.service.NoteService;
import com.simplenotes.dto.NoteResponse;
import com.simplenotes.dto.NoteCreateRequest;
import com.simplenotes.dto.NoteUpdateRequest;
import com.simplenotes.mapper.NoteMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*") // For development - adjust for production
public class NoteController {
    private final NoteService noteService;
    private final NoteMapper noteMapper;

    public NoteController(NoteService noteService, NoteMapper noteMapper) {
        this.noteService = noteService;
        this.noteMapper = noteMapper;
    }

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@RequestBody NoteCreateRequest request) {
        Note note = new Note(request.getTitle(), request.getContent());
        Note savedNote = noteService.createNote(note);
        return ResponseEntity.ok(noteMapper.toResponse(savedNote));
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        List<Note> notes = noteService.getAllNotes();
        return ResponseEntity.ok(noteMapper.toResponseList(notes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable UUID id) {
        Note note = noteService.getNoteById(id);
        return ResponseEntity.ok(note);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @PathVariable UUID id,
            @RequestBody NoteUpdateRequest request) {
        Note note = noteService.getNoteById(id);
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        Note updatedNote = noteService.updateNote(id, note);
        return ResponseEntity.ok(noteMapper.toResponse(updatedNote));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable UUID id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok().build();
    }
}
