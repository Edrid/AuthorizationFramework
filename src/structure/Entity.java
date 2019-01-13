package structure;

import AuthorizationMechanism.Authorizer;
import AuthorizationMechanism.Clearance;
import AuthorizationMechanism.Handler;
import Container.Document;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
//todo maybe every entity should have a pointer to a log to all documents?
//this is a component of the composite design pattern

public interface Entity {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public int getId(); //Forces all subclasses to have an id
    public String getName();
    public void addChild(Entity e);
    public void deleteChildById(int id);
    public Entity getChildById(int id);
    public Entity getChild(int index);
    public int getLenChildren();
    public default Clearance handleClearanceRequest(Document document, boolean isLast){ //todo: add document to method signature
        Clearance clr = Clearance.PENDING;
        Scanner in = new Scanner(System.in);
        System.out.println("You are " + this.getName() + "\nApprove thingy? y/n");
        char ans = in.next().charAt(0);
        switch (ans){
            case 'y':
                clr = Clearance.APPROVED;
                document.addAuthorization(this.getName(), true, isLast); //FIXME to be managed the last block thingy
                break;
            case 'n':
                clr = Clearance.REJECTED;
                document.addAuthorization(this.getName(), false, isLast);
                break;
        }
        return clr;
    }

    public default void update(Observable observable, Object o) {
        //todo maybe make log
        //note: the clearance handling has been implemented in the interface by the default method
        System.out.println(ANSI_YELLOW + "Notification for: " + this.getName() + ANSI_RESET);
        System.out.println("Notification for pending document: here is the log \n" + ((Document) observable).getAuthorizationsList());
    }

    public default Handler getChainBlock(Boolean isLast){
        return new Authorizer(this, isLast);
    }
}
