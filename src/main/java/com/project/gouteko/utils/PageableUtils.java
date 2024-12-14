package com.project.gouteko.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageableUtils {
    public static Pageable createPageable(Integer pageNo, Integer pageSize) {
        int defaultPageNo = 0;
        int defaultPageSize = 10;

        int finalPageNo = (pageNo != null && pageNo >= 0) ? pageNo : defaultPageNo;
        int finalPageSize = (pageSize != null && pageSize > 0) ? pageSize : defaultPageSize;

        return PageRequest.of(finalPageNo, finalPageSize);
    }

}


