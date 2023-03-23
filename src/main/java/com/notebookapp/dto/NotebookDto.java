package com.notebookapp.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record NotebookDto (

    String id,

    @NotNull(message = "Notebook title cannot be null")
    @NotBlank(message = "Notebook title cannot be empty")
    String title,

    List<ShortNoteDto> notes,

    LocalDateTime createdAt,
    
    LocalDateTime updatedAt
    
) {}
