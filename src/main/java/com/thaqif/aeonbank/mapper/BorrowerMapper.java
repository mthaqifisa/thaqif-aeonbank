package com.thaqif.aeonbank.mapper;

import com.thaqif.aeonbank.dto.borrower.BorrowerRequest;
import com.thaqif.aeonbank.dto.borrower.BorrowerResponse;
import com.thaqif.aeonbank.entity.Borrower;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BorrowerMapper {
    BorrowerMapper INSTANCE = Mappers.getMapper(BorrowerMapper.class);
    Borrower toEntity(BorrowerRequest borrowRequest);
    BorrowerResponse.BorrowerResponseData toDTO(Borrower borrower);
}
