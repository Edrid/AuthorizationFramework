import Container.Document;
import structure.Aggregate;
import structure.Entity;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Test 1: creazione dell'organigramma, constructor constructs the tree
        Organigramma organigramma = new Organigramma();
        GestoreAutorizzazioni gestore = new GestoreAutorizzazioni(organigramma);
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(11);
        list.add(6);
        list.add(4);
        list.add(2);
        list.add(8);
        list.add(1);
        list.add(0);

        organigramma.printGerarchia();

        gestore.makeChain("FirstChain", list); //this is the output
        Document document = new Document("Edoardo");
        gestore.startAuthorizationChain("FirstChain", document);
    }
}
