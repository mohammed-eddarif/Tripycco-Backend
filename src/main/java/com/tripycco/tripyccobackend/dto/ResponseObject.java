package com.tripycco.tripyccobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject<T> {
    private boolean success;
    private String message;
    private T data;
}
