package com.thaqif.aeonbank.service;

import com.thaqif.aeonbank.dto.book.BookRequest;
import com.thaqif.aeonbank.dto.book.BookResponse;
import com.thaqif.aeonbank.entity.Book;
import com.thaqif.aeonbank.mapper.BookMapper;
import com.thaqif.aeonbank.repository.BookRepository;
import com.thaqif.aeonbank.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.thaqif.aeonbank.enums.ErrorMessageEnum.BOOK_NOT_FOUND;
import static com.thaqif.aeonbank.enums.ErrorMessageEnum.SBN_DATA_MISMATCHED_ERROR;

@Service
@Transactional
public class BookService {
    @Autowired private BookRepository bookRepository;
    @Autowired private BookMapper bookMapper;

    public BookResponse getAllBooks() {
        BookResponse response = new BookResponse();
        try {
            List<BookResponse.BookResponseData> data = bookRepository.findAll()
                    .stream()
                    .map(bookMapper::toDTO)
                    .toList();
            response.setData(data);

            return ResponseUtil.success(response);
        } catch (Exception e) {
            return ResponseUtil.error(response, e.getMessage());
        }
    }

    public BookResponse getBookById(Long bookId) {
        BookResponse response = new BookResponse();
        try {
            List<BookResponse.BookResponseData> data = bookRepository.findById(bookId)
                    .map(bookMapper::toDTO)
                    .stream()
                    .toList();
            response.setData(data);

            if(data.isEmpty()){
                throw new ArithmeticException(BOOK_NOT_FOUND.message);
            }

            return ResponseUtil.success(response);
        } catch (Exception e) {
            return ResponseUtil.error(response, e.getMessage());
        }
    }

    public BookResponse registerBook(BookRequest request) {
        BookResponse response = new BookResponse();
        try {
            Optional<Book> existingBook = bookRepository.findByIsbn(request.getIsbn());
            Book book;
            if(existingBook.isPresent()){
                book = existingBook.get();;
                if(!existingBook.get().getTitle().equals(request.getTitle()) || !existingBook.get().getAuthor().equals(request.getAuthor())){
                    throw new ArithmeticException(SBN_DATA_MISMATCHED_ERROR.message);
                }
            } else {
                book = bookMapper.toEntity(request);
            }

            book.setCopyNumber(existingBook.map(value -> value.getCopyNumber() + 1).orElse(1));

            List<BookResponse.BookResponseData> data = Optional.of(bookRepository.save(book))
                    .map(bookMapper::toDTO)
                    .stream()
                    .toList();
            response.setData(data);

            return ResponseUtil.success(response);
        } catch (Exception e) {
            return ResponseUtil.error(response, e.getMessage());
        }
    }
}
