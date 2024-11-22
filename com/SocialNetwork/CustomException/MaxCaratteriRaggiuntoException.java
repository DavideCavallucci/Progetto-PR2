package com.SocialNetwork.CustomException;

public class MaxCaratteriRaggiuntoException extends Exception{
    public MaxCaratteriRaggiuntoException(int caratteriMassini) {
        super("Il post ha raggiunto la lunghezza massima di " + caratteriMassini + " caratteri.");
    }
}
