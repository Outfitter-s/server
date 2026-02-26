package fr.paillaugue.outfitter.common.paging;

import org.springframework.data.domain.Page;

import java.util.List;

// ? This class is a simple wrapper of the original Spring Page class
// ? It might not be strictly necessary per se, but I have no time to investigate
// ? why in the hell does the Page class is not serializable by default and throws
public record PageDTO<T>(List<T> content, int pageNumber, int pageSize, long totalElements, int totalPages, boolean isLast, boolean isFirst) {
    public static <T> PageDTO<T> fromPage(Page<T> page) {
        return new PageDTO<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst()
        );
    }

}