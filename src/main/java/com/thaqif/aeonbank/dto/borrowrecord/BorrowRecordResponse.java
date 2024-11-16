package com.thaqif.aeonbank.dto.borrowrecord;

import com.thaqif.aeonbank.dto.common.CommonResponse;
import com.thaqif.aeonbank.entity.Book;
import com.thaqif.aeonbank.entity.Borrower;

import java.time.LocalDate;
import java.util.List;

public class BorrowRecordResponse extends CommonResponse {
    List<BorrowerRecordResponseData> data;

    public static class BorrowerRecordResponseData {
        private Long id;
        private LocalDate borrowDate;
        private LocalDate returnDate;

        private Book book;
        private Borrower borrower;

        public Book getBook() {
            return book;
        }

        public void setBook(Book book) {
            this.book = book;
        }

        public Borrower getBorrower() {
            return borrower;
        }

        public void setBorrower(Borrower borrower) {
            this.borrower = borrower;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public LocalDate getBorrowDate() {
            return borrowDate;
        }

        public void setBorrowDate(LocalDate borrowDate) {
            this.borrowDate = borrowDate;
        }

        public LocalDate getReturnDate() {
            return returnDate;
        }

        public void setReturnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
        }
    }

    public List<BorrowerRecordResponseData> getData() {
        return data;
    }

    public void setData(List<BorrowerRecordResponseData> data) {
        this.data = data;
    }
}
