package com.notebookapp.controller;

import java.util.List;

import org.assertj.core.util.Lists;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notebookapp.NotebookTestUtils;
import com.notebookapp.dto.NoteDto;
import com.notebookapp.dto.NotebookDto;
import com.notebookapp.service.NotebookService;

@AutoConfigureDataMongo
@WebMvcTest(NotebookController.class)
public class NotebookControllerTest {

    @MockBean
    private NotebookService notebookService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getNoteboks() throws Exception {
        // given
        List<NotebookDto> notebookDtos = NotebookTestUtils.createNotebookDtoListWithoutNotes();

        // when
        Mockito.when(notebookService.getNoteboks()).thenReturn(Lists.newArrayList(notebookDtos));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/notebook"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(notebookDtos.size())));
    }

    @Test
    public void createNotebook() throws Exception {
        // given
        NotebookDto notebookDto = NotebookTestUtils.createNotebookDtoWithoutNotes();

        // when
        Mockito.when(notebookService.create(notebookDto)).thenReturn(notebookDto);

        mockMvc.perform(MockMvcRequestBuilders.post(
                "/api/notebook/create").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(notebookDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void createNotebookValidationError(String title) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(
                "/api/notebook/create").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NotebookTestUtils.createNotebookDtoWithoutNotes("id", title))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateNotebook() throws Exception {
        // given
        NotebookDto notebookDto = NotebookTestUtils.createNotebookDtoWithoutNotes();

        // when
        Mockito.when(notebookService.update(notebookDto)).thenReturn(notebookDto);

        mockMvc.perform(MockMvcRequestBuilders.put(
                "/api/notebook/update").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(notebookDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void updateNotebookValidationError(String title) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(
                "/api/notebook/update").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NotebookTestUtils.createNotebookDtoWithoutNotes("id", title))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deleteNotebook() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("/api/notebook/%s", "id")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void addNote() throws Exception {
        // given
        NoteDto noteDto = NotebookTestUtils.createNoteDto();

        // when
        Mockito.when(notebookService.addNote("notebook_id", noteDto)).thenReturn(noteDto);

        mockMvc.perform(MockMvcRequestBuilders.post(String.format("/api/notebook/%s/add-note", "notebook_id"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noteDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void addNoteValidationError(String title) throws Exception {
        // given
        NoteDto noteDto = NotebookTestUtils.createNoteDto("note_id", title);

        // when
        Mockito.when(notebookService.addNote("notebook_id", noteDto)).thenReturn(noteDto);

        mockMvc.perform(MockMvcRequestBuilders.post(String.format("/api/notebook/%s/add-note", "notebook_id"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noteDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getNoteById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/api/notebook/note/%s", "note_id")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
