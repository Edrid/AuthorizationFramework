package AuthorizationMechanism;

import Container.Document;
import structure.Entity;

import java.util.Observable;
import java.util.Observer;

public class Authorizer implements Handler {
    protected Handler nextHandler = null;
    protected Entity realEntity;
    protected boolean isLast = false;

    public Authorizer(Entity realEntity, boolean isLast){
        this.realEntity = realEntity;
        this.isLast = isLast;
    }

    @Override
    public void setNextHandler(Handler handler) {
        this.nextHandler = handler;
    }

    @Override
    public void authorize(Document document) {
        //todo sends request to the correct entity (say, by email or similar)
        System.out.println("Asking for handling request...");
        Clearance clearance = realEntity.handleClearanceRequest(document, isLast);
        if(nextHandler == null){
            return;
        }
        if(clearance == Clearance.APPROVED) {
            nextHandler.authorize(document);
        }
        else if(clearance == Clearance.REJECTED){
            //todo break the chain and notify
            }
        }

    @Override
    public int getRealEntityID(){
        return realEntity.getId();
    }

}
