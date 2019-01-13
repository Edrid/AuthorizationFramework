package structure;
import AuthorizationMechanism.Authorizer;
import AuthorizationMechanism.Clearance;
import Container.Document;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Aggregate implements Entity, Observer {
    private ArrayList<Entity> subEntities;
    private int id;
    private String name;


    public Aggregate(String name, int id){
        this.name = name;
        subEntities = new ArrayList<Entity>();
        this.id = id;
    }

    @Override
    public String getName(){
        return this.name;
    }


    @Override
    public void addChild(Entity e) {
        this.subEntities.add(e);
    }

    @Override
    public void deleteChildById(int id) {
        for(Entity entity:subEntities){
            if(id == entity.getId()){
                subEntities.remove(entity); //fixme attenzione, prono a errore? Vedi for each
            }
        }
    }

    @Override
    public Entity getChildById(int id) {
        Entity temp = null;
        for(Entity entity:subEntities){
            if(id == entity.getId()){
                temp = entity;
                break; //bruttino
            }
        }
        return temp;
    }

    @Override
    public int getLenChildren(){
        return this.subEntities.size();
    }

    @Override
    public Entity getChild(int index){
        return this.subEntities.get(index);
    }

    @Override
    public int getId(){ return this.id; }

    @Override
    public void update(Observable observable, Object o) {
        Entity.super.update(observable, o);
    }

    @Override
    public Clearance handleClearanceRequest(Document document, boolean isLast) {
        Clearance clr = Entity.super.handleClearanceRequest(document, isLast);
        //todo to be completed
        document.addObserver(this);
        return clr;
    }

}
