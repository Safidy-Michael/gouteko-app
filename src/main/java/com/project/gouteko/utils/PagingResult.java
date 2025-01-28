package com.project.gouteko.utils;

import com.project.gouteko.model.Order;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Collection;

@Data
@NoArgsConstructor
public class PagingResult<T> {

    private Collection<T> content;
    private Integer totalPages;
    private long totalElements;
    private Integer size;
    private Integer page;
    private boolean empty;

    public PagingResult(Page<T> pageContent) {
        this.content = pageContent.getContent();
        this.totalPages = pageContent.getTotalPages();
        this.totalElements = pageContent.getTotalElements();
        this.size = pageContent.getSize();
        this.page = pageContent.getNumber() + 1; 
        this.empty = pageContent.isEmpty();
    }
}
