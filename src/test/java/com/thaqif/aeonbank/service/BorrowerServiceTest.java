package com.thaqif.aeonbank.service;

import com.thaqif.aeonbank.dto.borrower.BorrowerRequest;
import com.thaqif.aeonbank.dto.borrower.BorrowerResponse;
import com.thaqif.aeonbank.entity.Borrower;
import com.thaqif.aeonbank.mapper.BorrowerMapper;
import com.thaqif.aeonbank.repository.BorrowerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.thaqif.aeonbank.enums.ErrorMessageEnum.*;
import static com.thaqif.aeonbank.util.ResponseUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BorrowerServiceTest {
    @InjectMocks private BorrowerService borrowerService;
    @Mock private BorrowerRepository borrowerRepository;
    @Mock private BorrowerMapper borrowerMapper;

    private Borrower borrower;
    private BorrowerRequest borrowerRequest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        borrower = new Borrower();
        borrower.setId(1L);
        borrower.setName("name1");
        borrower.setEmail("email1@test.com");

        borrowerRequest = new BorrowerRequest();
        borrowerRequest.setName("name");
        borrowerRequest.setEmail("email@test.com");
    }

    @Test
    void getAllBorrowers_successTest() {
        when(borrowerRepository.findAll()).thenReturn(List.of(borrower));
        when(borrowerMapper.toDTO(any(Borrower.class))).thenReturn(new BorrowerResponse.BorrowerResponseData());

        BorrowerResponse response = borrowerService.getAllBorrowers();

        assertNotNull(response);
        assertEquals(response.getStatus(), SUCCESS_STATUS);
    }

    @Test
    void getAllBorrowers_exceptionTest() {
        when(borrowerRepository.findAll()).thenThrow(new ArithmeticException("random error"));
        when(borrowerMapper.toDTO(any(Borrower.class))).thenReturn(new BorrowerResponse.BorrowerResponseData());

        BorrowerResponse response = borrowerService.getAllBorrowers();

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), "random error");
    }

    @Test
    void getBorrowerById_successTest() {
        when(borrowerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(borrower));
        when(borrowerMapper.toDTO(any(Borrower.class))).thenReturn(new BorrowerResponse.BorrowerResponseData());

        BorrowerResponse response = borrowerService.getBorrowerById(anyLong());

        assertNotNull(response);
        assertEquals(response.getStatus(), SUCCESS_STATUS);
    }

    @Test
    void getBorrowerById_notFoundTest() {
        when(borrowerRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(borrowerMapper.toDTO(any(Borrower.class))).thenReturn(new BorrowerResponse.BorrowerResponseData());

        BorrowerResponse response = borrowerService.getBorrowerById(anyLong());

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), BORROWER_NOT_FOUND.message);
    }

    @Test
    void getBorrowerById_exceptionTest() {
        when(borrowerRepository.findById(anyLong())).thenThrow(new ArithmeticException("random error"));
        when(borrowerMapper.toDTO(any(Borrower.class))).thenReturn(new BorrowerResponse.BorrowerResponseData());

        BorrowerResponse response = borrowerService.getBorrowerById(anyLong());

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), "random error");
    }

    @Test
    void registerBorrower_successTest() {
        when(borrowerRepository.findByName(any())).thenReturn(Optional.empty());
        when(borrowerRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);
        when(borrowerMapper.toDTO(any(Borrower.class))).thenReturn(new BorrowerResponse.BorrowerResponseData());
        when(borrowerMapper.toEntity(any(BorrowerRequest.class))).thenReturn(new Borrower());

        BorrowerResponse response = borrowerService.registerBorrower(borrowerRequest);

        assertNotNull(response);
        assertEquals(response.getStatus(), SUCCESS_STATUS);
    }

    @Test
    void registerBorrower_duplicateNameTest() {
        when(borrowerRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(borrowerRepository.findByName(any())).thenReturn(Optional.of(new Borrower()));
        when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);
        when(borrowerMapper.toDTO(any(Borrower.class))).thenReturn(new BorrowerResponse.BorrowerResponseData());
        when(borrowerMapper.toEntity(any(BorrowerRequest.class))).thenReturn(new Borrower());

        BorrowerResponse response = borrowerService.registerBorrower(borrowerRequest);

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), DUPLICATE_BORROWER_NAME.message);
    }

    @Test
    void registerBorrower_duplicateEmailTest() {
        when(borrowerRepository.findByEmail(any())).thenReturn(Optional.of(new Borrower()));
        when(borrowerRepository.findByName(any())).thenReturn(Optional.empty());
        when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);
        when(borrowerMapper.toDTO(any(Borrower.class))).thenReturn(new BorrowerResponse.BorrowerResponseData());
        when(borrowerMapper.toEntity(any(BorrowerRequest.class))).thenReturn(new Borrower());

        BorrowerResponse response = borrowerService.registerBorrower(borrowerRequest);

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), DUPLICATE_BORROWER_EMAIL.message);
    }

    @Test
    void registerBorrower_exceptionTest() {
        when(borrowerRepository.findByName(any())).thenThrow(new ArithmeticException("random error"));
        when(borrowerRepository.findByEmail(any())).thenReturn(Optional.ofNullable(borrower));
        when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);
        when(borrowerMapper.toDTO(any(Borrower.class))).thenReturn(new BorrowerResponse.BorrowerResponseData());
        when(borrowerMapper.toEntity(any(BorrowerRequest.class))).thenReturn(new Borrower());

        BorrowerResponse response = borrowerService.registerBorrower(borrowerRequest);

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), "random error");
    }
}
