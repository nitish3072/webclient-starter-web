package com.nitish.web.exception;

import com.nitish.web.DefaultWebResultResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request) {
		LOGGER.error("HttpRequestMethodNotSupportedException: {} : {}", ex.getMessage(), ExceptionUtils.getStackTrace(ex));
		return new ResponseEntity<>(new DefaultWebResultResponse<>(HttpStatus.valueOf(status.value()), "Error !!!!! handleHttpRequestMethodNotSupported"), status);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		LOGGER.error("MethodArgumentNotValidException: {} : {}", ex.getMessage(), ExceptionUtils.getStackTrace(ex));
		return new ResponseEntity<>(new DefaultWebResultResponse<>(HttpStatus.valueOf(status.value()), "Error !!!!! handleMethodArgumentNotValid"), status);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			MissingServletRequestParameterException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";
		LOGGER.error("MissingServletRequestParameterException: {} : {}", ex.getMessage(), ExceptionUtils.getStackTrace(ex));

		return new ResponseEntity<>(new DefaultWebResultResponse<>(HttpStatus.valueOf(status.value()), "Error !!!!! handleMissingServletRequestParameter"), status);
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(
			ServletRequestBindingException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		LOGGER.error("ServletRequestBindingException: {} : {}", ex.getMessage(), ExceptionUtils.getStackTrace(ex));

		return new ResponseEntity<>(new DefaultWebResultResponse<>(HttpStatus.valueOf(status.value()), "Error !!!!! handleServletRequestBindingException"), status);
	}

	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(
			ConversionNotSupportedException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		LOGGER.error("ConversionNotSupportedException: {} : {}", ex.getMessage(), ExceptionUtils.getStackTrace(ex));

		return new ResponseEntity<>(new DefaultWebResultResponse<>(HttpStatus.valueOf(status.value()), "Error !!!!! handleConversionNotSupported"), status);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(
			TypeMismatchException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		LOGGER.error("TypeMismatchException: {} : {}", ex.getMessage(), ExceptionUtils.getStackTrace(ex));

		return new ResponseEntity<>(new DefaultWebResultResponse<>(HttpStatus.valueOf(status.value()), "Error !!!!! handleTypeMismatch"), status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		LOGGER.error("HttpMessageNotReadableException: {} : {}", ex.getMessage(), ExceptionUtils.getStackTrace(ex));

		return new ResponseEntity<>(new DefaultWebResultResponse<>(HttpStatus.valueOf(status.value()), "Error !!!!! handleHttpMessageNotReadable"), status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(
			HttpMessageNotWritableException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		LOGGER.error("HttpMessageNotWritableException: {} : {}", ex.getMessage(), ExceptionUtils.getStackTrace(ex));

		return new ResponseEntity<>(new DefaultWebResultResponse<>(HttpStatus.valueOf(status.value()), "Error !!!!! handleHttpMessageNotWritable"), status);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
			NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
		LOGGER.error("NoHandlerFoundException: {} : {}", ex.getMessage(), ExceptionUtils.getStackTrace(ex));

		return new ResponseEntity<>(new DefaultWebResultResponse<>(HttpStatus.valueOf(status.value()),"Error !!!!! handleNoHandlerFoundException"), status);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(
			MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		LOGGER.error("MissingServletRequestPartException: {} : {}", ex.getMessage(), ExceptionUtils.getStackTrace(ex));

		return new ResponseEntity<>(new DefaultWebResultResponse<>(HttpStatus.valueOf(status.value()),"Error !!!!! handleMissingServletRequestPart"), status);
	}

	@Override
	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
			AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		LOGGER.error("AsyncRequestTimeoutException: {} : {}", ex.getMessage(), ExceptionUtils.getStackTrace(ex));

		return new ResponseEntity<>(new DefaultWebResultResponse<>(HttpStatus.valueOf(status.value()),"Error !!!!! handleAsyncRequestTimeoutException"), status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
			HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));
		LOGGER.error("HttpMediaTypeNotSupportedException: {} : {}", ex.getMessage(), ExceptionUtils.getStackTrace(ex));

		return new ResponseEntity<>(new DefaultWebResultResponse<>(HttpStatus.valueOf(status.value()),"Error !!!!! handleHttpMediaTypeNotSupported"), status);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(
			MissingPathVariableException ex,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request) {
		LOGGER.error("MissingPathVariableException: {} : {}", ex.getMessage(), ExceptionUtils.getStackTrace(ex));

		return new ResponseEntity<>(new DefaultWebResultResponse<>(HttpStatus.valueOf(status.value()),"Error !!!!! handleMissingPathVariable"), status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(
			HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers,
			HttpStatusCode status,
			WebRequest request) {
		LOGGER.error("HttpMediaTypeNotAcceptableException: {} : {}", ex.getMessage(), ExceptionUtils.getStackTrace(ex));

		return new ResponseEntity<>(new DefaultWebResultResponse<>(HttpStatus.valueOf(status.value()),"Error !!!!! handleHttpMediaTypeNotAcceptable"), status);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExcpetionHandler(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		LOGGER.error("Exception: {} : {}", ex.getMessage(), ExceptionUtils.getStackTrace(ex));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
