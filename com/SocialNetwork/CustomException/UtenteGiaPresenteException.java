package com.SocialNetwork.CustomException;

public class UtenteGiaPresenteException extends Exception {
    public UtenteGiaPresenteException(String utente){
        super("L'utente " + utente + " è già presente sulla piattaforma.");
    }
}
