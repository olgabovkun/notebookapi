package com.notebookapp.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// separate to different types: TodoList TextNote TableNote 
@Document(collection = "note")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @MongoId(targetType = FieldType.OBJECT_ID)
    private String id;

    private String title;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Override 
    public String toString() {
        return "Note [id=" + id + ", title=" + title + "]";
    }
    
}
