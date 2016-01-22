/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Dialog.Entity;
import Dialog.Property;
import Dialog.PropertyAdmin;
import java.util.ArrayList;
import static model.Connector.*;
import model.Event;
import model.Person;
import model.Object;
import model.Place;
import org.apache.jena.rdf.model.Model;



/**
 *
 * @author loannguyen
 */
public class semantics {
    
    
    public static void main(String args[]) {
       // Model m2;
       // m2 = SearchOurEntitiesFromText("MLK_speech.bwf");
         Entity e = new Entity("http://dbpedia.org/resource/Racine", null, null, null);
         LoadEntityProperties(e);
     //   SearchAllEntitiesFromText("<http://dbpedia.org/resource/The_Thinker>");
        System.out.println("main");
    }
    
    public Property[] getAllPropertiesFromEntity(Entity e) {
        
        
        
        //en fonction du type, on fait une boucle pour récuperer toutes les infos de l'oeuvre en interne + lod
        Property[] tab = null;
        String type = e.getType();
        
        switch (type) {
	    case "person":
                Person p = new Person();
                p.setURI(e.getURI());
                p.constructEntity();
                p.constructPerson();
                tab = p.getPropertiesPerson();
		break;
	    case "event":
		Event ev = new Event();
                ev.setURI(e.getURI());
                ev.constructEntity();
                ev.constructEvent();
                tab = ev.getPropertiesEvent();
		break;
	    case "object":
                Object o = new Object();
                o.setURI(e.getURI());
                o.constructEntity();
                o.constructObject();
                tab = o.getPropertiesObject();
		break;
            case "location":
                Place pl = new Place();
                pl.setURI(e.getURI());
                pl.constructEntity();
                pl.constructPlace();
                tab = pl.getPropertiesPlace();
		break;
	    default:
		throw new AssertionError();
	}
        
        
        
        return tab;
    }
    
    public PropertyAdmin[] getAllPropertiesAdminFromEntity(Entity e) {
        
        //en fonction du type, on fait une boucle pour récuperer toutes les infos de l'oeuvre en interne + lod
        PropertyAdmin[] tab = null;
        String type = e.getType();
        
        switch (type) {
	    case "person":
                Person p = new Person();
                p.setURI(e.getURI());
                p.constructEntity();
                p.constructPerson();
                tab = p.getPropertiesAdminPerson();
		break;
	    case "event":
		Event ev = new Event();
                ev.setURI(e.getURI());
                ev.constructEntity();
                ev.constructEvent();
                tab = ev.getPropertiesAdminEvent();
		break;
	    case "object":
                Object o = new Object();
                o.setURI(e.getURI());
                o.constructEntity();
                o.constructObject();
                tab = o.getPropertiesAdminObject();
		break;
            case "location":
                Place pl = new Place();
                pl.setURI(e.getURI());
                pl.constructEntity();
                pl.constructPlace();
                tab = pl.getPropertiesAdminPlace();
		break;

	    default:
		throw new AssertionError();
	}
        
        return tab;
    }



    public static ArrayList<Property> LoadEntityProperties(Entity e) { //Riad
        // renvoie un tableau de toutes les propriétés d'une entité donnée
        
        entityBrowser(e);
        
        return null;
    }

    public static Model SearchOurEntitiesFromText(String needle) { //loan
        // renvoie un tableau d'entité à partir d'une chaîne de caractere dans notre base de données sémantique
        Model m = executeQueryConstruct("SELECT ?s "
                + "WHERE { ?s ?p ?name . "
                + "FILTER regex(?name, '^MLK_speech.bwf') }");
        
        System.out.println(m.toString());
        return m;
    }

    public static ArrayList<Entity> SearchAllEntitiesFromText(String needle) { //riad
        // renvoie un tableau d'entité à partir d'une chaîne de caractere dans le LoD (dbpedia, freebase)
       
        ArrayList<Entity> entites = new ArrayList<Entity>();
        
        selectlodFromKeyWord(needle);
        return entites;
    }
}
