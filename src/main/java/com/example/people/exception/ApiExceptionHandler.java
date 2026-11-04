package com.example.people.exception;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(PersonNotFoundException.class)
	public ResponseEntity<ApiError> handlePersonNotFound(
		PersonNotFoundException exception,
		HttpServletRequest request
	) {
		return createErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request.getRequestURI(), Map.of());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidationFailure(
		MethodArgumentNotValidException exception,
		HttpServletRequest request
	) {
		Map<String, String> validationErrors = exception.getBindingResult().getFieldErrors().stream()
			.collect(Collectors.toMap(
				fieldError -> fieldError.getField(),
				fieldError -> fieldError.getDefaultMessage() == null
					? "Valor inválido"
					: fieldError.getDefaultMessage(),
				(firstMessage, ignoredMessage) -> firstMessage,
				TreeMap::new
			));
		return createErrorResponse(
			HttpStatus.BAD_REQUEST,
			"A requisição contém campos inválidos.",
			request.getRequestURI(),
			validationErrors
		);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiError> handleUnreadableRequest(
		HttpMessageNotReadableException exception,
		HttpServletRequest request
	) {
		return createErrorResponse(
			HttpStatus.BAD_REQUEST,
			"O corpo da requisição está ausente ou malformado.",
			request.getRequestURI(),
			Map.of()
		);
	}

	private ResponseEntity<ApiError> createErrorResponse(
		HttpStatus status,
		String message,
		String path,
		Map<String, String> validationErrors
	) {
		ApiError apiError = new ApiError(
			status.value(),
			status.getReasonPhrase(),
			message,
			path,
			validationErrors
		);
		return ResponseEntity.status(status).body(apiError);
	}
}
