package com.thaqif.aeonbank.dto.borrowrecord;

import jakarta.validation.constraints.NotNull;

public class BorrowRecordRequest {
    @NotNull private Long borrowerId;
    @NotNull private Long bookId;

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
