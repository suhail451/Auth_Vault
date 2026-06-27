package com.Project.Auth_Vault.GlobalException;

import com.Project.Auth_Vault.DTO.ErrorResponse;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        ErrorResponse error = new ErrorResponse(500, ex.getMessage());
        return ResponseEntity.status(500).body(error);
    }











}
