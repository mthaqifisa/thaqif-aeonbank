package com.thaqif.aeonbank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thaqif.aeonbank.dto.borrower.BorrowerRequest;
import com.thaqif.aeonbank.dto.borrower.BorrowerResponse;
import com.thaqif.aeonbank.service.BorrowerService;
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

@WebMvcTest(BorrowerController.class)
public class BorrowerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BorrowerService service;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void getAllBorrowers_test() throws Exception {
        Mockito.when(service.getAllBorrowers()).thenReturn(new BorrowerResponse());
        mockMvc.perform(get("/v1/borrower/inquiry")).andExpect(status().isOk());
    }

    @Test
    void getAllBorrowerById_test() throws Exception {
        Mockito.when(service.getBorrowerById(anyLong())).thenReturn(new BorrowerResponse());
        mockMvc.perform(get("/v1/borrower/retrieve/1")).andExpect(status().isOk());
    }

    @Test
    void registerBorrower_test() throws Exception {
        BorrowerRequest request = new BorrowerRequest();
        request.setEmail("test@test.com");
        request.setName("test");

        Mockito.when(service.registerBorrower(any(BorrowerRequest.class))).thenReturn(new BorrowerResponse());
        mockMvc.perform(post("/v1/borrower/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());
    }
}
