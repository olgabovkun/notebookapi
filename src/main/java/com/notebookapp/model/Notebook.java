package com.notebookapp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "notebook")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Notebook {

    @MongoId(targetType = FieldType.OBJECT_ID)
    private String id;

    private String title;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    @DocumentReference
    private List<Note> notes = new ArrayList<>();

    @Override 
    public String toString() {
        return "Notebook [id=" + id + ", title=" + title + "]";
    }
    
}
