package com.enigmacamp.chall_koperasikita.utils;

import com.enigmacamp.chall_koperasikita.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ResponseEntity<CommonResponse<T>> buildResponse(HttpStatus httpStatus, String message, T data) {
        CommonResponse<T> response = CommonResponse.<T>builder()
                .statusCode(httpStatus.value())
                .message(message)
                .data(data)
                .build();

        return ResponseEntity.status(httpStatus).body(response);
    }
}
