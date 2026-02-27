package fr.paillaugue.outfitter.common;

import fr.paillaugue.outfitter.common.paging.PageDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PagingTests {

    @Test
    @DisplayName("Test the creation of a PageDTO from an empty Spring Page")
    public void testPageDTOCreation() {
        // given a Spring Page with some content
        var springPage = Page.empty();
        var pageDTO = PageDTO.fromPage(springPage);
        // then the created PageDTO should have the same content and pagination info as the original Spring Page
        assertSameContent(pageDTO, springPage);
    }

    @Test
    @DisplayName("Test the creation of a PageDTO from a Spring Page with content")
    public void testPageDTOCreationWithContent() {
        // given a Spring Page with some content
        var springPage = new PageImpl<>(List.of("item1", "item2", "item3"), PageRequest.of(0, 3), 3);
        var pageDTO = PageDTO.fromPage(springPage);
        // then the created PageDTO should have the same content and pagination info as the original Spring Page
        assertSameContent(pageDTO, springPage);
    }

    private void assertSameContent(PageDTO<?> pageDTO, Page<?> springPage) {
        assertEquals(pageDTO.content(), springPage.getContent(), "Content should be the same");
        assertEquals(pageDTO.pageNumber(), springPage.getNumber(), "Page number should be the same");
        assertEquals(pageDTO.pageSize(), springPage.getSize(), "Page size should be the same");
        assertEquals(pageDTO.totalElements(), springPage.getTotalElements(), "Total elements should be the same");
        assertEquals(pageDTO.totalPages(), springPage.getTotalPages(), "Total pages should be the same");
        assertEquals(pageDTO.isLast(), springPage.isLast(), "isLast should be the same");
        assertEquals(pageDTO.isFirst(), springPage.isFirst(), "isFirst should be the same");
    }

}
