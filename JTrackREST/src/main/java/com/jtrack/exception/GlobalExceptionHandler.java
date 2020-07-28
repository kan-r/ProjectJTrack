package com.jtrack.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<Object> handleException(InvalidDataException e){
		
		 Map<String, Object> body = new LinkedHashMap<>();
	     body.put("error", "invalid_data");
	     body.put("message", e.getMessage());
	     body.put("timestamp", LocalDateTime.now());
	        
		return ResponseEntity.badRequest().body(body);
	}
}
