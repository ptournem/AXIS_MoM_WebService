/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Dialog.Entity;
import Dialog.Property;
import java.net.URI;
import static model.Connector.*;
import org.apache.jena.rdf.model.Model;



/**
 *
 * @author loannguyen
 */
public class semantics {
    
    
    public static void main(String args[]) {
        Model m2;
        m2 = SearchOurEntitiesFromText("MLK_speech.bwf");
        System.out.println("main");
    }
    
    public URI AddEntity(String name, URI type) { //robine
        // on ajoute une nouvelle entity à l'aide du connector
        
        //on fait une "case" en fonction des 6 types possibles
        
        // pour chaque case on fait un insert into avec :
//            sujet = type;
//            prédicat = le prédicat en fonction du case type;
//            objet = name;
        return null;
        
    }
    
    public Boolean RemoveEntity(URI entity) {
        // on supprime une nouvelle entity à l'aide du connector
        return null;
    }

    public Boolean SetEntityTextProperty(URI entity, URI property, String value) { 
        // on affecte une nouvelle valeur à une propriété d'une entité à l'aide du connector
        return null;
    }
    
    public Boolean SetEntityObjectProperty(URI entity, URI property, URI value) { 
        // on affecte une nouvelle URI à une propriété d'une entité à l'aide du connector
        return null;
    }

    public Boolean RemoveEntityObjectProperty(URI entity, URI property) {
        // on supprime la propriété d'une entity
        return null;
    }

    public Boolean RemoveEntityObjectPropertyWithObject(URI entity, URI property) {
        // ???
        return null;
    }

    public Property[] LoadEntityProperties() { //loan
        // renvoie un tableau de toutes les propriétés d'une entité donnée
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

    public Entity[] SearchAllEntitiesFromText(String needle) { //riad
        // renvoie un tableau d'entité à partir d'une chaîne de caractere dans le LoD (dbpedia, freebase)
        return null;
    }
}
