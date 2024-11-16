package com.thaqif.aeonbank.mapper;

import com.thaqif.aeonbank.dto.borrowrecord.BorrowRecordRequest;
import com.thaqif.aeonbank.dto.borrowrecord.BorrowRecordResponse;
import com.thaqif.aeonbank.entity.BorrowRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BorrowRecordMapper {
    BorrowRecordMapper INSTANCE = Mappers.getMapper(BorrowRecordMapper.class);
    BorrowRecord toEntity(BorrowRecordRequest borrowRecordRequest);
    BorrowRecordResponse.BorrowerRecordResponseData toDTO(BorrowRecord borrowRecord);
}
