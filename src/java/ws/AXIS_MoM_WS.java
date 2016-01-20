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
import javax.jws.WebMethod;
import javax.jws.WebParam;
import static model.Connector.selectAllEntitiesURI;
import model.Person;
import model.Object;


@WebService(serviceName = "AXIS_MoM_WS", endpointInterface = "ws.AXIS_MoM_WSInterface")
public class AXIS_MoM_WS implements AXIS_MoM_WSInterface {

    @Override
    public Entity AddEntity(Entity e) {
	return e.AddEntity();
    }

    @Override
    public Boolean RemoveEntity(Entity e) {
	return true;
    }

    @Override
    public Boolean SetEntityProperty(Entity e, Property p, Entity valueEntity) {
	Object obj = new Object();
        Person pers = new Person();
        
        p.setEnt(valueEntity);
        boolean ret = false;
        switch (p.getName()) {
	    case "author":
                obj.setURI(e.getURI());
                obj.constructEntity();
                obj.constructObject();
                obj.insertAuthor(p);
                ret = true;
                break;
            case "sameas":
                e.insertSameAs(p);
                ret = true;
                break;
            case "location":
                obj.setURI(e.getURI());
                obj.constructEntity();
                obj.constructObject();
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
                pers.constructEntity();
                pers.constructPerson();
                pers.insertBirthDate(p);
                ret = true;
                break;
            case "deathdate":
                pers.setURI(e.getURI());
                pers.constructEntity();
                pers.constructPerson();
                pers.insertDeathDate(p);
                ret = true;
                break;
            case "restinplace":
                pers.setURI(e.getURI());
                pers.constructEntity();
                pers.constructPerson();
                pers.insertRestInPlace(p);
                ret = true;
                break;
            case "mother":
                pers.setURI(e.getURI());
                pers.constructEntity();
                pers.constructPerson();
                pers.insertMother(p);
                ret = true;
                break;
            case "father":
                pers.setURI(e.getURI());
                pers.constructEntity();
                pers.constructPerson();
                pers.insertFather(p);
                ret = true;
                break;
            case "isauthorof":
                pers.setURI(e.getURI());
                pers.constructEntity();
                pers.constructPerson();
                pers.insertIsAuthorOf(p);
                ret = true;
                break;
            default:
                return false;
                
        }
        
	return ret;
    }

    @Override
    public Boolean RemoveEntityObjectProperty(Entity e, Property p, Entity valueEntity) {
	return true;
    }

    @Override
    public Boolean RemoveEntityObjectPropertyWithObject(Entity e, Property p, Entity valueEntity) {
	return true;
    }

    @Override
    public Property[] LoadEntityProperties(Entity e) {
        
        e.constructEntity();
        
        Object obj = new Object();
        obj.setURI(e.getURI());
        obj.constructObject();
        System.out.println(obj);
        
        semantics ctrl = new semantics();
        Property[] tab = ctrl.getAllPropertiesFromEntity(e);
        return tab;
        
//        ArrayList<Property> list = new ArrayList<Property>();
//	Property p1 = new Property();
//	p1.setName("Surnom");
//	p1.setValue("MLK");
//	p1.setType("fr");
//	list.add(p1);
//
//	Property p2 = new Property();
//	p2.setName("Aime");
//	p2.setType("URI");
//	Entity e2 = new Entity();
//	e2.setImage("http://t2.uccdn.com/fr/images/2/0/5/img_comment_ouvrir_une_canette_de_coca_secouee_6502_orig.jpg");
//	e2.setName("Coca");
//	e2.setType("object");
//	e2.setURI("coca");
//	p2.setEnt(e2);
//	list.add(p2);
//
//	Property p3 = new Property();
//	p3.setName("Est de nationalité");
//	p3.setType("URI");
//	Entity e3 = new Entity();
//	e3.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/e/e2/Flag_of_the_United_States_%28Pantone%29.svg/320px-Flag_of_the_United_States_%28Pantone%29.svg.png");
//	e3.setName("Américain");
//	e3.setType("organisation");
//	e3.setURI("USA");
//	p3.setEnt(e3);
//	list.add(p3);
//
//	Property p4 = new Property();
//	p4.setName("Conjoint");
//	p4.setType("URI");
//	Entity e4 = new Entity();
//	e4.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Martin_Luther_King_Jr_NYWTS_5.jpg/440px-Martin_Luther_King_Jr_NYWTS_5.jpg");
//	e4.setName("Coretta Scott King");
//	e4.setType("person");
//	e4.setURI("CSK");
//	p4.setEnt(e4);
//	list.add(p4);
//
//	Property[] ret = new Property[list.size()];
//	return (Property[]) list.toArray(ret);
        
//	semantics ctrl = new semantics();
//        Property[] tab = ctrl.getAllPropertiesFromEntity(e);
//        return tab;
    }

    @Override
    public Entity[] SearchOurEntitiesFromText(String needle) {
	String [] tabEntities = selectAllEntitiesURI();
        ArrayList<Entity> tab = new ArrayList<Entity>();
        
        for(int i =0; i<tabEntities.length; i++) {
            Entity e = new Entity();
            e.setURI(tabEntities[i]);
            e.constructEntity();
            if(e.getName() != null) {
                if(e.getName().contains(needle))
                    tab.add(e);
            }
        }
        
        Entity[] ret = new Entity[tab.size()];
	return (Entity[]) tab.toArray(ret);
    }

    @Override
    public Entity[] SearchAllEntitiesFromText(String needle) {
	ArrayList<Entity> list = new ArrayList<>();

	Entity e1 = new Entity();
	e1.setImage("http://1.1.1.2/bmi/static.ladepeche.fr/content/media/image/zoom/2011/03/07/603056.jpg");
	e1.setName("Canette de coca");
	e1.setURI("coca");
	e1.setType("object");
	list.add(e1);

	Entity e2 = new Entity();
	e2.setImage("http://1.1.1.1/bmi/cp91279.biography.com/1000509261001/1000509261001_1891997649001_History-Bill-Clinton-on-MLK-SF.jpg");
	e2.setName("Martin Luther King");
	e2.setURI("MLK");
	e2.setType("person");
	list.add(e2);

	Entity[] ret = new Entity[list.size()];
	return (Entity[]) list.toArray(ret);
    }

    @Override
    public Comment AddComment(Comment c) {
	double URI = (Math.random() * 1000);
	c.setURI(URI + "");
	return c;
    }

    @Override
    public Boolean GrantComment(Comment c) {
	return true;
    }

    @Override
    public Boolean RemoveComment(Comment c) {
	return true;
    }

    @Override
    public Comment[] LoadComment(Entity e) {
	ArrayList<Comment> list = new ArrayList<>();

	Comment c1 = new Comment();
	c1.setAuthorName("Babibel");
	c1.setEmail("babibel@gmail.com");
	c1.setComment("Très belle oeuvre");
	list.add(c1);

	Comment c2 = new Comment();
	c2.setAuthorName("Babibel");
	c2.setEmail("babibel@gmail.com");
	c2.setComment("Très belle oeuvre");
	list.add(c2);

	Comment[] ret = new Comment[list.size()];
	return list.toArray(ret);
    }

    @Override
    public Entity[] GetAllEntities() {
        
        return this.SearchOurEntitiesFromText("");
//	ArrayList<Entity> list = new ArrayList<>();
//
//	Entity e1 = new Entity();
//	e1.setImage("http://1.1.1.2/bmi/static.ladepeche.fr/content/media/image/zoom/2011/03/07/603056.jpg");
//	e1.setName("gros bouffon coco");
//	e1.setURI("coca");
//	e1.setType("object");
//	list.add(e1);
//
//	Entity e2 = new Entity();
//	e2.setImage("http://1.1.1.1/bmi/cp91279.biography.com/1000509261001/1000509261001_1891997649001_History-Bill-Clinton-on-MLK-SF.jpg");
//	e2.setName("salut beau gosse");
//	e2.setURI("MLK");
//	e2.setType("person");
//	list.add(e2);
//
//	Entity[] ret = new Entity[list.size()];
//	return (Entity[]) list.toArray(ret);
    }

    @Override
    public PropertyAdmin[] GetAllPropertiesAdmin(Entity e) {
        
        System.out.println(e.getURI());
        e.constructEntity();
        
        Object obj = new Object();
        obj.setURI(e.getURI());
        obj.constructObject();
        System.out.println(obj);
        
        semantics ctrl = new semantics();
        PropertyAdmin[] tab = ctrl.getAllPropertiesAdminFromEntity(e);
        return tab;
//	ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();
//	PropertyAdmin p1 = new PropertyAdmin();
//	p1.setName("author");
//	p1.setValue_locale("");
//	p1.setValue_dbpedia("");
//	p1.setType("");
//	list.add(p1);
//
//	PropertyAdmin p2 = new PropertyAdmin();
//	p2.setName("location");
//	p2.setValue_locale("");
//	p2.setValue_dbpedia("");
//	p2.setType("");
//	list.add(p2);
//
//	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
//	return (PropertyAdmin[]) list.toArray(ret);
    }

    @Override
    public Entity GetEntity(Entity e) {
	e.constructEntity();        
        return e;
    }

}
