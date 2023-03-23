package com.notebookapp.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.notebookapp.NotebookTestUtils;
import com.notebookapp.dto.NoteDto;
import com.notebookapp.dto.NotebookDto;
import com.notebookapp.exception.ExceptionMessageType;
import com.notebookapp.exception.NotFoundCustomException;
import com.notebookapp.mapper.NotebookMapper;
import com.notebookapp.model.Note;
import com.notebookapp.model.Notebook;
import com.notebookapp.repository.NoteRepository;
import com.notebookapp.repository.NotebookRepository;

@SpringBootTest
class NotebookServiceTest {

	@InjectMocks
	private NotebookService notebookService;

	@Mock
	private NotebookRepository notebookRepository;

	@Mock
	private NoteRepository noteRepository;

	@Test
	public void createNotebook() {
		// given
		NotebookDto notebookDto = NotebookTestUtils.createNotebookDtoWithoutNotes();
		when(notebookRepository.save(any(Notebook.class))).thenReturn(NotebookMapper.MAPPER.convert(notebookDto));

		// when
		NotebookDto created = notebookService.create(notebookDto);

		// then
		Mockito.verify(notebookRepository).save(any(Notebook.class));
		Assertions.assertThat(created.title()).isEqualTo(notebookDto.title());
	}

	@Test
	public void updateNotebook() throws NotFoundCustomException {
		// given
		NotebookDto notebookDto = NotebookTestUtils.createNotebookDtoWithoutNotes();
		when(notebookRepository.findById(any(String.class)))
				.thenReturn(Optional.of(NotebookTestUtils.createNotebookWithoutNotes()));
		when(notebookRepository.save(any(Notebook.class))).thenReturn(NotebookMapper.MAPPER.convert(notebookDto));

		// when
		NotebookDto updated = notebookService.update(notebookDto);

		// then
		Mockito.verify(notebookRepository).save(any(Notebook.class));
		Mockito.verify(notebookRepository).findById(any(String.class));
		Assertions.assertThat(updated.title()).isEqualTo(notebookDto.title());
	}

	@Test
	public void updateNotebookThrowNotFoundError() throws NotFoundCustomException {
		NotebookDto notebookDto = NotebookTestUtils.createNotebookDtoWithoutNotes();
		Assertions.assertThatThrownBy(() -> notebookService.update(notebookDto))
				.hasMessage(ExceptionMessageType.NOTEBOOK_NOT_FOUND);
	}

	@Test
	public void deleteNotebook() {
		// given
		Notebook notebookFromDb = NotebookTestUtils.createNotebookWithoutNotes();
		when(notebookRepository.findById(any(String.class))).thenReturn(Optional.of(notebookFromDb));

		// when
		notebookService.delete(notebookFromDb.getId());

		// then
		Mockito.verify(notebookRepository).delete(any(Notebook.class));
	}

	@Test
	public void getNotebooks() {
		// given
		when(notebookRepository.findAll()).thenReturn(List.of(NotebookTestUtils.createNotebookWithoutNotes()));

		// when
		List<NotebookDto> notebooks = notebookService.getNoteboks();

		// then
		Mockito.verify(notebookRepository).findAll();
		Assertions.assertThat(notebooks).hasSize(1);
	}

	@Test
	public void getNoteById() throws NotFoundCustomException {
		// given
		Note note = NotebookTestUtils.createNote();
		when(noteRepository.findById(any(String.class))).thenReturn(Optional.of(note));

		// when
		NoteDto noteDto = notebookService.getNoteById(note.getId());

		// then
		Mockito.verify(noteRepository).findById(any(String.class));
		Assertions.assertThat(noteDto).isNotNull();
	}

	@Test
	public void getNoteByIdThrowError() {
		Assertions.assertThatThrownBy(() -> notebookService.getNoteById(any(String.class)))
				.hasMessage(ExceptionMessageType.NOTE_NOT_FOUND);
	}

	@Test
	public void addNoteToNotebook() throws NotFoundCustomException {
		// given
		Notebook notebook = NotebookTestUtils.createNotebookWithNotes();
		when(notebookRepository.findById(any(String.class))).thenReturn(Optional.of(notebook));
		when(noteRepository.save(any(Note.class))).thenReturn(NotebookTestUtils.createNote());

		// when
		NoteDto noteDto = notebookService.addNote(anyString(), NotebookTestUtils.createNoteDto());

		// then
		Mockito.verify(notebookRepository).findById(any(String.class));
		Mockito.verify(notebookRepository).save(any(Notebook.class));
		Mockito.verify(noteRepository).save(any(Note.class));
		Assertions.assertThat(noteDto).isNotNull();
		Assertions.assertThat(notebook.getNotes()).isNotEmpty();
		Assertions.assertThat(notebook.getNotes()).hasSize(3);
	}

	@Test
	public void addNoteThrowError() {
		Assertions.assertThatThrownBy(() -> notebookService.addNote(null, any(NoteDto.class)))
				.hasMessage(ExceptionMessageType.NOTEBOOK_NOT_FOUND);
	}

}
