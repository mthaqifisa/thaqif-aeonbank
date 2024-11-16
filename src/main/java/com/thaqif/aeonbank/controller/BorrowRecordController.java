package com.thaqif.aeonbank.controller;

import com.thaqif.aeonbank.dto.borrowrecord.BorrowRecordRequest;
import com.thaqif.aeonbank.dto.borrowrecord.BorrowRecordResponse;
import com.thaqif.aeonbank.service.BorrowRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/borrow-record")
public class BorrowRecordController {
    @Autowired BorrowRecordService borrowRecordService;

    @GetMapping("/inquiry")
    @ResponseStatus(HttpStatus.OK)
    public BorrowRecordResponse getAllBorrowRecord(){
        return borrowRecordService.getAllBorrowRecord();
    }

    @PostMapping("/borrow-book")
    @ResponseStatus(HttpStatus.OK)
    public BorrowRecordResponse borrowBook(@Valid @RequestBody BorrowRecordRequest request){
        return borrowRecordService.borrowBook(request);
    }

    @PostMapping("/return-book")
    @ResponseStatus(HttpStatus.OK)
    public BorrowRecordResponse returnBook(@Valid @RequestBody BorrowRecordRequest request){
        return borrowRecordService.returnBook(request);
    }
}
