package com.SocialNetwork.Interfaces;

import com.SocialNetwork.CustomException.*;
import com.SocialNetwork.Post;

import java.util.List;
import java.util.Map;
import java.util.Set;

/*	OVERVIEW: Collezione mutabile di <Post, utenti> dove gli utenti possono seguire altri utenti che hanno
    pubblicato almeno una volta. Seguire un altro utente significa pubblicare un Post contenente la
    String "like:"+id ∈ N del post a cui vogliamo mettere like.

    Typical Element: <Post,followers> dove l'insieme posts contiene i Post e social contiene gli utenti con i relativi followers

    Rep Invariant:
 *  social != null ∧ social.size >= 0 ∧
    ∀x ∈ social.keySet() => x != null ∧ x.length > 0 ∧
    ∀y ∈ social.values() => y != null ∧ y.size >= 0 ∧
    ∀z ∈ y => z != null ∧ z.length > 0

 *  influencers != null ∧ influencers.size >= 0 ∧
    ∀x ∈ influencers.keySet() => x != null ∧ x.length > 0 ∧
    ∀y ∈ influencers.values() => y != null ∧ y.size >= 0

 *  posts != null ∧ posts.size >= 0 ∧
    ∀x ∈ posts.keySet() => x != null ∧ x >= 0 ∧
    ∀y ∈ posts.values() => y != null
 
 *  mentionedusers != null ∧ mentionedusers.size >= 0 ∧
    ∀x ∈ mentionedusers => x != null ∧ x.length > 0
 */

public interface InterfacciaSocial {

    /*
     * REQUIRES: username ≠ null ∧ username.length > 0
     * MODIFIES:this
     * EFFECTS: aggiunge alla Mappa social l'utente come chiave e un Set vuoto come value. Questo metodo equivale alla registrazione dell'utente sulla piattaforma.
     *
     * THROWS:
     * UtenteGiaPresenteException: se ∃x ∈ social.keySet() ∧ y ∈ social.keySet() . x = y
     * NullPointerException: (unchecked exception) se username = null
     */
    String aggiungiUtente(String username) throws UtenteGiaPresenteException;

    /*
     * REQUIRES: author ≠ null ∧ text ≠ null ∧ author.length > 0 ∧ text.length > 0
     * MODIFIES:this
     * EFFECTS: Crea un Post p1 utilizzando author e text come parametri del costruttore e
     * assegna un intero come id, il quale verrà dopo incrementato.
     * Più formalmente: definito posts come l'insieme dei post, mentionedUsers come
     * l insieme degli autori di questi post, social come la funzione che associa ad ogni utente i relativi followers,
     * influencers come la funzione che associa ad ogni utente, il numero di followers, p1 ∈ Post come il post appena
     * creato e p2 ∈ Post come il Post a cui viene messo il like:
     * posts.put(p1) ∧ metionedUsers.add(p1.author) ∧ social.get(p2.author).add(p1.author)
     * ∧ influencers.put(p2.author, social.get(p2.author).size()).
     * Definisco un like, come:
     * ∃p1,p2 ∈ posts . p1.author=a ∧ p2.author=b ∧ a≠b ∧ p1.id=x ∈ N ∧ p2.text.startsWith("like:" + x) -> f(a,b)
     * f:(u x u)->(u x followers)
     * dato a ⊆ u ∧ b ⊆ u
     * f(a,b) = ab
     *
     * THROWS:
     *  UtenteNonTrovatoException: se author ∉ social
     * MaxCaratteriRaggiuntoException: se text > 140
     *  NullPointerException: (unchecked exception) se author = null V text = null
     *  AutoFollowException: ∃p1, p2 ∈ posts . p1.author=a ∧ p2.author = b ∧ a = b ∧ p1.id = x ∈ N ∧ p2.contains("like:" + x)
     */
    Post aggiungiPost(String author, String text) throws UtenteNonTrovatoException, MaxCaratteriRaggiuntoException, AutoFollowException;

    /*
     * REQUIRES: ps ≠ null ∧ ∀p ∈ ps . p ≠ null ∧ p instanceof Post)
     * EFFECTS: restituisce una mappa chiave-valore, Map<Key, Value> contenente come Key gli autori del Post e come Value i suoi followers.
     * Un utente a segue un utente b, se esiste un post di a tale per cui il suo post contiene la dicitura "like:"+ un intero x.
     * e un post di b, il cui id è quello stesso numero x.
     * Formalmente:
     * ∃post1,post2 ∈ ps . post1.author = a ∧ post2.author = b ∧ a≠b ∧ post1.id = x ∈ N ∧ post2.contains("like:" + x) -> f(a, b)
     *     f:(u x u) -> (u x followers)
     *     dato a ⊆ u ∧ b ⊆ u
     *     f(a,b) = ab
     *
     * THROWS:
     * PostGiaPresenteException: se ∃x ∈ ps ∧ y ∈ ps . x.getId() = y.getId()
     * AutoFollowException: ∃p1,p2 ∈ ps . p1.author=a ∧ p2.author=b ∧ a=b ∧ p1.id=x ∈ N ∧ p2.contains("like:" + x)
     * NullPointerException: (unchecked exception) se ps = null V ∃p ∈ ps . p=null
     */
    Map<String, Set<String>> guessFollowers(List<Post> ps) throws PostGiaPresenteException, AutoFollowException;

    /*
     * EFFECTS: Restituisce una lista contenente gli utenti della rete, ordinati in modo decrescente per numero di Followers.
     */
    List<String> influencers();

    /*
     * EFFECTS: Restituisce un Set contenenti gli utenti che hanno postato almeno una volta nella rete sociale.
     */
    Set<String> getMentionedUsers();

    /*
     * REQUIRES: ps ≠ null ∧ ∀p ∈ ps . p ≠ null
     * EFFECTS: Restituisce un Set contenente gli autori dei post all'interno della lista ps.
     * Più formalmente: ∀p ∈ ps -> p.author
     * 
     * THROWS:
     * NullPointerException: (unchecked exception) se ps = null V ∃p ∈ ps . p=null
     */
    Set<String> getMentionedUsers(List<Post> ps);

    /*
     * REQUIRES: username ≠ null ∧ username.length > 0 ∧ username.trim().length > 0
     * EFFECTS: Restituisce una lista di Post scritti dall'autore passato come parametro, username.
     * Formalmente: ∀p ∈ posts ∧ p.getAuthor = username
     * 
     * THROWS:
     * NullPointerException: se username=null (unchecked exception)
     */
    List<Post> writtenBy(String username);

    /*
     * REQUIRES: ps ≠ null ∧ (∀p . p ∈ ps ∧ p ≠ null) username ≠ null ∧ username.length > 0 ∧ username.trim().length > 0
     * EFFECTS: Restituisce gli autori di tutti i post contenuti nella List<Post> ps, passata come parametro.
     * Formalmente: ∀p ∈ ps ∧ p.getAuthor = username
     *
     * THROWS:
     * NullPointerException: (unchecked exception) se ps = null V ∃p ∈ ps . p=null v username = null
     */
    List<Post> writtenBy(List<Post> ps, String username);

    /*
     * REQUIRES: words ≠ null ∧ (∀w . w ∈ words ∧ w ≠ null)
     * EFFECTS: Restituisce l'insieme dei Post che contengono al loro interno una delle String contenute in words.
     * Formalmente: ∀p ∈ posts (∃w . w ∈ words ∧ p.getText.contains(w))
     *
     * THROWS:
     * NullPointerException: (unchecked exception) se words = null V ∃w ∈ words . w=null
     */
    List<Post> containing(List<String> words);
}
