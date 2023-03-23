package com.notebookapp.dto;

import java.util.Date;

public record CustomErrorDto(
        int statusCode,

        Date timestamp,

        String message,

        String description) {
}
