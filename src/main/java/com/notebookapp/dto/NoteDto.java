package com.notebookapp.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NoteDto (

    String id,

    @NotNull(message = "Note title cannot be null")
    @NotBlank(message = "Note title cannot be empty")
    String title,

    String content,

    LocalDateTime createdAt,

    LocalDateTime updatedAt
    
) {}
