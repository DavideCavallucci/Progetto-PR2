package com.SocialNetwork;

import com.SocialNetwork.CustomException.AutoFollowException;
import com.SocialNetwork.CustomException.MaxCaratteriRaggiuntoException;
import com.SocialNetwork.CustomException.UtenteNonTrovatoException;
import com.SocialNetwork.Interfaces.InterfacciaFiltro;

import java.util.ArrayList;
/*
    OVERVIEW: Collezione mutabile di Post, segnalati inserendo la String "rep: "+ id ∈ N nel campo text del metodo post,
    ereditato della superclasse SocialNetwork.
    AF: a(listaPostSegnalati) = (listaPostSegnalati[0,listaPostSegnalati.size]->(Post)=
        { p1 | p1 ∈ posts.values ∧ (∃p2 ∈ posts.values | p2.getText.contains("rep: "+ p1.getId)) }
 */
public class ReteSocialerFiltrata extends ReteSociale implements InterfacciaFiltro {
    ArrayList<Post> listaPostSegnalati = new ArrayList<>();

    public ReteSocialerFiltrata() {}

    @Override
    public Post aggiungiPost(String author, String text) throws MaxCaratteriRaggiuntoException, UtenteNonTrovatoException, AutoFollowException {
        Post post = super.aggiungiPost(author, text);
        if(text.startsWith("segnala:")){
                Post postSegnalato = new Post (generatoreUnivocoID(), author, text);
                listaPostSegnalati.add(postSegnalato);
        }
        return post;
    }
    @Override
    public ArrayList<Post> getListaPostSegnalati() {
        return listaPostSegnalati;
    }
}
