package com.notebookapp.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record NotebookDto(
        String id,

        @NotBlank(message = "Notebook title cannot be empty") 
        String title,
        
        List<ShortNoteDto> notes,

        LocalDateTime createdAt,

        LocalDateTime updatedAt) {
}
