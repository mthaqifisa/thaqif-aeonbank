package com.thaqif.aeonbank.service;

import com.thaqif.aeonbank.dto.borrower.BorrowerRequest;
import com.thaqif.aeonbank.dto.borrower.BorrowerResponse;
import com.thaqif.aeonbank.entity.Borrower;
import com.thaqif.aeonbank.mapper.BorrowerMapper;
import com.thaqif.aeonbank.repository.BorrowerRepository;
import com.thaqif.aeonbank.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.thaqif.aeonbank.enums.ErrorMessageEnum.*;

@Service
@Transactional
public class BorrowerService {
    @Autowired BorrowerRepository borrowerRepository;
    @Autowired BorrowerMapper borrowerMapper;

    public BorrowerResponse getAllBorrowers(){
        BorrowerResponse response = new BorrowerResponse();
        try {
            List<BorrowerResponse.BorrowerResponseData> data = borrowerRepository.findAll()
                    .stream()
                    .map(borrowerMapper::toDTO)
                    .toList();
            response.setData(data);

            return ResponseUtil.success(response);
        } catch (Exception e) {
            return ResponseUtil.error(response, e.getMessage());
        }
    }

    public BorrowerResponse getBorrowerById(Long borrowerId){
        BorrowerResponse response = new BorrowerResponse();
        try {
            List<BorrowerResponse.BorrowerResponseData> data = borrowerRepository.findById(borrowerId)
                    .map(borrowerMapper::toDTO)
                    .stream()
                    .toList();
            response.setData(data);

            if(data.isEmpty()){
                throw new ArithmeticException(BORROWER_NOT_FOUND.message);
            }

            return ResponseUtil.success(response);
        } catch (Exception e) {
            return ResponseUtil.error(response, e.getMessage());
        }
    }

    public BorrowerResponse registerBorrower(BorrowerRequest request){
        BorrowerResponse response = new BorrowerResponse();
        try {
            Optional<Borrower> existingBorrowerByName = borrowerRepository.findByName(request.getName());
            Optional<Borrower> existingBorrowerByEmail = borrowerRepository.findByEmail(request.getEmail());

            if(existingBorrowerByName.isPresent()){
                throw new ArithmeticException(DUPLICATE_BORROWER_NAME.message);
            }
            if(existingBorrowerByEmail.isPresent()){
                throw new ArithmeticException(DUPLICATE_BORROWER_EMAIL.message);
            }

            Borrower borrower = borrowerMapper.toEntity(request);

            List<BorrowerResponse.BorrowerResponseData> data = Optional.of(borrowerRepository.save(borrower))
                    .map(borrowerMapper::toDTO)
                    .stream()
                    .toList();
            response.setData(data);

            return ResponseUtil.success(response);
        } catch (Exception e) {
            return ResponseUtil.error(response, e.getMessage());
        }
    }
}
