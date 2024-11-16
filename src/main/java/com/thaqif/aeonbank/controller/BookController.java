package com.thaqif.aeonbank.controller;

import com.thaqif.aeonbank.dto.book.BookRequest;
import com.thaqif.aeonbank.dto.book.BookResponse;
import com.thaqif.aeonbank.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/book")
public class BookController {
    @Autowired private BookService bookService;

    @GetMapping("/inquiry")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/retrieve/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse getBookById(@PathVariable Long id){
        return bookService.getBookById(id);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse registerBook(@Valid @RequestBody BookRequest request){
        return bookService.registerBook(request);
    }
}
