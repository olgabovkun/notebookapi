package com.notebookapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.notebookapp.model.Notebook;

@Repository
public interface NotebookRepository extends MongoRepository<Notebook, String> {
}
