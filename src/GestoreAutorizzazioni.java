import AuthorizationMechanism.Handler;
import Container.Document;
import structure.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GestoreAutorizzazioni {

    Organigramma organigramma;

    public GestoreAutorizzazioni(Organigramma organigramma){
        this.organigramma = organigramma;
    }

    class ChainHash{
        private String name;
        Handler headOfChain;
        public ChainHash(String name, Handler head){
            this.name = name;
            this.headOfChain = head;
        }

        public String getName() { return name; }
        public Handler getHead(){ return this.headOfChain; }
    }

    ArrayList<ChainHash> chains = new ArrayList<ChainHash>();

    //Creates and saves a chain of responsabilities, given id of reparto
    //Though this SEEMS redundant it's not, creating a chain allows for flexibility in stopping the approbation chain
    public void makeChain(String name, ArrayList<Integer> sequence) {
        //e.g. 11,8,4,1
        Collections.reverse(sequence);
        Handler temp;
        Handler prev = organigramma.findEntity(sequence.get(0)).getChainBlock(true); //fixme

        for (int i = 1; i < sequence.size(); i++) {
            System.out.println("Added: " + prev.getRealEntityID());

            temp = organigramma.findEntity(sequence.get(i)).getChainBlock(false);
            if (temp == null) {
                throw new NullPointerException();
            }
            temp.setNextHandler(prev);
            prev = temp;
        }
        System.out.println("Added: " + prev.getRealEntityID());
        ChainHash newChain = new ChainHash(name, prev);

        chains.add(newChain);
    }


    //todo: allow specification of a final thingy
    public void startAuthorizationChain(String name, Document document){
        for(ChainHash chain:this.chains){
            if(chain.getName().compareTo(name) == 0){
                chain.getHead().authorize(document);
            }
        }
    }

}

