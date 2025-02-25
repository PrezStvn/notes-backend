package com.simplenotes.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class NoteDTO {
    private UUID id;
    private String title;
    private String content;
    private int[] indentLevels;  // Array for frontend
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public int[] getIndentLevels() { return indentLevels; }
    public void setIndentLevels(int[] indentLevels) { this.indentLevels = indentLevels; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 