import structure.Aggregate;
import structure.EndEntity;
import structure.Entity;

import java.util.ArrayList;
/*  Struttura organigramma:
*   0. CEO:
*       1. CMO
*       2. CTO
*           11. Reparto tecnico
*               7. Reparto progetti
*                   9. O-Team1
*                   10. O-Team2
*               4. R&D
 *                  8. S-Team1
*       3. Budjet office
*           5. SpecialBudget
*           6. OrdinaryBudjet
*
*
* */
//todo can be implemented as singleton AND may be not necessary
public class Organigramma {
    private Entity ceo;
    private ArrayList<Entity> organico = new ArrayList<Entity>();

    public Organigramma(){
        this.ceo = new Aggregate("CEO", 0);

        //The following is kinda ugly but not too much
        organico.add(ceo);

        organico.add(new EndEntity("CMO", 1));
        organico.add(new Aggregate("CTO", 2));
        organico.add(new Aggregate("BudjetOffice", 3));
        organico.add(new Aggregate("R&D", 4));
        organico.add(new EndEntity("SpecialBudget", 5));
        organico.add(new EndEntity("OrdinaryBudget", 6));
        organico.add(new Aggregate("RepartoProgetti", 7));
        organico.add(new EndEntity("S-Team1", 8));
        organico.add(new EndEntity("O-Team1", 9));
        organico.add(new EndEntity("O-team2", 10));
        organico.add(new Aggregate("Reparto tecnico", 11));

        //define ceo children
        ceo.addChild(organico.get(1));  //cmo
        ceo.addChild(organico.get(2));  //cto
        ceo.addChild(organico.get(3));  //Budget

        //define budget office children
        organico.get(3).addChild(organico.get(5));  //special budget
        organico.get(3).addChild(organico.get(6));  //ordinary budget

        //define cto children
        organico.get(2).addChild(organico.get(11));

        //define reparto tecnico children
        organico.get(11).addChild(organico.get(4)); //r&d
        organico.get(11).addChild(organico.get(7)); //reparto progetti

        //define R&D child
        organico.get(4).addChild(organico.get(8));

        //define reparto progetti children
        organico.get(7).addChild(organico.get(10));
        organico.get(7).addChild(organico.get(9));

    }

    public void printGerarchia(){
        ArrayList<Entity> e = new ArrayList<Entity>();
        e.add(ceo);
        printGerarchia_rec(e, "");

    }

    private void printGerarchia_rec(ArrayList<Entity> entities, String tabs){
        if(entities == null){
            return;
        }
        String newTabs = tabs + "\t";
        ArrayList<Entity> entita = null;

        for(Entity ent:entities){
            System.out.println(tabs + ent.getName() + ", ID: " + ent.getId());
            if(!ent.isEnd())
                entita = new ArrayList<Entity>(ent.getChildren_safe());
            //entita.add(ent);
            this.printGerarchia_rec(entita, newTabs);
        }

    }


    public Entity findEntity(int id){
        for(Entity e:this.organico){
            if(e.getId() == id){
                return e;
            }
        }
        return null;
    }


    public Entity getCeo(){ return ceo; }
}