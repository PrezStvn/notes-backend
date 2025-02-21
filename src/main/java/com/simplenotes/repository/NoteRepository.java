package com.simplenotes.repository;

import com.simplenotes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    // Spring Data JPA will automatically implement basic CRUD operations
    // We can add custom query methods here if needed later

    // Spring will create: SELECT * FROM note ORDER BY updated_at DESC
    List<Note> findAllByOrderByUpdatedAtDesc();

    // Search notes by title or content
    List<Note> findByTitleContainingOrContentContainingOrderByUpdatedAtDesc(String title, String content);
    
    // Find recent notes (could be useful for "Recent Notes" feature)
    List<Note> findTop5ByOrderByUpdatedAtDesc();
}
