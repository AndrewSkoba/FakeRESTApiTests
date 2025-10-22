package com.bookstore.models;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "traceId")
public class ErrorObject {
    private String type;
    private String title;
    private int status;
    private String traceId;
}