package com.SocialNetwork;

import com.SocialNetwork.CustomException.MaxCaratteriRaggiuntoException;
import com.SocialNetwork.Interfaces.InterfacciaPost;
import java.sql.Timestamp;

public final class Post implements InterfacciaPost {
    private int id;
    private String author;
    private String text;
    private Timestamp timestamp;
    private int lunghezzaMassimaText = 140;

    /*
     * REQUIRES: id ≠ null ∧ author ≠ null ∧ text ≠ null ∧ text.length < 140
     * MODIFIES:this
     *
     * THROWS:
     * MaxCaratteriRaggiuntoException: se text.length() > lunghezzaMassimaText
     * NullPointerException: (unchecked exception) se id = null V author = null V text = null
     */
    public Post(int id, String author, String text) throws MaxCaratteriRaggiuntoException {
        this.id = id;
        this.setAuthor(author);
        this.setText(text);
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public void setAuthor(String author) throws NullPointerException {
        if(author == null)
            throw new NullPointerException("L'autore non può essere vuoto.");
        this.author = author;
    }

    @Override
    public void setText(String text) throws NullPointerException, MaxCaratteriRaggiuntoException {
        if(text == null) throw new NullPointerException("Il testo non può essere vuoto.");
        if(text.length() > lunghezzaMassimaText)
            throw new MaxCaratteriRaggiuntoException(lunghezzaMassimaText);
        this.text = text;
    }
}
