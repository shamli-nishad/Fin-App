package com.sha.fin.app.transaction.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(TransactionCustomException.class)
	public ResponseEntity<ErrorResponse> handleTransactionCustomExcpetion(TransactionCustomException exception) {

		return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), exception.getErrorCode()),
				HttpStatus.valueOf(exception.getStatus()));

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralExcpetion(Exception exception) {

		return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name()),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
