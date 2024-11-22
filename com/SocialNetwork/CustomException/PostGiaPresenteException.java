package com.SocialNetwork.CustomException;

public class PostGiaPresenteException extends Exception {
    public PostGiaPresenteException(int id) {
        super("Il Post " + id + " è già presente sulla piattaforma.");
    }
}
