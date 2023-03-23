package com.notebookapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.notebookapp.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
}
