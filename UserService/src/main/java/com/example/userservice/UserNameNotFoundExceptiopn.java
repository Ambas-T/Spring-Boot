package com.example.userservice;

public class UserNameNotFoundExceptiopn extends RuntimeException{

    UserNameNotFoundExceptiopn(String exception){
        super((exception));
    }
}
