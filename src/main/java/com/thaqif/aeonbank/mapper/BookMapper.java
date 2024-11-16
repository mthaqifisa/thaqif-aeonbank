package com.thaqif.aeonbank.mapper;

import com.thaqif.aeonbank.dto.book.BookRequest;
import com.thaqif.aeonbank.dto.book.BookResponse;
import com.thaqif.aeonbank.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    Book toEntity(BookRequest bookRequest);
    BookResponse.BookResponseData toDTO(Book book);
}
