package com.rewards.backend;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

	private static final String MESSAGE = "message";
    private static final String STATUS = "status";
    private static final String STATUS_MESSAGE = "status_message";
    private static final String DATA = "data";
	
	public static ResponseEntity<Object> generateResponse(Object responseObj, HttpStatus status,String message ) {
		Map<String, Object> map = new LinkedHashMap<>();
		String statusMessage;
		if(status.value()==200 || status.value()==202 || status.value()==201)
		{
			statusMessage = "SUCCESS";
		}else  statusMessage = "FAILURE";
		map.put(MESSAGE, message);
		map.put(STATUS_MESSAGE, statusMessage);
		map.put(STATUS, status.value());
		map.put(DATA, responseObj);
		return new ResponseEntity<>(map, status);
	}
	
}
