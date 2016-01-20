/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Dialog.Entity;
import Dialog.Property;
import control.semantics;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import static model.Connector.*;

/**
 *
 * SAVE NE PAS MODIFIER
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
@WebService(serviceName = "TestWebService")
public class SAVETestWebService {

    /**
     * le webservice fait appel aux méthodes suivantes :
     *
     * AddEntity Crée une nouvelle entité RemoveEntity Supprime une entité
     * SetEntityTextProperty Défini la propriété d’une entité en plain text
     * SetEntityObjectProperty Défini la propriété d’une entité avec une autre
     * entité RemoveEntityObjectProperty Supprime une propriété d’une entité
     * RemoveEntityObjectPropertyWithObject Supprime une propriété d’une entité
     * et l’objet lui correspondant LoadEntityProperties Charge toutes les
     * propriétés d’une entité AddComment Ajoute un commentaire sur une entité
     * GrantComment Valide un commentaire RemoveComment Refuse et supprime un
     * commentaire LoadComment Renvoie les commentaires d’une entité
     * SearchEntitiesFromText Permet de rechercher des entités relatives à un
     * texte donné
     *
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
	return "Hello " + txt + " !";
    }

    @WebMethod(operationName = "AddEntity")
    public Entity AddEntity(@WebParam(name = "e") Entity e) {

        return e.AddEntity();

    }

    @WebMethod(operationName = "SetEntityTextProperty")
    public boolean SetEntityProperty(@WebParam(name = "e") Entity e, @WebParam(name = "p") Property p) {

	//insert(e.getURI(), p.getPropURI(), p.getValue(), "fr");
        
        Object obj = null;
        Person pers = null;
        
        switch (p.getName()) {
	    case "author":
                obj = (Object) e;
                obj.insertAuthor(p);
                break;
            case "image":
                e.insertImage(p);
                break;
            case "name":
                e.insertName(p);
                break;
            case "birthdate":
                pers = (Person) e;
                pers.insertBirthDate(p);
                break;
            case "deathdate":
                pers = (Person) e;
                pers.insertDeathDate(p);
                break;
                
        }
        
	return true;
    }

    @WebMethod(operationName = "RemoveEntity")
    public String RemoveEntity() {
	return "RemoveEntity";
    }

    @WebMethod(operationName = "AddComment")
    public String AddComment() {
	return "AddComment";
    }

    @WebMethod(operationName = "RemoveComment")
    public String RemoveComment() {
	return "RemoveComment";
    }

    @WebMethod(operationName = "GrantComment")
    public String GrantComment() {
	return "GrantComment";
    }

    @WebMethod(operationName = "LoadComment")
    public String LoadComment() {
	return "LoadComment";
    }

    @WebMethod(operationName = "SearchOurEntitiesFromText")
    public Entity[] SearchOurEntitiesFromText(@WebParam(name = "needle") String needle) {
        
        String [] tabEntities = selectAllEntitiesURI();
        ArrayList<Entity> tab = new ArrayList<Entity>();
        
        for(int i =0; i<tabEntities.length; i++) {
            Entity e = new Entity();
            e.setURI(tabEntities[i]);
            e.constructEntity();
            if(e.getName().contains(needle))
                tab.add(e);
        }
        
        Entity[] ret = new Entity[tab.size()];
	return (Entity[]) tab.toArray(ret);
    }
    
    @WebMethod(operationName = "GetEntity")
    public Entity GetEntity(@WebParam(name = "e") Entity e) {

        e.constructEntity();        
        return e;
        
    }
    
    @WebMethod(operationName = "LoadEntityProperties")
    Property[] LoadEntityProperties(@WebParam(name = "e") Entity e) {
        semantics ctrl = new semantics();
        Property[] tab = ctrl.getAllPropertiesFromEntity(e);
        return tab;
    }

}
