
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
        e.delete(e.getURI(), "rdf:type", "?o");
        e.delete(e.getURI(), "rdf:hasRepresentation", "?o");
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
//                pers.constructEntity();
//                pers.constructPerson();
                pers.insertBirthDate(p);
                ret = true;
                break;
            case "deathdate":
                pers.setURI(e.getURI());
//                pers.constructEntity();
//                pers.constructPerson();
                pers.insertDeathDate(p);
                ret = true;
                break;
            case "restinplace":
                pers.setURI(e.getURI());
//                pers.constructEntity();
//                pers.constructPerson();
                pers.insertRestInPlace(p);
                ret = true;
                break;
            case "child":
                pers.setURI(e.getURI());
//                pers.constructEntity();
//                pers.constructPerson();
                pers.insertChild(p);
                ret = true;
                break;
            case "parent":
                pers.setURI(e.getURI());
//                pers.constructEntity();
//                pers.constructPerson();
                pers.insertParent(p);
                ret = true;
                break;
            case "isauthorof":
                pers.setURI(e.getURI());
//                pers.constructEntity();
//                pers.constructPerson();
                pers.insertIsAuthorOf(p);
                ret = true;
                break;
            case "socialnetwork":
                e.insertSocialNetwork(p);
                ret = true;
                break;
            case "birthplace":
                pers.setURI(e.getURI());
//                pers.constructEntity();
//                pers.constructPerson();
                pers.insertPlaceOfBirth(p);
                ret = true;
                break;
            case "birthplaceof":
                pla.setURI(e.getURI());
//                pla.constructEntity();
//                pla.constructPlace();
                pla.insertBirthPlaceOf(p);
                ret = true;
                break;
            case "deathplace":
                pers.setURI(e.getURI());
                pers.insertDeathDate(p);
                ret = true;
                break;
            case "deathplaceof":
                pla.setURI(e.getURI());
                pla.insertDeathPlace(p);
                ret = true;
                break;
            case "postalcode":
                pla.setURI(e.getURI());
//                pla.constructEntity();
//                pla.constructPlace();
                pla.insertPostalCode(p);
                ret = true;
                break;
            case "region":
                pla.setURI(e.getURI());
//                pla.constructEntity();
//                pla.constructPlace();
                pla.insertRegion(p);
                ret = true;
                break;
            case "country":
                pla.setURI(e.getURI());
//                pla.constructEntity();
//                pla.constructPlace();
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
            case "dateOfCreation":
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
                break;
            case "sameas":
                e.delete(e.getURI(), "owl:sameAs", "<"+valueEntity.getURI()+">");
                break;
            case "socialnetwork":
                property = "axis-datamodel:socialNetwork";
                regof = "Document";
                break;
            case "description":
                property = "rdf:Description";
                regof = "Document";
                break;
            case "location":
                property = "axis-datamodel:takePlaceIn";
                regof = "RegOfObjectItem";
                break;
            case "birthdate":
                property = "schema:birthDate";
                regof = "RegOfPhysicalPerson";
                break;
            case "deathdate":
                property = "schema:deathDate";
                regof = "RegOfPhysicalPerson";
                break;
            case "restinplace":
                property = "dbont:restInPlace";
                regof = "RegOfPhysicalPerson";
                break;
            case "parent":
                property = "dbont:parent";
                regof = "RegOfPhysicalPerson";
                break;
            case "child":
                property = "dbont:child";
                regof = "RegOfPhysicalPerson";
                break;
            case "isauthorof":
                property = "axis-datamodel:isPerformedBy";
                regof = "RegOfPhysicalPerson";
                break;
            case "birthplace":
                property = "dbont:birthPlace";
                regof = "RegOfPhysicalPerson";
                break;
            case "birthplaceof":
                property = "dbont:birthPlace";
                regof = "RegOfPlace";
                break;
            case "deathplace":
                property = "dbont:birthPlace";
                regof = "RegOfPhysicalPerson";
                break;
            case "deathplaceof":
                property = "dbont:deathPlace";
                regof = "RegOfPlace";
                break;
            case "postalcode":
                property = "dbont:postalCode";
                regof = "RegOfPlace";
                break;
            case "region":
                property = "dbont:region";
                regof = "RegOfPlace";
                break;
            case "country":
                property = "dbont:country";
                regof = "RegOfPlace";
                break;
            case "locationof":
                property = "axis-datamodel:isAPlaceOfObject";
                regof = "RegOfPlace";
                break;
            case "placeofevent":
                property = "axis-datamodel:takesPlaceIn";
                regof = "RegOfEvent";
                break;
            case "hasparticipant":
                property = "axis-datamodel:hasParticipant";
                regof = "RegOfEvent";
                break;
            case "dateofevent":
                property = "dbont:date";
                regof = "RegOfEvent";
                break;
            case "website":
                property = "dbont:wikiPageExternalLink";
                regof = "Document";
                break;
            case "owner":
                property = "dbont:owner";
                regof = "RegOfObjectItem";
                break;
            case "museum":
                property = "dbp:museum";
                regof = "RegOfObjectItem";
                break;
            case "year":
                property = "dbp:year";
                regof = "Document";
                break;
            case "type":
                property = "dbp:type";
                regof = "Document";
                break;
            case "placeoforganisation":
                property = "axis-datamodel:takesPlaceIn";
                regof = "RegOfMoralPerson";
                break;
            case "isaplaceoforganisation":
                property = "dbont:location";
                regof = "RegOfPlace";
                break;
            case "dateofcreation":
                property = "dbp:established";
                regof = "RegOfMoralPerson";
                break;
            case "istheleaderof":
                property = "dbont:leaderName";
                regof = "RegOfPhysicalPerson";
                break;
                
            default:
                return false;
        }
        
        if(valueEntity.getURI().isEmpty() || valueEntity.getURI() == null || valueEntity.getURI().equals("null"))
            e.delete(selectRegOfEntity(e.getURI(), regof), property, "?o");
        else
            e.delete(selectRegOfEntity(e.getURI(), regof), property, "<"+valueEntity.getURI()+">");
        
	return ret;
    }

    @Override
    public Property[] LoadEntityProperties(Entity e) {
        
        
        e.constructEntity();
        System.out.println("e:"+e);
//        Object obj = new Object();
//        obj.setURI(e.getURI());
//        obj.constructObject();
//        System.out.println(obj);
        
        semantics ctrl = new semantics();
        
        Property[] tab = ctrl.getAllPropertiesFromEntity(e);
        return tab;

    }

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

    @Override
    public Entity[] SearchAllEntitiesFromText(String needle) {
        ArrayList<Entity> tab = selectlodFromKeyWord(needle);
        Entity[] ret = new Entity[tab.size()];
	return (Entity[]) tab.toArray(ret);
    }

    @Override
    public Comment AddComment(Comment c, Entity e) {
        c.setEntity(e);
        c = c.insertComment();
	return c;
    }

    @Override
    public Boolean GrantComment(Comment c) {
        c.changeValided(true);
	return true;
    }

    @Override
    public Boolean RemoveComment(Comment c, Entity e) {
        c.setEntity(e);
        return c.deleteComment();
    }

    @Override
    public Boolean DenyComment(Comment c) {
        c.changeValided(false);
	return true;
    }
    
    @Override
    public Comment[] LoadComment(Entity e) {
        
        Comment[] tab;
        if(e == null)
            tab = selectAllComments();
        else
            tab = selectAllComments(e);
	
        return tab;
    }

    @Override
    public Entity[] GetAllEntities() {
        
        return this.SearchOurEntitiesFromText("");
    }

    @Override
    public PropertyAdmin[] GetAllPropertiesAdmin(Entity e) {
        
        e.constructEntity();
        semantics ctrl = new semantics();
        PropertyAdmin[] tab = ctrl.getAllPropertiesAdminFromEntity(e);

        return tab;

    }

    @Override
    public Entity GetEntity(Entity e) {
	e.constructEntity();        
        return e;
    }

}
