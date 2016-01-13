/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

import static model.Connector.insert;
import static model.Connector.insertLitteral;

/**
 *
 * @author Mélanie
 */
public class Entity {
   
    String URI;
    String name;

    
    public static void main(String args[]) {
        addEntity("person");

    }
    
    public Entity(String URI, String name) {
        this.URI = URI;
        this.name = name;
    }
    
    public static void addEntity(String json) {
        
        String myUID = null;
        if(json == "person") {
            myUID = insert("Entity", "RegOfPhysicalPerson");
        }
        
        else if (json == "object") {
            myUID = insert("Entity", "RegOfPhysicalObject");
        }
        
        // on crée le wati AFP
        
        addEntityName(myUID, "Martin Luther King");
    }
    
    public static void addEntityName(String uri, String name) {
        insertLitteral(uri, "label", name);
    }
    
    
    
}
