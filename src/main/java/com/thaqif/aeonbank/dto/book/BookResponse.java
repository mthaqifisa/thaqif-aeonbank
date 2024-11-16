package com.thaqif.aeonbank.dto.book;

import com.thaqif.aeonbank.dto.common.CommonResponse;

import java.util.List;

public class BookResponse extends CommonResponse {
    private List<BookResponseData> data;

    public static class BookResponseData {
        private Long id;
        private String isbn;
        private String title;
        private String author;
        private Integer copyNumber;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Integer getCopyNumber() {
            return copyNumber;
        }

        public void setCopyNumber(Integer copyNumber) {
            this.copyNumber = copyNumber;
        }
    }

    public List<BookResponseData> getData() {
        return data;
    }

    public void setData(List<BookResponseData> data) {
        this.data = data;
    }
}
