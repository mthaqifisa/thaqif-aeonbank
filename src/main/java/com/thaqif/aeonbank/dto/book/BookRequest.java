package com.thaqif.aeonbank.dto.book;

import jakarta.validation.constraints.NotBlank;

public class BookRequest {
    @NotBlank private String isbn;
    @NotBlank private String title;
    @NotBlank private String author;

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
}
