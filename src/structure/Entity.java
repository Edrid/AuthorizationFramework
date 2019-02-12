package structure;

import AuthorizationMechanism.Authorizer;
import AuthorizationMechanism.Clearance;
import AuthorizationMechanism.Handler;
import Container.Document;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
//todo maybe every entity should have a pointer to a log to all documents?
//this is a component of the composite design pattern

public abstract class Entity implements Observer{
    protected int id;
    protected String name;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public abstract int getId(); //Forces all subclasses to have an id
    public abstract String getName();
    public abstract boolean isEnd();
    public abstract void addChild(Entity e);
    public abstract void deleteChildById(int id);
    public abstract Entity getChildById(int id);
    public abstract Entity getChild(int index);
    public abstract int getLenChildren();
    public abstract ArrayList<Entity> getChildren_safe();

    public Clearance handleClearanceRequest(Document document, boolean isLast){
        Clearance clr = Clearance.PENDING;
        Scanner in = new Scanner(System.in);
        System.out.println("You are " + this.getName() + "\nApprove document ? y/n");
        char ans;
        do {
            ans = in.next().charAt(0);
        } while(!(ans == 'y' || ans == 'n'));

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

    public void update(Observable observable, Object o) {
        //todo maybe make log
        //note: the clearance handling has been implemented in the interface by the default method
        System.out.println(ANSI_YELLOW + "Notification for: " + this.getName() + ANSI_RESET);
        System.out.println("Notification for pending document: here is the log \n" + ((Document) observable).getAuthorizationsList());
    }

    public Handler getChainBlock(Boolean isLast){
        return new Authorizer(this, isLast);
    }
}
