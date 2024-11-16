package com.thaqif.aeonbank.repository;

import com.thaqif.aeonbank.entity.Book;
import com.thaqif.aeonbank.entity.BorrowRecord;
import com.thaqif.aeonbank.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    Optional<BorrowRecord> findByBorrowerAndBookAndReturnDateIsNull(Borrower borrower, Book book);
}
