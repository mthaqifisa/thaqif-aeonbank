package com.thaqif.aeonbank.controller;

import com.thaqif.aeonbank.dto.borrower.BorrowerRequest;
import com.thaqif.aeonbank.dto.borrower.BorrowerResponse;
import com.thaqif.aeonbank.service.BorrowerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/borrower")
public class BorrowerController {
    @Autowired BorrowerService borrowerService;

    @GetMapping("/inquiry")
    @ResponseStatus(HttpStatus.OK)
    public BorrowerResponse getAllBorrowers(){
        return borrowerService.getAllBorrowers();
    }

    @GetMapping("/retrieve/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BorrowerResponse getBorrowerById(@PathVariable Long id){
        return borrowerService.getBorrowerById(id);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public BorrowerResponse registerBorrower(@Valid @RequestBody BorrowerRequest request){
        return borrowerService.registerBorrower(request);
    }
}
