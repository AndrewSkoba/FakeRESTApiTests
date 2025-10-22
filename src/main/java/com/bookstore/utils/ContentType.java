package com.bookstore.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContentType {
    TXT("text/plain", "txt"),
    JSON("application/json", "json");

    private final String type;
    private final String fileExtension;
}
