package com.SocialNetwork;

import com.SocialNetwork.CustomException.*;
import com.SocialNetwork.Interfaces.InterfacciaSocial;
import java.util.*;
import java.util.stream.Collectors;

public class ReteSociale implements InterfacciaSocial {
    //Mappa contenente come primo argomento gli utenti registrati sulla piattaforma e come secondo un Set con i propri seguiti
    private Map<String, Set<String>> reteSociale;
    //HashMap contenente come primo argomento gli utenti registrati sulla piattaforma e come secondo un intero, il numnero di follower
    private Map<String, Integer> influencers;
    //HashMap contenente come primo argomento l'id del posto approvato e come secondo l'oggetto Post approvato
    private HashMap<Integer, Post> postApprovati;
    //Set contenente gli utenti che hanno postato sulla piattaforma almeno una volta
    private Set<String> utentiMenzionati;

    public ReteSociale() {
        reteSociale = new HashMap<>();
        influencers = new HashMap<>();
        postApprovati = new HashMap<>();
        utentiMenzionati = new HashSet<>();
    }

    public int generatoreUnivocoID() {
        UUID identificativo = UUID.randomUUID();
        String str = "" + identificativo;
        int univocoID = str.hashCode();
        String filterStr = "" + univocoID;
        str = filterStr.replaceAll("-", "");
        return Integer.parseInt(str);
    }

    @Override
    public String aggiungiUtente(String username) throws UtenteGiaPresenteException {
        if (reteSociale.containsKey(username)) {
            throw new UtenteGiaPresenteException(username);
        }
        reteSociale.put(username, new HashSet<>());
        influencers.put(username, 0);
        return username;
    }

    @Override
    public Post aggiungiPost(String author, String text) throws UtenteNonTrovatoException, AutoFollowException, MaxCaratteriRaggiuntoException {
        if (!reteSociale.containsKey(author))
            throw new UtenteNonTrovatoException(author);
        if (text.startsWith("like:")) {
            String[] splittedText = text.split(":");
            try {
                int idPost = Integer.parseInt(splittedText[1].trim());
                Post post = postApprovati.get(idPost);
                if (post != null) {
                    if (post.getAuthor().equals(author))
                        throw new AutoFollowException();
                    reteSociale.get(author).add(post.getAuthor());
                    influencers.put(post.getAuthor(), influencers.get(post.getAuthor()+1));
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {}
        }
        Post nuovoPost = new Post(generatoreUnivocoID(), author, text);
        postApprovati.put(generatoreUnivocoID(), nuovoPost);
        utentiMenzionati.add(author);
        return nuovoPost;
    }

    @Override
    public Map<String, Set<String>> guessFollowers(List<Post> ps) throws AutoFollowException, PostGiaPresenteException {
        Map<String, Set<String>> reteSociale = new HashMap<>();
        HashMap<Integer,Post> mappaDiPost = new HashMap<>();
        HashMap<Integer,Post> mappaDiLike = new HashMap<>();

        for (Post post : ps){
            if(mappaDiPost.containsKey(post.getId()) || mappaDiLike.containsKey(post.getId()))
                throw new PostGiaPresenteException(post.getId());
            reteSociale.put(post.getAuthor(), new HashSet<>());

            if(post.getText().contains("like:")){
                mappaDiLike.put(post.getId(),post);
            }else {
                mappaDiPost.put(post.getId(),post);
            }
        }
        for (Post like : mappaDiLike.values()){
            String[] separaTesto = like.getText().split(":");
            try {
                int postID = Integer.parseInt(separaTesto[1].trim());
                Post postPiaciuto = mappaDiPost.get(postID);

                if (postPiaciuto != null && like.getTimestamp().after(postPiaciuto.getTimestamp())) {
                    if (postPiaciuto.getAuthor().equals(like.getAuthor()))
                        throw new AutoFollowException();
                    reteSociale.get(postPiaciuto.getAuthor()).add(like.getAuthor());
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {}
        }
        return reteSociale;
    }

    @Override
        public List<String> influencers() {
        List<Map.Entry<String, Integer>> listaUtentiPiuInfluentiOrdinata = new ArrayList<>(influencers.entrySet());
        listaUtentiPiuInfluentiOrdinata.sort(Map.Entry.comparingByValue());
        List<String> listaOrdinataRovesciata = new ArrayList<>();
        for (int i = 0; i < listaUtentiPiuInfluentiOrdinata.size(); i++) {
            listaOrdinataRovesciata.add(listaUtentiPiuInfluentiOrdinata.get(i).getKey());
        }
        return listaOrdinataRovesciata;
        }

    @Override
    public Set<String> getMentionedUsers() {
        return utentiMenzionati;
    }

    @Override
    public Set<String> getMentionedUsers(List<Post> ps) {
        Set<String> listaUtentiMenzionati = new HashSet<>();
        for (Post post : ps) {
            listaUtentiMenzionati.add(post.getAuthor());
        }
        return listaUtentiMenzionati;
    }

    @Override
    public List<Post> writtenBy(String username) {
        List<Post> listaPostScrittiDa = new ArrayList<>();
        for (Post post : postApprovati.values()) {
            if (post.getAuthor().equals(username)) {
                listaPostScrittiDa.add(post);
            }
        }
        return listaPostScrittiDa;
    }

    @Override
    public List<Post> writtenBy(List<Post> ps, String username) {
        List<Post> listPostScrittiDa = new ArrayList<>();
        for (Post post : ps) {
            if (post.getAuthor().equals(username)) {
                listPostScrittiDa.add(post);
            }
        }
        return listPostScrittiDa;
    }

    @Override
    public List<Post> containing(List<String> words) {
        List<Post> listaMatch = new ArrayList<>();
        for (Post post : postApprovati.values()) {
            for (String word : words) {
                if (post.getText().contains(word)) {
                    listaMatch.add(post);
                    break;
                }
            }
        }
        return listaMatch;
    }

    public String stampaConferma() {
        String conferma = " si è aggiunto alla piattaforma.";
        return conferma;
    }

    protected HashMap<Integer, Post> getPostApprovati() {
        return postApprovati;
    }
}