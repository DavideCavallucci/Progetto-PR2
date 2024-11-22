package com.SocialNetwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        ReteSociale reteSociale = new ReteSociale();

        // Viene lanciata l'eccezione: Utente non trovato
        try {
            reteSociale.aggiungiPost("Ned Stark", "L'inverno sta arrivando.");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        // Viene aggiunto alla piattaforma l'utente Ned Stark e stampato a video
        String NedStark = reteSociale.aggiungiUtente("Ned Stark");
        System.out.println(NedStark + reteSociale.stampaConferma());

        // Viene lanciata l'eccezione: Massimo nummero di caratteri raggiunto per un Post (140)
        try {
            reteSociale.aggiungiPost("Ned Stark", "L'inverno sta arrivando.".repeat(50));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        // Ned Stark pubblica un post sulla piattaforma correttamente
        Post NedStarkPost = reteSociale.aggiungiPost(NedStark, "L'inverno sta arrivando.");
        Post NedStarkPost2 = reteSociale.aggiungiPost(NedStark, "L'inverno è arrivato.");
        System.out.println("Il post di " + NedStark + "è stato postato correttamente? " + ((NedStarkPost != null) ? "Si" : "No"));

        // Viene aggiunto alla piattaforma l'utente Jamie Lannister e stampato a video
        String JamieLannister = reteSociale.aggiungiUtente("Jamie Lannister");
        System.out.println(JamieLannister + reteSociale.stampaConferma());

        // Viene aggiunto alla piattaforma l'utente Robert Baratheon e stampato a video
        String RobertBaratheon = reteSociale.aggiungiUtente("Robert Baratheon");
        System.out.println(RobertBaratheon + reteSociale.stampaConferma());

        // Viene aggiunto alla piattaforma l'utente Loras Tyrell e stampato a video
        String LorasTyrell = reteSociale.aggiungiUtente("Loras Tyrell");
        System.out.println(LorasTyrell + reteSociale.stampaConferma());

        // Viene aggiunto alla piattaforma l'utente Oberyn Martell e stampato a video
        String OberynMartell = reteSociale.aggiungiUtente("Oberyn Martell");
        System.out.println(OberynMartell + reteSociale.stampaConferma());

        Post RobertBaratheonPost = reteSociale.aggiungiPost(RobertBaratheon, "Il trono è mio.");
        System.out.println("Il post di " + RobertBaratheon + "è stato postato correttamente? " + ((NedStarkPost != null) ? "Si" : "No"));

        Post likeNedToRobert = reteSociale.aggiungiPost(NedStark, "like:" + RobertBaratheonPost.getId());
        Post likeJamieToNed = reteSociale.aggiungiPost(JamieLannister, "like:" + NedStarkPost.getId());
        Post likeOberynToNed = reteSociale.aggiungiPost(OberynMartell, "like:" + NedStarkPost.getId());
        Post NedStarkPost3 = reteSociale.aggiungiPost(NedStark, "like:L'inverno è arrivato.");

        Post LorasTyrellPost = reteSociale.aggiungiPost(LorasTyrell, "Conquisterò approdo del re.");

        List<Post> list = new ArrayList<>();
        list.add(RobertBaratheonPost);
        list.add(NedStarkPost);
        list.add(likeNedToRobert);
        list.add(likeJamieToNed);
        list.add(likeOberynToNed);
        list.add(NedStarkPost3);
        Collections.shuffle(list);

        System.out.println("MentionedUsers: " + reteSociale.getMentionedUsers());
        System.out.println("WrittendBy, " + NedStark + " ha scritto: " + reteSociale.writtenBy(NedStark));

        ArrayList<String> words = new ArrayList<>();
        words.add("inverno");
        System.out.println("Containing, i Post con all'interno la parola inverno: " + reteSociale.containing(words));
        System.out.println("Influencers: " + reteSociale.influencers());
        System.out.println("GuessFollowers: " + reteSociale.guessFollowers(list));
        System.out.println("MentionedUsers (List<Post> ps):  " + reteSociale.getMentionedUsers(list));
        System.out.println("WrittenBy (List<Post> ps):  " + NedStark + " ha scritto: " + reteSociale.writtenBy(list, NedStark));

        ReteSocialerFiltrata reteSocialerFiltrata = new ReteSocialerFiltrata();

        String CerseiLannister = reteSocialerFiltrata.aggiungiUtente("Cersei Lannister");
        String JonSnow = reteSocialerFiltrata.aggiungiUtente("Jon Snow");

        Post CerseiLannisterPost = reteSocialerFiltrata.aggiungiPost(CerseiLannister, "Cazzo sono indignata.");
        reteSocialerFiltrata.aggiungiPost(JonSnow, "segnala:" + CerseiLannisterPost.getId());

        System.out.println("Post segnalati: " + reteSocialerFiltrata.getListaPostSegnalati());
    }
}
