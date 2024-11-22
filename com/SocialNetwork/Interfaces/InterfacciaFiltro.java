package com.SocialNetwork.Interfaces;

import com.SocialNetwork.CustomException.AutoFollowException;
import com.SocialNetwork.CustomException.MaxCaratteriRaggiuntoException;
import com.SocialNetwork.CustomException.UtenteNonTrovatoException;
import com.SocialNetwork.Post;

import java.util.ArrayList;

public interface InterfacciaFiltro extends InterfacciaSocial {
    /**
     * REQUIRES: author ≠ null ∧ text ≠ null
     * EFFECTS: viene controllato se il post che si vuole pubblicare, comincia con "rep + id ∈ N".<br>
     * In quel caso, viene "segnalato" il post in questione se esiste.
     * Quando un Post viene segnalato, viene aggiunto in una lista, la quale contiene infatti tutti i Post da revisionare
     * perchè segnalati da altri utenti per contenuti offensivi.
     * Ritorna una reference al Post appena pubblicato.
     *
     * THROWS:
     * UtenteNonTrovatoException: se author ∉ social
     * MaxCaratteriRaggiuntoException: se text.length > 140
     * NullPointerException: (unchecked exception) se author = null V text=null
     * AutoFollowException: se ∃p ∈ posts . p.author=author (o p.author.equals(author))
     */
    @Override
    Post aggiungiPost(String author, String text) throws MaxCaratteriRaggiuntoException, UtenteNonTrovatoException, AutoFollowException;

    /**
     * EFFECTS: ritorna la lista contenente i Post segnalati da un utente
     */
    ArrayList<Post> getListaPostSegnalati();
}
