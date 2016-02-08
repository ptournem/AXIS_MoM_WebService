
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import Dialog.Comment;
import Dialog.Entity;
import Dialog.Property;
import Dialog.PropertyAdmin;
import control.semantics;
import java.util.ArrayList;
import javax.jws.WebService;
import static model.Connector.selectAllEntitiesURI;
import static model.Connector.selectRegOfEntity;
import static model.Connector.selectAllComments;
import static model.Connector.selectlodFromKeyWord;
import model.Event;
import model.Person;
import model.Object;
import model.Organisation;
import model.Place;

/**
 * 
 */
@WebService(serviceName = "AXIS_MoM_WS", endpointInterface = "ws.AXIS_MoM_WSInterface")
public class AXIS_MoM_WS implements AXIS_MoM_WSInterface {
    

    /**
     * AddEntity : Ajoute une entité dans la base de données sémantique.
     * @param e : l'entité à ajouter dans la base. Elle a une URI vide
     * @return : l'entité avec son URI de renseignée
     */
    @Override
    public Entity AddEntity(Entity e) {
	return e.AddEntity();
    }

    /**
     * Supprime une entité de la base de données sémantique.
     * @param e : l'entité à supprimer
     * @return : true si la suppression a été effectuée
     */
    @Override
    public Boolean RemoveEntity(Entity e) {
        e.deleteTriplet("<"+e.getURI()+">", "?p", "?o");
        e.deleteTriplet("?s", "axis-datamodel:uses", "<"+e.getURI()+">");
	return true;
    }

    /**
     * Modifie une propriété d’une entité (ajoute un sujet-prédicat-objet à notre entité)
     * @param e : l'entité qui recevra l'ajout de la propriété
     * @param p : la propriété (prédicat + objet)
     * @param valueEntity : si l'objet est une Entité, on la renseigne dans valueEntity
     * @return La fonction renvoie un booléen “Vrai” si la modification de la
     * propriété s’est bien passée.
     */
    @Override
    public Boolean SetEntityProperty(Entity e, Property p, Entity valueEntity) {
	Object obj = new Object();
        Person pers = new Person();
        Place pla = new Place();
        Event eve = new Event();
        Organisation orga = new Organisation();
        e.constructEntity();
        
        Entity[] tab = new Entity[1];
        tab[0] = valueEntity;
        p.setEnt(tab);
        
        boolean ret = false;
        switch (p.getName()) {
	    case "author":
                obj.setURI(e.getURI());
//                obj.constructEntity();
//                obj.constructObject();
                obj.insertAuthor(p);
                ret = true;
                break;
            case "sameas":
                e.insertSameAs(p);
                ret = true;
                break;
            case "description":
                e.insertDescription(p);
                ret = true;
                break;
            case "location":
                obj.setURI(e.getURI());
//                obj.constructEntity();
//                obj.constructObject();
                obj.insertLocation(p);
                ret = true;
                break;
            case "image":
                e.insertImage(p);
                ret = true;
                break;
            case "name":
                e.insertName(p);
                ret = true;
                break;
            case "birthdate":
                pers.setURI(e.getURI());
                pers.insertBirthDate(p);
                ret = true;
                break;
            case "deathdate":
                pers.setURI(e.getURI());
                pers.insertDeathDate(p);
                ret = true;
                break;
            case "restinplace":
                pers.setURI(e.getURI());
                pers.insertRestInPlace(p);
                ret = true;
                break;
            case "child":
                pers.setURI(e.getURI());
                pers.insertChild(p);
                ret = true;
                break;
            case "parent":
                pers.setURI(e.getURI());
                pers.insertParent(p);
                ret = true;
                break;
            case "isauthorof":
                pers.setURI(e.getURI());
                pers.insertIsAuthorOf(p);
                ret = true;
                break;
            case "socialnetwork":
                e.insertSocialNetwork(p);
                ret = true;
                break;
            case "birthplace":
                pers.setURI(e.getURI());
                pers.insertPlaceOfBirth(p);
                ret = true;
                break;
            case "birthplaceof":
                pla.setURI(e.getURI());
                pla.insertBirthPlaceOf(p);
                ret = true;
                break;
            case "deathplace":
                pers.setURI(e.getURI());
                pers.insertDeathPlace(p);
                ret = true;
                break;
            case "deathplaceof":
                pla.setURI(e.getURI());
                pla.insertDeathPlace(p);
                ret = true;
                break;
            case "postalcode":
                pla.setURI(e.getURI());
                pla.insertPostalCode(p);
                ret = true;
                break;
            case "region":
                pla.setURI(e.getURI());
                pla.insertRegion(p);
                ret = true;
                break;
            case "country":
                pla.setURI(e.getURI());
                pla.insertCountry(p);
                ret = true;
                break;
            case "locationof":
                pla.setURI(e.getURI());
                pla.insertLocationOf(p);
                ret = true;
                break;
            case "placeofevent":
                eve.setURI(e.getURI());
                eve.insertPlaceOfEvent(p);
                ret = true;
                break;
            case "hasparticipant":
                eve.setURI(e.getURI());
                eve.insertHasParticipant(p);
                ret = true;
                break;
            case "dateofevent":
                eve.setURI(e.getURI());
                eve.insertDateOfEvent(p);
                ret = true;
                break;
            case "website":
                e.insertWebsite(p);
                ret = true;
                break;
            case "owner":
                obj.setURI(e.getURI());
                obj.insertOwner(p);
                ret = true;
                break;
            case "museum":
                obj.setURI(e.getURI());
                obj.insertMuseum(p);
                ret = true;
                break;
            case "hasobject":
                orga.setURI(e.getURI());
                orga.insertMuseum(p);
                ret = true;
                break;
            case "year":
                obj.setURI(e.getURI());
                obj.insertYear(p);
                ret = true;
                break;
            case "type":
                obj.setURI(e.getURI());
                obj.insertType(p);
                ret = true;
                break;
            case "placeoforganisation":
                orga.setURI(e.getURI());
                orga.insertPlaceOfOrganisation(p);
                ret = true;
                break;
            case "isaplaceoforganisation":
                pla.setURI(e.getURI());
                pla.insertIsAPlaceOfOrganisation(p);
                ret = true;
                break;
            case "dateofcreation":
                orga.setURI(e.getURI());
                orga.insertDateOfCreation(p);
                ret = true;
                break;    
            case "leader":
                orga.setURI(e.getURI());
                orga.insertLeader(p);
                ret = true;
                break;
            case "istheleaderof":
                pers.setURI(e.getURI());
                pers.insertIsTheLeaderOf(p);
                ret = true;
                break;
            case "participatesinevent":
                pers.setURI(e.getURI());
                pers.insertParticipatesInEvent(p);
                ret = true;
                break;
                
            default:
                return false;
                
        }
        
	return ret;
    }

    
    /**
     * Supprime une propriété d’une entité. 
     * @param e l’entité dont on veut supprimer la propriété. Son attribut “URI” doit être renseigné. 
     * @param p la propriété que l’on veut supprimer. Ses attribut “URI” et “type” doivent être renseignés.
     * @param valueEntity l’attribut “type” est “URI”, l’objet Entity “valueEntity” doit être fourni avec son attribut “URI” renseigné. Sinon, l’objet Property “p” doit avoir son attribut “value” renseigné et l’objet Entity “valueEntity” peut être “null”.
     * @return La fonction renvoie un booléen “Vrai” si la suppresion s’est bien passée.
     */
    @Override
    public Boolean RemoveEntityProperty(Entity e, Property p, Entity valueEntity) {
        
        boolean ret = false;
        String property = "null";
        String regof = "null";
        
        switch (p.getName()) {
	    case "author":
                property = "axis-datamodel:performs";
                regof = "RegOfObjectItem";
                ret = true;
                break;
            case "sameas":
                e.delete(e.getURI(), "owl:sameAs", "<"+valueEntity.getURI()+">");
                ret = true;
                break;
            case "socialnetwork":
                property = "axis-datamodel:socialNetwork";
                regof = "Document";
                ret = true;
                break;
            case "description":
                property = "rdf:Description";
                regof = "Document";
                ret = true;
                break;
            case "location":
                property = "axis-datamodel:takePlaceIn";
                regof = "RegOfObjectItem";
                ret = true;
                break;
            case "birthdate":
                property = "schema:birthDate";
                regof = "RegOfPhysicalPerson";
                ret = true;
                break;
            case "deathdate":
                property = "schema:deathDate";
                regof = "RegOfPhysicalPerson";
                ret = true;
                break;
            case "restinplace":
                property = "dbont:restInPlace";
                regof = "RegOfPhysicalPerson";
                ret = true;
                break;
            case "parent":
                property = "dbont:parent";
                regof = "RegOfPhysicalPerson";
                ret = true;
                break;
            case "child":
                property = "dbont:child";
                regof = "RegOfPhysicalPerson";
                ret = true;
                break;
            case "isauthorof":
                property = "axis-datamodel:isPerformedBy";
                regof = "RegOfPhysicalPerson";
                ret = true;
                break;
            case "birthplace":
                property = "dbont:birthPlace";
                regof = "RegOfPhysicalPerson";
                ret = true;
                break;
            case "birthplaceof":
                property = "dbont:birthPlace";
                regof = "RegOfPlace";
                ret = true;
                break;
            case "deathplace":
                property = "dbont:deathPlace";
                regof = "RegOfPhysicalPerson";
                ret = true;
                break;
            case "deathplaceof":
                property = "dbont:deathPlace";
                regof = "RegOfPlace";
                ret = true;
                break;
            case "postalcode":
                property = "dbont:postalCode";
                regof = "RegOfPlace";
                break;
            case "region":
                property = "dbont:region";
                regof = "RegOfPlace";
                ret = true;
                break;
            case "country":
                property = "dbont:country";
                regof = "RegOfPlace";
                ret = true;
                break;
            case "locationof":
                property = "axis-datamodel:isAPlaceOfObject";
                regof = "RegOfPlace";
                ret = true;
                break;
            case "placeofevent":
                property = "axis-datamodel:takesPlaceIn";
                regof = "RegOfEvent";
                ret = true;
                break;
            case "hasparticipant":
                property = "axis-datamodel:hasParticipant";
                regof = "RegOfEvent";
                ret = true;
                break;
            case "dateofevent":
                property = "dbont:date";
                regof = "RegOfEvent";
                ret = true;
                break;
            case "website":
                property = "dbont:wikiPageExternalLink";
                regof = "Document";
                ret = true;
                break;
            case "owner":
                property = "dbont:owner";
                regof = "RegOfObjectItem";
                ret = true;
                break;
            case "museum":
                property = "dbp:museum";
                regof = "RegOfObjectItem";
                ret = true;
                break;
            case "hasobject":
                property = "dbp:museum";
                regof = "RegOfMoralPerson";
                ret = true;
                break;
            case "year":
                property = "dbp:year";
                regof = "Document";
                ret = true;
                break;
            case "type":
                property = "dbp:type";
                regof = "Document";
                ret = true;
                break;
            case "placeoforganisation":
                property = "axis-datamodel:takesPlaceIn";
                regof = "RegOfMoralPerson";
                ret = true;
                break;
            case "isaplaceoforganisation":
                property = "dbont:location";
                regof = "RegOfPlace";
                ret = true;
                break;
            case "dateofcreation":
                property = "dbp:established";
                regof = "RegOfMoralPerson";
                ret = true;
                break;
            case "istheleaderof":
                property = "dbont:leaderName";
                regof = "RegOfPhysicalPerson";
                ret = true;
                break;
            case "participatesinevent":
                property = "axis-datamodel:participatesInEvent";
                regof = "RegOfPhysicalPerson";
                ret = true;
                break;
                
            default:
                ret = false;
        }
        
        if(ret) {
            if(valueEntity.getURI().isEmpty() || valueEntity.getURI() == null || valueEntity.getURI().equals("null"))
                e.delete(selectRegOfEntity(e.getURI(), regof), property, "?o");
            else
                e.delete(selectRegOfEntity(e.getURI(), regof), property, "<"+valueEntity.getURI()+">");
        }
        
	return ret;
    }

    /**
     * Charge les propriétés remplies d’une entité.
     * @param e : l’entité dont on veut charger les propriétés. Son attribut “URI” doit être renseigné. 
     * @return : renvoie un tableau d’objet Property non-vide de l'entité voulue
     */
    @Override
    public Property[] LoadEntityProperties(Entity e) {
        e.constructEntity();
        
        semantics ctrl = new semantics();
        
        Property[] tab = ctrl.getAllPropertiesFromEntity(e);
        return tab;
    }

    /**
     * Cherche les entités présentes dans notre base de données sémantiques dont le nom correspond au texte passé en paramètre. 
     * @param needle : texte recherché dans le nom des entités.
     * @return : tableau d’objet Entity qui correspondent à la recherche. 
     */
    @Override
    public Entity[] SearchOurEntitiesFromText(String needle) {

	Entity[] tabEntities = selectAllEntitiesURI();
        ArrayList<Entity> tab = new ArrayList<Entity>();

        needle = needle.toLowerCase().replaceAll(" ", "");
        for(int i =0; i<tabEntities.length; i++) {
            if(tabEntities[i].getName() != null) {
                if(tabEntities[i].getName().toLowerCase().replaceAll(" ", "").contains(needle)) {
                    Entity e = new Entity();
                    e.setURI(tabEntities[i].getURI());
                    e.constructEntity();
                    tab.add(e);
                }
            }
        }
        
        Entity[] ret = new Entity[tab.size()];
	return (Entity[]) tab.toArray(ret);
    }

    /**
     * Cherche les entités présentes dans le Linked Open Data dont le nom correspond au texte passé en paramètre. 
     * @param needle : représente le texte recherché dans le nom des entités.
     * @return : tableau d’objet Entity qui correspondent à la recherche. 
     */
    @Override
    public Entity[] SearchAllEntitiesFromText(String needle) {
        ArrayList<Entity> tab = selectlodFromKeyWord(needle);
        Entity[] ret = new Entity[tab.size()];
	return (Entity[]) tab.toArray(ret);
    }

    /**
     * Ajoute un commentaire dans la base de données sémantique locale. 
     * @param c : commentaire que l’on veut ajouter. Ses attributs “email”, “authorName” et “comment” doivent être renseignés. 
     * @param e : l’entité que l’on veut commenter. Son attribut “URI” doit être renseigné. 
     * @return : le commentaire inséré dans la base donnée sémantique locale avec tous ses attributs renseignés.
     */
    @Override
    public Comment AddComment(Comment c, Entity e) {
        c.setEntity(e);
        c = c.insertComment();
	return c;
    }

    /**
     * Valide un commentaire afin qu’il soit affiché au public du site web. 
     * @param c : le commentaire que l’on veut valider. Son attribut “id” doit être renseigné.
     * @return : renvoie un booléen “Vrai” si la validation s’est bien déroulée.
     */
    @Override
    public Boolean GrantComment(Comment c) {
        c.changeValided(true);
	return true;
    }

    /**
     * Supprime un commentaire de la base de données sémantique locale. 
     * @param c : le commentaire que l’on veut supprimer. Son attribut “id” doit être renseigné. 
     * @param e : l’entité que l’on veut supprimer. Son attribut “URI” doit être renseigné. 
     * @return : renvoie un booléen “Vrai” si la suppression s’est bien déroulée.
     */
    @Override
    public Boolean RemoveComment(Comment c, Entity e) {
        c.setEntity(e);
        return c.deleteComment();
    }

    /**
     * Refuse un commentaire afin qu’il ne soit plus affiché au public mais pas supprimé.
     * @param c : commentaire que l’on veut refuser. Son attribut “id” doit être renseigné. 
     * @return : renvoie un booléen “Vrai” si le refus s’est bien déroulé.
     */
    @Override
    public Boolean DenyComment(Comment c) {
        c.changeValided(false);
	return true;
    }
    
    /**
     * Charge tous les commentaires sur une entité. 
     * @param e : l’entité dont on veut récupérer les commentaires. Son attribut “URI” doit être renseigné. 
     * @return : un tableau d’objet Comment. Si l’objet Entity “e” est “null”,  la fonction renvoie tous les commentaires de la base de données sémantiques locale. 
     */
    @Override
    public Comment[] LoadComment(Entity e) {
        
        Comment[] tab;
        if(e == null)
            tab = selectAllComments();
        else
            tab = selectAllComments(e);
	
        return tab;
    }
    
    /**
     * charge toutes les entités présentes dans notre base de données.
     * @return : La fonction renvoie un tableau d’objet Entity qui sont présentes dans notre base de données sémantique locale.
     */
    @Override
    public Entity[] GetAllEntities() {
        
        return this.SearchOurEntitiesFromText("");
    }
    
    /**
     * charge toutes les propriétés possible d’une entité pour l’administration de celles-ci.
     * @param e : l’entité dont on veut charger les propriétés. Son attribut “URI” doit être renseigné.
     * @return : renvoie un tableau d’objet PropertyAdmin.
     */
    @Override
    public PropertyAdmin[] GetAllPropertiesAdmin(Entity e) {
        
        e.constructEntity();
        semantics ctrl = new semantics();
        PropertyAdmin[] tab = ctrl.getAllPropertiesAdminFromEntity(e);

        return tab;

    }

    /**
     * Récupère une entité. 
     * @param e : l’entité que l’on veut récupérer. Son attribut “URI” doit être renseigné.
     * @return : La fonction renvoie un objet Entity correspondant à l’attribut URI donnée en paramètre et dont tous les attributs sont renseignés.
     */
    @Override
    public Entity GetEntity(Entity e) {
	e.constructEntity();        
        return e;
    }

}
