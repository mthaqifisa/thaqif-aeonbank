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
import com.thaqif.aeonbank.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.thaqif.aeonbank.enums.ErrorMessageEnum.*;

@Service
@Transactional
public class BorrowRecordService {
    @Autowired BorrowRecordRepository borrowRecordRepository;
    @Autowired BookRepository bookRepository;
    @Autowired BorrowerRepository borrowerRepository;
    @Autowired BorrowRecordMapper borrowRecordMapper;

    public BorrowRecordResponse getAllBorrowRecord(){
        BorrowRecordResponse response = new BorrowRecordResponse();
        try {
            List<BorrowRecordResponse.BorrowerRecordResponseData> data = borrowRecordRepository.findAll()
                    .stream()
                    .map(borrowRecordMapper::toDTO)
                    .toList();
            response.setData(data);

            return ResponseUtil.success(response);
        } catch (Exception e) {
            return ResponseUtil.error(response, e.getMessage());
        }
    }

    public BorrowRecordResponse borrowBook(BorrowRecordRequest request){
        BorrowRecordResponse response = new BorrowRecordResponse();
        try {
            Borrower borrower = borrowerRepository.findById(request.getBorrowerId())
                    .orElseThrow(() -> new ArithmeticException(BORROWER_NOT_FOUND.message));

            Book book = bookRepository.findById(request.getBookId())
                    .orElseThrow(() -> new ArithmeticException(BOOK_NOT_FOUND.message));

            if(borrowRecordRepository.findByBorrowerAndBookAndReturnDateIsNull(borrower, book).isPresent()){
                throw new ArithmeticException(DUPLICATE_BORROWED_BOOK.message);
            }

            BorrowRecord borrowRecord = new BorrowRecord();
            borrowRecord.setBorrower(borrower);
            borrowRecord.setBook(book);
            borrowRecord.setBorrowDate(LocalDate.now());

            List<BorrowRecordResponse.BorrowerRecordResponseData> data = Optional.of(borrowRecordRepository.save(borrowRecord))
                    .map(borrowRecordMapper::toDTO)
                    .stream()
                    .toList();
            response.setData(data);

            return ResponseUtil.success(response);
        } catch (Exception e) {
            return ResponseUtil.error(response, e.getMessage());
        }
    }

    public BorrowRecordResponse returnBook(BorrowRecordRequest request){
        BorrowRecordResponse response = new BorrowRecordResponse();
        try {
            Borrower borrower = borrowerRepository.findById(request.getBorrowerId())
                    .orElseThrow(() -> new ArithmeticException(BORROWER_NOT_FOUND.message));

            Book book = bookRepository.findById(request.getBookId())
                    .orElseThrow(() -> new ArithmeticException(BOOK_NOT_FOUND.message));

            BorrowRecord borrowRecord = borrowRecordRepository.findByBorrowerAndBookAndReturnDateIsNull(borrower, book)
                    .orElseThrow(() -> new ArithmeticException(INVALID_BORROWER.message));

            borrowRecord.setReturnDate(LocalDate.now());

            List<BorrowRecordResponse.BorrowerRecordResponseData> data = Optional.of(borrowRecordRepository.save(borrowRecord))
                    .map(borrowRecordMapper::toDTO)
                    .stream()
                    .toList();
            response.setData(data);

            return ResponseUtil.success(response);
        } catch (Exception e) {
            return ResponseUtil.error(response, e.getMessage());
        }
    }
}
