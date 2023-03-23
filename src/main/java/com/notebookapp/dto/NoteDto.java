package com.notebookapp.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record NoteDto(
        String id,

        @NotBlank(message = "Note title cannot be empty") 
        String title,
        
        String content,

        LocalDateTime createdAt,

        LocalDateTime updatedAt) {
}
