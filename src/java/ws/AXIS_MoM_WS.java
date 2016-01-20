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
	model.Object obj = null;
        Person pers = null;
        
        switch (p.getName()) {
	    case "author":
                obj = (model.Object) e;
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
	semantics ctrl = new semantics();
        Property[] tab = ctrl.getAllPropertiesFromEntity(e);
        return tab;
    }

    @Override
    public Entity[] SearchOurEntitiesFromText(String needle) {
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
    public PropertyAdmin[] GetAllPropertiesAdmin(Entity e) {
	ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();
	PropertyAdmin p1 = new PropertyAdmin();
	p1.setName("Surnom");
	p1.setValue_locale("MLK");
	p1.setValue_dbpedia("Martin LK");
	p1.setType("fr");
	list.add(p1);

	PropertyAdmin p2 = new PropertyAdmin();
	p2.setName("Aime");
	p2.setType("URI");

	Entity e2 = new Entity();
	e2.setImage("http://1.1.1.2/bmi/static.ladepeche.fr/content/media/image/zoom/2011/03/07/603056.jpg");
	e2.setName("Coca");
	e2.setType("Object");
	e2.setURI("coca");
	p2.setEntity_locale(e2);

	Entity e1 = new Entity();
	e1.setImage("http://www.sushitime-france.fr/wp-content/uploads/2015/01/canette-coca-33cl.jpg");
	e1.setName("Coca Dbpedia");
	e1.setType("Object");
	e1.setURI("coca");
	p2.setEntity_dbpedia(e1);
	list.add(p2);

	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }

    @Override
    public Entity GetEntity(Entity e) {
	e.constructEntity();        
        return e;
    }

}
