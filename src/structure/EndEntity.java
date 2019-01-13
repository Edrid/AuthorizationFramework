package structure;
import AuthorizationMechanism.Authorizer;
import AuthorizationMechanism.Clearance;
import Container.Document;

import java.util.Observable;
import java.util.Observer;

//"End" as in limit case
//Note: though authorizer is an abstract class it's not
public class EndEntity implements Entity, Observer {
    private int id;
    private String name;

    public EndEntity(String name, int id){
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName(){ return this.name; }

    @Override
    public int getId(){ return this.id; }
    //the following is ugly but necessary


    @Override
    public void addChild(Entity e) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void deleteChildById(int id) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Entity getChildById(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Entity getChild(int index){
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLenChildren(){
        throw new UnsupportedOperationException();
    }



    @Override
    public Clearance handleClearanceRequest(Document document, boolean isLast) {
        Clearance clr = Entity.super.handleClearanceRequest(document, isLast);
        //todo to be completed
        document.addObserver(this);
        return clr;
    }

    @Override
    public void update(Observable observable, Object o) {
        Entity.super.update(observable, o);
    }
}