package Container;

import java.util.ArrayList;
import java.util.Observable;

//is subject, implicitly (of the GoF observer design pattern)
public class Document extends Observable {
    class Authorization{
        private String issuedBy = null;
        private boolean authorized = false;
        //Defensive copies are NOT necessary: strings are immutable
        public Authorization(String author, boolean authorized){
            this.issuedBy = author;
            this.authorized = authorized;
        }

        public String getAuthor(){
            return this.issuedBy;
        }
    }
    private String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean molestie faucibus odio, a blandit erat dignissim eu. Sed a erat efficitur purus blandit pulvinar. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris a mauris sed enim volutpat pharetra. Vivamus tempus convallis nisl quis scelerisque. Etiam porta, leo sed iaculis tincidunt, lorem ex suscipit lectus, sit amet porttitor risus urna rutrum erat. Nam nec nulla tortor. Integer vel mi gravida, imperdiet leo non, venenatis tellus. Vivamus ac mi nulla. Etiam consequat odio eu ipsum fringilla, nec ullamcorper enim feugiat. Suspendisse porta nulla at dui blandit, et porttitor arcu imperdiet. Donec.";
    private ArrayList<Authorization> authorizations = new ArrayList<Authorization>();
    private String author = "";

    public Document(String author, String content){
        this.author = author;
        this.content = content;
    }

    public Document(String author){
        this.author = author;
    }


    public String getAuthorizationsList(){
        String auth = "";
        for(Authorization authorization:authorizations){
            auth = auth + authorization.getAuthor() + "\t clearance: " + authorization.authorized;
            auth = auth + "\n";
        }
        return auth;
    }

    public void printAuthorizationsList(){
        System.out.println(this.getAuthorizationsList());
    }

    public void addAuthorization(String author, boolean authorized, boolean lastBlock){
        Authorization autorizzazione = new Authorization(author, authorized);
        this.authorizations.add(autorizzazione);
        setChanged(); //todo to be finished observer
        if(!authorized || lastBlock){
            notifyObservers(this.getAuthorizationsList()); //I pass the list for specifying details
        }
    }
}
