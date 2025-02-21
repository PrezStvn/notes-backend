package com.simplenotes.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class NoteResponse {
    private UUID id;
    private String title;
    private String content;
    private LocalDateTime updatedAt;

    // Constructor
    public NoteResponse(UUID id, String title, String content, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.updatedAt = updatedAt;
    }

    // Getters
    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
} 