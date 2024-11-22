package com.SocialNetwork.Interfaces;

import com.SocialNetwork.CustomException.MaxCaratteriRaggiuntoException;

import java.sql.Timestamp;

public interface InterfacciaPost {
    /*
     * OVERVIEW: un Post è un oggetto immutable contenente un id univoco ∈ N, un autore, un testo di massimo 140 caratteri e un timestamp che rappresenta data e ora della pubblicazione
     * TE: <id, author, text, timestamp> dove author, text sono scritti dall'utente e passati come parametri al costruttore, id e timestamp sono generati automaticamente dal sistema. Uno tramite incremento progressivo, uno tramite cattura del tempo, preso in millisecondi della pubblicazione del Post.
     */
    
    /*
     *EFFECTS: Viene restituito l'id del Post, che è univoco
     */
    int getId();
    
    /*
     *EFFECTS: Viene restituito l'autore del Post
     */
    String getAuthor();
    
    /*
     *EFFECTS: Viene restituito il testo del Post
     */
    String getText();
    
    /*
     *EFFECTS: Viene restituito il tempo di pubblicazione del Post
     */
    Timestamp getTimestamp();

    /*
     *EFFECTS: Viene settato il valore dell'autore, lancia NullPointerException nel caso sia nullo
     */
    void setAuthor(String author) throws NullPointerException;

    /*
     *EFFECTS: Viene settato il valore del testo, lancia NullPointerException nel caso sia nullo e MaxCaratteriRaggiuntoException nel caso sia maggiore di 140
     */
    void setText(String text) throws NullPointerException, MaxCaratteriRaggiuntoException;
}
