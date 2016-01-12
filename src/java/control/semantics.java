/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Dialog.Entity;
import Dialog.Property;
import java.net.URI;



/**
 *
 * @author loannguyen
 */
public class semantics {
    
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

    public Entity[] SearchOurEntitiesFromText(String needle) { //loan
        // renvoie un tableau d'entité à partir d'une chaîne de caractere dans notre base de données sémantique
        return null;
    }

    public Entity[] SearchAllEntitiesFromText(String needle) { //riad
        // renvoie un tableau d'entité à partir d'une chaîne de caractere dans le LoD (dbpedia, freebase)
        return null;
    }
}
