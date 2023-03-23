package com.notebookapp;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.util.Lists;

import com.notebookapp.dto.NoteDto;
import com.notebookapp.dto.NotebookDto;
import com.notebookapp.dto.ShortNoteDto;
import com.notebookapp.model.Note;
import com.notebookapp.model.Notebook;

public class NotebookTestUtils {

    public static NotebookDto createNotebookDtoWithoutNotes() {
        return new NotebookDto("notebook_id", "notebook_title", null, LocalDateTime.now(), LocalDateTime.now());
    }

    public static NotebookDto createNotebookDtoWithoutNotes(String id, String title) {
        return new NotebookDto(id, title, null, LocalDateTime.now(), LocalDateTime.now());
    }

    public static NotebookDto createNotebookDtoWithNotes() {
        return new NotebookDto("id", "title", Lists.newArrayList(
                createShortNoteDto("first_note_id", "first_note_title"),
                createShortNoteDto("second_note_id", "second_note_title")), LocalDateTime.now(), LocalDateTime.now());
    }

    public static List<NotebookDto> createNotebookDtoListWithoutNotes() {
        return Lists.newArrayList(
                createNotebookDtoWithoutNotes("first_notebook_id", "first_notebook_title"),
                createNotebookDtoWithoutNotes("second_notebook_id", "second_notebook_title"),
                createNotebookDtoWithoutNotes("third_notebook_id", "third_notebook_title"),
                createNotebookDtoWithoutNotes("fourth_notebook_id", "fourth_notebook_title"));
    }

    public static Notebook createNotebookWithoutNotes() {
        return new Notebook("notebook_id", "notebook_title", null, LocalDateTime.now(), LocalDateTime.now());
    }

    public static Notebook createNotebookWithNotes() {
        return new Notebook("notebook_id", "notebook_title", Lists.newArrayList(
            createNote("first_note_id", "first_note_title"),
            createNote("second_note_id", "second_note_title")
        ), LocalDateTime.now(), LocalDateTime.now());
    }

    public static ShortNoteDto createShortNoteDto() {
        return new ShortNoteDto("note_id", "note_title");
    }

    public static ShortNoteDto createShortNoteDto(String id, String title) {
        return new ShortNoteDto(id, title);
    }

    public static NoteDto createNoteDto() {
        return new NoteDto("note_id", "note_title", "note_content", LocalDateTime.now(), LocalDateTime.now());
    }

    public static NoteDto createNoteDto(String id, String title) {
        return new NoteDto(id, title, "note_content", LocalDateTime.now(), LocalDateTime.now());
    }

    public static Note createNote() {
        return new Note("note_id", "note_title", "note_content", LocalDateTime.now(), LocalDateTime.now());
    }

    public static Note createNote(String id, String title) {
        return new Note(id, title, "note_content", LocalDateTime.now(), LocalDateTime.now());
    }

}
