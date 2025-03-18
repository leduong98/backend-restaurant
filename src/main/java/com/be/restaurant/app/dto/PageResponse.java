package com.be.restaurant.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    private List<T> data;
    private Metadata metadata;

    @Data
    @AllArgsConstructor
    public static class Metadata {
        private int page = 0;
        private int size = 10;
        private long total = 0;
        private int totalPage = 0;

        public <T> Metadata(Page<T> page) {
            size = page.getSize();
            this.page = page.getNumber();
            this.total = page.getTotalElements();
            this.totalPage = page.getTotalPages();
        }
    }
}
