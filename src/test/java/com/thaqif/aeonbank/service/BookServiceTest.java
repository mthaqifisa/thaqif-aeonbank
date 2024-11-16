package com.thaqif.aeonbank.service;

import com.thaqif.aeonbank.dto.book.BookRequest;
import com.thaqif.aeonbank.dto.book.BookResponse;
import com.thaqif.aeonbank.entity.Book;
import com.thaqif.aeonbank.mapper.BookMapper;
import com.thaqif.aeonbank.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.thaqif.aeonbank.enums.ErrorMessageEnum.BOOK_NOT_FOUND;
import static com.thaqif.aeonbank.enums.ErrorMessageEnum.SBN_DATA_MISMATCHED_ERROR;
import static com.thaqif.aeonbank.util.ResponseUtil.FAILED_STATUS;
import static com.thaqif.aeonbank.util.ResponseUtil.SUCCESS_STATUS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceTest {
    @InjectMocks private BookService bookService;
    @Mock private BookRepository bookRepository;
    @Mock private BookMapper bookMapper;

    private Book book;
    private BookRequest bookRequest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        book = new Book();
        book.setId(1L);
        book.setTitle("title");
        book.setAuthor("author");
        book.setIsbn("12");
        book.setCopyNumber(1);

        bookRequest = new BookRequest();
        bookRequest.setTitle("title");
        bookRequest.setAuthor("author");
        bookRequest.setIsbn("12");
    }

    @Test
    void getAllBooks_successTest() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookMapper.toDTO(any(Book.class))).thenReturn(new BookResponse.BookResponseData());

        BookResponse response = bookService.getAllBooks();

        assertNotNull(response);
        assertEquals(response.getStatus(), SUCCESS_STATUS);
    }

    @Test
    void getAllBooks_exceptionTest() {
        when(bookRepository.findAll()).thenThrow(new ArithmeticException("random error"));
        when(bookMapper.toDTO(any(Book.class))).thenReturn(new BookResponse.BookResponseData());

        BookResponse response = bookService.getAllBooks();

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), "random error");
    }

    @Test
    void getBookById_successTest() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(book));
        when(bookMapper.toDTO(any(Book.class))).thenReturn(new BookResponse.BookResponseData());

        BookResponse response = bookService.getBookById(anyLong());

        assertNotNull(response);
        assertEquals(response.getStatus(), SUCCESS_STATUS);
    }

    @Test
    void getBookById_notFoundTest() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(bookMapper.toDTO(any(Book.class))).thenReturn(new BookResponse.BookResponseData());

        BookResponse response = bookService.getBookById(anyLong());

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), BOOK_NOT_FOUND.message);
    }

    @Test
    void getBookById_exceptionTest() {
        when(bookRepository.findById(anyLong())).thenThrow(new ArithmeticException("random error"));
        when(bookMapper.toDTO(any(Book.class))).thenReturn(new BookResponse.BookResponseData());

        BookResponse response = bookService.getBookById(anyLong());

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), "random error");
    }

    @Test
    void registerBook_existingBookSuccessTest() {
        when(bookRepository.findByIsbn(any())).thenReturn(Optional.ofNullable(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDTO(any(Book.class))).thenReturn(new BookResponse.BookResponseData());
        when(bookMapper.toEntity(any(BookRequest.class))).thenReturn(new Book());

        BookResponse response = bookService.registerBook(bookRequest);

        assertNotNull(response);
        assertEquals(response.getStatus(), SUCCESS_STATUS);
    }

    @Test
    void registerBook_newBookSuccessTest() {
        when(bookRepository.findByIsbn(any())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDTO(any(Book.class))).thenReturn(new BookResponse.BookResponseData());
        when(bookMapper.toEntity(any(BookRequest.class))).thenReturn(new Book());

        BookResponse response = bookService.registerBook(bookRequest);

        assertNotNull(response);
        assertEquals(response.getStatus(), SUCCESS_STATUS);
    }

    @Test
    void registerBook_mismatchDataTest() {
        when(bookRepository.findByIsbn(any())).thenReturn(Optional.ofNullable(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDTO(any(Book.class))).thenReturn(new BookResponse.BookResponseData());
        when(bookMapper.toEntity(any(BookRequest.class))).thenReturn(new Book());

        BookRequest sampleRequest = new BookRequest();
        sampleRequest.setTitle("titleX");
        sampleRequest.setAuthor("authorX");
        sampleRequest.setIsbn("12");
        BookResponse response = bookService.registerBook(sampleRequest);

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), SBN_DATA_MISMATCHED_ERROR.message);
    }

    @Test
    void registerBook_exceptionTest() {
        when(bookRepository.findByIsbn(any())).thenThrow(new ArithmeticException("random error"));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDTO(any(Book.class))).thenReturn(new BookResponse.BookResponseData());
        when(bookMapper.toEntity(any(BookRequest.class))).thenReturn(new Book());

        BookResponse response = bookService.registerBook(bookRequest);

        assertNotNull(response);
        assertEquals(response.getStatus(), FAILED_STATUS);
        assertEquals(response.getErrorMessage(), "random error");
    }
}
