package com.thaqif.aeonbank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thaqif.aeonbank.dto.borrowrecord.BorrowRecordRequest;
import com.thaqif.aeonbank.dto.borrowrecord.BorrowRecordResponse;
import com.thaqif.aeonbank.service.BorrowRecordService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BorrowRecordController.class)
public class BorrowRecordControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BorrowRecordService service;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void getAllBorrowRecord_test() throws Exception {
        Mockito.when(service.getAllBorrowRecord()).thenReturn(new BorrowRecordResponse());
        mockMvc.perform(get("/v1/borrow-record/inquiry")).andExpect(status().isOk());
    }

    @Test
    void borrowBook_test() throws Exception {
        BorrowRecordRequest request = new BorrowRecordRequest();
        request.setBookId(1L);
        request.setBorrowerId(1L);

        Mockito.when(service.borrowBook(any(BorrowRecordRequest.class))).thenReturn(new BorrowRecordResponse());
        mockMvc.perform(post("/v1/borrow-record/borrow-book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());
    }

    @Test
    void returnBook_test() throws Exception {
        BorrowRecordRequest request = new BorrowRecordRequest();
        request.setBookId(1L);
        request.setBorrowerId(1L);

        Mockito.when(service.returnBook(any(BorrowRecordRequest.class))).thenReturn(new BorrowRecordResponse());
        mockMvc.perform(post("/v1/borrow-record/return-book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());
    }
}
