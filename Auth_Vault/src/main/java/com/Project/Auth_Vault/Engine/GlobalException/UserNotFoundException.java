package com.Project.Auth_Vault.Engine.GlobalException;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message){
        super(message);
    }

}
