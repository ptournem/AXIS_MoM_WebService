/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Dialog.Entity;
import Dialog.Property;
import model.Oeuvre;
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

	if (e == null) {
	    return new Entity();
	}
	e.setURI(insert("rdf:type", "axis:Entity"));
	String uri = null;
	switch (e.getType()) {
	    case "person":
		uri = insert("rdf:type", "axis:RegOfPhysicalPerson");
		insert(e.getURI(), "axis:hasExpression", uri);
		break;
	    case "event":
		uri = insert("rdf:type", "axis:RegOfEvent");
		insert(e.getURI(), "axis:hasExpression", uri);
		break;
	    case "object":
		uri = insert("rdf:type", "axis:RegOfPhysicalObject");
		insert(e.getURI(), "axis:hasExpression", uri);
		break;
	    case "location":
		uri = insert("rdf:type", "axis:RegOfPlace");
		insert(e.getURI(), "axis:hasExpression", uri);
		break;
//              case "activity":
//                  uri = insert("rdf:type", "axis:RegOfPhysicalPerson");
//                  insert(e.URI, "axis:hasExpression", uri);
//                  break;
	    case "organisation":
		uri = insert("rdf:type", "axis:RegOfMoralPerson");
		insert(e.getURI(), "axis:hasExpression", uri);
		break;
	    default:
		throw new AssertionError();
	}
        
        e.insertName(new Property("name", e.getName(), "fr", null));
        e.insertImage(new Property("image", e.getImage(), "fr", null));
	//insert(e.getURI(), "rdfs:label", e.getName(), "fr");
	return e;
    }

    @WebMethod(operationName = "SetEntityTextProperty")
    public boolean SetEntityProperty(Entity e, Property p) {

	//insert(e.getURI(), p.getPropURI(), p.getValue(), "fr");
        
        Oeuvre oeuv = null;
        Person pers = null;
        
        switch (p.getName()) {
	    case "author":
                oeuv = (Oeuvre) e;
                oeuv.insertAuthor(p);
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

    @WebMethod(operationName = "SearchEntitiesFromText")
    public String SearchEntitiesFromText() {
	return "SearchEntitiesFromText";
    }

}
