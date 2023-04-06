package com.notebookapp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.notebookapp.dto.NoteDto;
import com.notebookapp.dto.NotebookDto;
import com.notebookapp.exception.ExceptionMessageType;
import com.notebookapp.exception.NotFoundCustomException;
import com.notebookapp.mapper.NotebookMapper;
import com.notebookapp.model.Note;
import com.notebookapp.model.Notebook;
import com.notebookapp.repository.NoteRepository;
import com.notebookapp.repository.NotebookRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotebookService {

    private final NotebookRepository notebookRepository;
    private final NoteRepository noteRepository;

    public List<NotebookDto> getNoteboks() {
        List<Notebook> notebooks = notebookRepository.findAll();
        return notebooks.stream()
                .map(NotebookMapper.MAPPER::convert)
                .collect(Collectors.toList());
    }

    public NotebookDto create(NotebookDto notebookDto) {
        log.info("Creating new notebook");

        Notebook newNotebook = NotebookMapper.MAPPER.convert(notebookDto);
        Notebook createdNotebook = notebookRepository.save(newNotebook);
        return NotebookMapper.MAPPER.convert(createdNotebook);
    }

    public NotebookDto update(NotebookDto notebookDto) throws NotFoundCustomException {
        log.info("Updating notebook {}", notebookDto.id());

        Optional<Notebook> fromDbOpt = notebookRepository.findById(notebookDto.id());

        if (fromDbOpt.isEmpty()) {
            throw NotFoundCustomException.getNotFoundException(ExceptionMessageType.NOTEBOOK_NOT_FOUND);
        }

        Notebook fromDb = fromDbOpt.get();

        NotebookMapper.MAPPER.update(notebookDto, fromDb);

        Notebook updatedNotebook = notebookRepository.save(fromDb);

        return NotebookMapper.MAPPER.convert(updatedNotebook);
    }

    public void delete(String id) {
        log.info("Deleting notebook {}", id);

        Optional<Notebook> fromDb = notebookRepository.findById(id);
        fromDb.ifPresent(notebookRepository::delete);
    }

    public NotebookDto getNotebookById(String id) throws NotFoundCustomException {
        Optional<Notebook> fromDbOpt = notebookRepository.findById(id);

        if (fromDbOpt.isEmpty()) {
            throw NotFoundCustomException.getNotFoundException(ExceptionMessageType.NOTEBOOK_NOT_FOUND);
        }

        Notebook fromDb = fromDbOpt.get();

        return NotebookMapper.MAPPER.convert(fromDb);
    }

    public NoteDto addNote(String id, NoteDto nodeDto) throws NotFoundCustomException {
        log.info("Adding note to notebook {}", id);

        Optional<Notebook> fromDbOpt = notebookRepository.findById(id);

        if (fromDbOpt.isEmpty()) {
            throw NotFoundCustomException.getNotFoundException(ExceptionMessageType.NOTEBOOK_NOT_FOUND);
        }
        Notebook fromDb = fromDbOpt.get();

        Note newNote = NotebookMapper.MAPPER.convert(nodeDto);
        Note updatedNote = noteRepository.save(newNote);

        fromDb.getNotes().add(updatedNote);
        notebookRepository.save(fromDb);

        return NotebookMapper.MAPPER.convert(updatedNote);
    }

    public NoteDto getNoteById(String id) throws NotFoundCustomException {
        log.info("Getting note by id = {}", id);

        Optional<Note> fromDbOpt = noteRepository.findById(id);
        if (fromDbOpt.isEmpty()) {
            throw NotFoundCustomException.getNotFoundException(ExceptionMessageType.NOTE_NOT_FOUND);
        }
        Note fromDb = fromDbOpt.get();

        return NotebookMapper.MAPPER.convert(fromDb);
    }

}
