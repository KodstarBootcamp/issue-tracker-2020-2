package com.kodstar.issuetracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class PagesDTO<T> {
    long totalElements;
    int totalPages;
    int pageNumber;
    List<T> content;
}
