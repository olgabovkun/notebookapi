package com.notebookapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notebookapp.dto.NoteDto;
import com.notebookapp.dto.NotebookDto;
import com.notebookapp.exception.NotFoundCustomException;
import com.notebookapp.service.NotebookService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/notebook")
@AllArgsConstructor
public class NotebookController {

    private final NotebookService notebookService;

    @GetMapping
    public List<NotebookDto> getNoteboks() {
        return notebookService.getNoteboks();
    }

    @PostMapping("/create")
    public NotebookDto create(@RequestBody @Valid NotebookDto notebookDto) {
        return notebookService.create(notebookDto);
    }

    @PutMapping("/update")
    public NotebookDto update(@RequestBody @Valid NotebookDto notebookDto) throws NotFoundCustomException {
        return notebookService.update(notebookDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        notebookService.delete(id);
    }

    @GetMapping("/{id}")
    public NotebookDto getNotebookById(@PathVariable("id") String id) throws NotFoundCustomException {
        return notebookService.getNotebookById(id);
    }

    @PostMapping("/{id}/add-note")
    public NoteDto addNote(@PathVariable("id") String id, @RequestBody @Valid NoteDto noteDto)
            throws NotFoundCustomException {
        return notebookService.addNote(id, noteDto);
    }

    @GetMapping("/note/{id}")
    public NoteDto getNoteById(@PathVariable("id") String id) throws NotFoundCustomException {
        return notebookService.getNoteById(id);
    }

}