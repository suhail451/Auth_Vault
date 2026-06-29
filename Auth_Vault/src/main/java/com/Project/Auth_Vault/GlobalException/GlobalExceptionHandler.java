package com.Project.Auth_Vault.GlobalException;

import com.Project.Auth_Vault.Engine.DTO.ErrorResponse;

import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error=new ErrorResponse(404,ex.getMessage());
        return ResponseEntity.status(404).body(error);
    }

     @ExceptionHandler(InValidTokenException.class)
     public ResponseEntity<ErrorResponse> handleInValidToken(InValidTokenException ex){

        ErrorResponse error = new ErrorResponse(401,ex.getMessage());
        return ResponseEntity.status(401).body(error);

     }

     @ExceptionHandler(InValidCredentialException.class)
    public ResponseEntity<ErrorResponse> handleInValidCredential(InValidCredentialException ex){
        ErrorResponse error=new ErrorResponse(401,ex.getMessage());
        return ResponseEntity.status(401).body(error);
     }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(400, ex.getMessage());
        return ResponseEntity.status(400).body(error);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson(HttpMessageNotReadableException ex) {
        ErrorResponse error = new ErrorResponse(400, "Galat JSON format hai request mein");
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoResourceFoundException ex) {
        ErrorResponse error = new ErrorResponse(404, "Ye URL exist nahi karta");
        return ResponseEntity.status(404).body(error);
    }











}
