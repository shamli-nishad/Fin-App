package com.sha.fin.app.transaction.external.error;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sha.fin.app.transaction.error.ErrorResponse;
import com.sha.fin.app.transaction.error.TransactionCustomException;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		
		ErrorResponse errorResponse;
		try {
			errorResponse = new ObjectMapper().readValue(response.body().asInputStream(), ErrorResponse.class);
			return new TransactionCustomException(errorResponse.message(), errorResponse.erroCode(), response.status());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
