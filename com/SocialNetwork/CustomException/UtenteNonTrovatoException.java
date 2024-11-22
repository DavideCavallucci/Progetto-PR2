package com.SocialNetwork.CustomException;

public class UtenteNonTrovatoException extends Exception {
    public UtenteNonTrovatoException(String utente) {
        super("L'utente " + utente + " non è presente sulla piattaforma.");
    }
}
