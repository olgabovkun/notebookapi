package com.notebookapp.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.notebookapp.dto.NotebookDto;
import com.notebookapp.model.Notebook;

@SpringBootTest
public class NotebookMapperTest {

    @Test
    public void mapperNotebookDtoToEntity() {
        // given
        NotebookDto source = new NotebookDto("id", "new_title", null, null, null);

        // when
        Notebook target = NotebookMapper.MAPPER.convert(source);

        // then
        Assertions.assertThat(target).isNotNull();
        Assertions.assertThat(target.getId()).isEqualTo(source.id());
        Assertions.assertThat(target.getTitle()).isEqualTo(source.title());
    }

    @Test
    public void mapperEntityToNotebookDto() {
        // given
        Notebook source = new Notebook();
        source.setId("id");
        source.setTitle("new_title");

        // when
        NotebookDto target = NotebookMapper.MAPPER.convert(source);

        // then
        Assertions.assertThat(target).isNotNull();
        Assertions.assertThat(target.id()).isEqualTo(source.getId());
        Assertions.assertThat(target.title()).isEqualTo(source.getTitle());
    }

}
