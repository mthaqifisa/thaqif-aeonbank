package com.thaqif.aeonbank.service;

import com.thaqif.aeonbank.dto.borrowrecord.BorrowRecordRequest;
import com.thaqif.aeonbank.dto.borrowrecord.BorrowRecordResponse;
import com.thaqif.aeonbank.entity.Book;
import com.thaqif.aeonbank.entity.BorrowRecord;
import com.thaqif.aeonbank.entity.Borrower;
import com.thaqif.aeonbank.mapper.BorrowRecordMapper;
import com.thaqif.aeonbank.repository.BookRepository;
import com.thaqif.aeonbank.repository.BorrowRecordRepository;
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
import static com.thaqif.aeonbank.util.ResponseUtil.FAILED_STATUS;
import static com.thaqif.aeonbank.util.ResponseUtil.SUCCESS_STATUS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BorrowRecordServiceTest {
    @InjectMocks BorrowRecordService borrowRecordService;
    @Mock BorrowRecordRepository borrowRecordRepository;
    @Mock BookRepository bookRepository;
    @Mock BorrowerRepository borrowerRepository;
    @Mock BorrowRecordMapper borrowRecordMapper;

    private BorrowRecord borrowRecord;
    private Book book;
    private Borrower borrower;
    private BorrowRecordRequest borrowRecordRequest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        book = new Book();
        book.setId(1L);
        book.setTitle("title");
        book.setAuthor("author");
        book.setIsbn("12");
        book.setCopyNumber(1);

        borrower = new Borrower();
        borrower.setId(1L);
        borrower.setName("name1");
        borrower.setEmail("email1@test.com");

        borrowRecord = new BorrowRecord();
        borrowRecord.setId(1L);

        borrowRecordRequest = new BorrowRecordRequest();
        borrowRecordRequest.setBorrowerId(1L);
        borrowRecordRequest.setBookId(1L);
    }

    @Test
    void getAllBorrowRecord_successTest(){
        when(borrowRecordRepository.findAll()).thenReturn(List.of(borrowRecord));
        when(borrowRecordMapper.toDTO(any(BorrowRecord.class))).thenReturn(new BorrowRecordResponse.BorrowerRecordResponseData());

        BorrowRecordResponse response = borrowRecordService.getAllBorrowRecord();

        assertNotNull(response);
        assertEquals(response.getStatus(), SUCCESS_STATUS);
    }

    @Test
    void getAllBorrowRecord_exceptionTest(){
        when(borrowRecordRepository.findAll()).thenThrow(new ArithmeticException("random error"));
        when(borrowRecordMapper.toDTO(any(BorrowRecord.class))).thenReturn(new BorrowRecordResponse.BorrowerRecordResponseData());

        BorrowRecordResponse response = borrowRecordService.getAllBorrowRecord();

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), "random error");
    }

    @Test
    void borrowBook_successTest(){
        when(borrowRecordMapper.toDTO(any(BorrowRecord.class))).thenReturn(new BorrowRecordResponse.BorrowerRecordResponseData());
        when(borrowerRepository.findById(anyLong())).thenReturn(Optional.of(borrower));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(borrowRecordRepository.findByBorrowerAndBookAndReturnDateIsNull(any(Borrower.class), any(Book.class))).thenReturn(Optional.empty());
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenReturn(borrowRecord);

        BorrowRecordResponse response = borrowRecordService.borrowBook(borrowRecordRequest);

        assertNotNull(response);
        assertEquals(response.getStatus(), SUCCESS_STATUS);
    }

    @Test
    void borrowBook_borrowerNotFoundTest(){
        when(borrowRecordMapper.toDTO(any(BorrowRecord.class))).thenReturn(new BorrowRecordResponse.BorrowerRecordResponseData());
        when(borrowerRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(book));
        when(borrowRecordRepository.findByBorrowerAndBookAndReturnDateIsNull(any(Borrower.class), any(Book.class))).thenReturn(Optional.empty());
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenReturn(borrowRecord);

        BorrowRecordResponse response = borrowRecordService.borrowBook(borrowRecordRequest);

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), BORROWER_NOT_FOUND.message);
    }

    @Test
    void borrowBook_bookNotFoundTest(){
        when(borrowRecordMapper.toDTO(any(BorrowRecord.class))).thenReturn(new BorrowRecordResponse.BorrowerRecordResponseData());
        when(borrowerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(borrower));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(borrowRecordRepository.findByBorrowerAndBookAndReturnDateIsNull(any(Borrower.class), any(Book.class))).thenReturn(Optional.empty());
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenReturn(borrowRecord);

        BorrowRecordResponse response = borrowRecordService.borrowBook(borrowRecordRequest);

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), BOOK_NOT_FOUND.message);
    }

    @Test
    void borrowBook_duplicateBorrowedBookTest(){
        when(borrowRecordMapper.toDTO(any(BorrowRecord.class))).thenReturn(new BorrowRecordResponse.BorrowerRecordResponseData());
        when(borrowerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(borrower));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(book));
        when(borrowRecordRepository.findByBorrowerAndBookAndReturnDateIsNull(any(Borrower.class), any(Book.class))).thenReturn(Optional.ofNullable(borrowRecord));
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenReturn(borrowRecord);

        BorrowRecordResponse response = borrowRecordService.borrowBook(borrowRecordRequest);

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), DUPLICATE_BORROWED_BOOK.message);
    }

    @Test
    void returnBook_successTest(){
        when(borrowRecordMapper.toDTO(any(BorrowRecord.class))).thenReturn(new BorrowRecordResponse.BorrowerRecordResponseData());
        when(borrowerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(borrower));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(book));
        when(borrowRecordRepository.findByBorrowerAndBookAndReturnDateIsNull(any(Borrower.class), any(Book.class))).thenReturn(Optional.ofNullable(borrowRecord));
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenReturn(borrowRecord);

        BorrowRecordResponse response = borrowRecordService.returnBook(borrowRecordRequest);

        assertNotNull(response);
        assertEquals(response.getStatus(), SUCCESS_STATUS);
    }

    @Test
    void returnBook_borrowerNotFoundTest(){
        when(borrowRecordMapper.toDTO(any(BorrowRecord.class))).thenReturn(new BorrowRecordResponse.BorrowerRecordResponseData());
        when(borrowerRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(book));
        when(borrowRecordRepository.findByBorrowerAndBookAndReturnDateIsNull(any(Borrower.class), any(Book.class))).thenReturn(Optional.ofNullable(borrowRecord));
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenReturn(borrowRecord);

        BorrowRecordResponse response = borrowRecordService.returnBook(borrowRecordRequest);

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), BORROWER_NOT_FOUND.message);
    }

    @Test
    void returnBook_bookNotFoundTest(){
        when(borrowRecordMapper.toDTO(any(BorrowRecord.class))).thenReturn(new BorrowRecordResponse.BorrowerRecordResponseData());
        when(borrowerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(borrower));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(borrowRecordRepository.findByBorrowerAndBookAndReturnDateIsNull(any(Borrower.class), any(Book.class))).thenReturn(Optional.ofNullable(borrowRecord));
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenReturn(borrowRecord);

        BorrowRecordResponse response = borrowRecordService.returnBook(borrowRecordRequest);

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), BOOK_NOT_FOUND.message);
    }

    @Test
    void returnBook_invalidBorrowerTest(){
        when(borrowRecordMapper.toDTO(any(BorrowRecord.class))).thenReturn(new BorrowRecordResponse.BorrowerRecordResponseData());
        when(borrowerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(borrower));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(book));
        when(borrowRecordRepository.findByBorrowerAndBookAndReturnDateIsNull(any(Borrower.class), any(Book.class))).thenReturn(Optional.empty());
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenReturn(borrowRecord);

        BorrowRecordResponse response = borrowRecordService.returnBook(borrowRecordRequest);

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), INVALID_BORROWER.message);
    }
}
