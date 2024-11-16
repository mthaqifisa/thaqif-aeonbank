package com.thaqif.aeonbank.enums;

public enum ErrorMessageEnum {
    SBN_DATA_MISMATCHED_ERROR("Books with the same ISBN must have the same title and author"),
    DUPLICATE_BORROWER_NAME("Borrower with the same name already exists"),
    DUPLICATE_BORROWER_EMAIL("Borrower with the same email already exists"),
    DUPLICATE_BORROWED_BOOK("Book is already borrowed"),
    BORROWER_NOT_FOUND("Borrower not found"),
    BOOK_NOT_FOUND("Book not found"),
    INVALID_BORROWER("No active borrow record found");

    public final String message;

    private ErrorMessageEnum(String message) {
        this.message = message;
    }
}
