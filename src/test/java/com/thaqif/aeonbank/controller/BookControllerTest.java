package com.thaqif.aeonbank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thaqif.aeonbank.dto.book.BookRequest;
import com.thaqif.aeonbank.dto.book.BookResponse;
import com.thaqif.aeonbank.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private BookService service;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void getAllBooks_test() throws Exception {
        Mockito.when(service.getAllBooks()).thenReturn(new BookResponse());
        mockMvc.perform(get("/v1/book/inquiry")).andExpect(status().isOk());
    }

    @Test
    void getBookById_test() throws Exception {
        Mockito.when(service.getBookById(anyLong())).thenReturn(new BookResponse());
        mockMvc.perform(get("/v1/book/retrieve/1")).andExpect(status().isOk());
    }

    @Test
    void registerBook_test() throws Exception {
        BookRequest request = new BookRequest();
        request.setIsbn("1234567890123");
        request.setAuthor("author");
        request.setTitle("title");

        Mockito.when(service.registerBook(any(BookRequest.class))).thenReturn(new BookResponse());
        mockMvc.perform(post("/v1/book/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());
    }
}
