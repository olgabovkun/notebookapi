package com.notebookapp.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.notebookapp.dto.NoteDto;
import com.notebookapp.dto.NotebookDto;
import com.notebookapp.dto.ShortNoteDto;
import com.notebookapp.model.Note;
import com.notebookapp.model.Notebook;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotebookMapper {

    NotebookMapper MAPPER = Mappers.getMapper(NotebookMapper.class);

    NotebookDto convert(Notebook source);

    Notebook convert(NotebookDto source);

    NoteDto convert(Note source);

    NoteDto convert(ShortNoteDto source);

    Note convertFromShort(ShortNoteDto source);

    Note convert(NoteDto source);

    ShortNoteDto convertToShortNote(Note source);

    ShortNoteDto convertToShortNote(NoteDto source);

    List<Note> convert(List<ShortNoteDto> employees);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notes", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void update(NotebookDto source, @MappingTarget Notebook target);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void update(NoteDto source, @MappingTarget Note target);
    
}