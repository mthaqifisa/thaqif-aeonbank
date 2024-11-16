package com.thaqif.aeonbank.util;

import com.thaqif.aeonbank.dto.common.CommonResponse;

public class ResponseUtil {
    private ResponseUtil(){}

    public static final String SUCCESS_STATUS = "SUCCESS";
    public static final String FAILED_STATUS = "FAILED";

    public static <T extends CommonResponse> T success(T responseDTO) {
        responseDTO.setStatus(SUCCESS_STATUS);
        return responseDTO;
    }

    public static <T extends CommonResponse> T error(T responseDTO, String errorMessage) {
        responseDTO.setStatus(FAILED_STATUS);
        responseDTO.setErrorMessage(errorMessage);
        return responseDTO;
    }
}
