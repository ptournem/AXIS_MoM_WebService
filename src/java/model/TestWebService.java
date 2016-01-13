/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import Dialog.Entity;
import Dialog.PropertyObject;
import Dialog.PropertyText;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import static model.Connector.*;

/**
 *
 * @author APP-Riad.Belmahi
 */
@WebService(serviceName = "TestWebService")
public class TestWebService {

    /**
     * le webservice fait appel aux méthodes suivantes :
     * 
        AddEntity Crée une nouvelle entité
        RemoveEntity Supprime une entité
        SetEntityTextProperty Défini la propriété d’une entité en plain text
        SetEntityObjectProperty Défini  la propriété d’une entité avec une autre entité
        RemoveEntityObjectProperty Supprime une propriété d’une entité
        RemoveEntityObjectPropertyWithObject Supprime une propriété d’une entité et l’objet lui correspondant
        LoadEntityProperties  Charge toutes les propriétés d’une entité
        AddComment  Ajoute un commentaire sur une entité
        GrantComment Valide un commentaire 
        RemoveComment Refuse et supprime un commentaire 
        LoadComment Renvoie les commentaires d’une entité
        SearchEntitiesFromText Permet de rechercher des entités relatives à un texte donné

     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    
      @WebMethod(operationName = "AddEntity")
    public Entity AddEntity(Entity e) {
        
        
        if(e.type == "person") {
            e.URI = insert("Entity", "RegOfPhysicalPerson");
        }
        
        else {
            e.URI = insert("Entity", "RegOfPhysicalObject");
        }
        
        // on crée le wati AFP
        insertLitteral(e.URI, "label", e.name);
        return e;
    }
    
    
          @WebMethod(operationName = "SetEntityTextProperty")
    public boolean SetEntityTextProperty(Entity e, PropertyText p) {
        
        insertLitteral(e.URI, p.propURI, p.value);
        return true;
    }
    
    
          @WebMethod(operationName = "RemoveEntity")
    public String RemoveEntity() {
        return "RemoveEntity";
    }
    
          @WebMethod(operationName = "SetEntityObjectProperty")
    public boolean SetEntityObjectProperty(Entity  e, PropertyObject p) {
        insert(e.URI, p.propURI, p.ObjectURI);
        return true;
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
    
          @WebMethod(operationName = "SearchEntitiesFromText")
    public String SearchEntitiesFromText() {
        return "SearchEntitiesFromText";
    }
    
}
