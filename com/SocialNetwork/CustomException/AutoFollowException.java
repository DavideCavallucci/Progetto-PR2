package com.SocialNetwork.CustomException;

public class AutoFollowException extends Exception {
    public AutoFollowException() {
        super("L'utente non può seguirsi da solo.");
    }
}
