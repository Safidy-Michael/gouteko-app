package com.project.gouteko.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.data.domain.PageRequest;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationUtils {

    public static PageRequest getPageable(PaginationRequest request) {
        return PageRequest.of(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
    }
}