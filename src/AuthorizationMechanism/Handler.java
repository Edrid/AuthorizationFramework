package AuthorizationMechanism; //CoR

import Container.Document;

public interface Handler {
    public void setNextHandler(Handler handler);
    public void authorize(Document document);
    public int getRealEntityID();
}
