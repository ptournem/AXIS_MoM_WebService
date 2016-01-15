/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import Dialog.Comment;
import Dialog.Entity;
import Dialog.Property;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Paul
 */
@WebService(serviceName = "AXIS_MoM_WS_phpTest", endpointInterface = "ws.AXIS_MoM_WSInterface")
public class AXIS_MoM_WS_phpTest implements AXIS_MoM_WSInterface {

    @Override
    public Entity AddEntity(Entity e) {
	double URI = (Math.random() * 1000);
	e.setURI(URI + "");
	return e;
    }

    @Override
    public Boolean RemoveEntity(Entity e) {
	return true;
    }

    @Override
    public Boolean SetEntityProperty(Entity e, Property p) {
	return true;
    }

    @Override
    public Boolean RemoveEntityObjectProperty(Entity e, Property p) {
	return true;
    }

    @Override
    public Boolean RemoveEntityObjectPropertyWithObject(Entity e, Property p) {
	return true;
    }

    @Override
    public Property[] LoadEntityProperties(Entity e) {
	ArrayList<Property> list = new ArrayList<Property>();
	Property p1 = new Property();
	p1.setName("blablabla");
	p1.setURI("URIprop");
	p1.setValue("blablbal");
	p1.setType("fr");
	list.add(p1);

	Property p2 = new Property();
	p2.setName("blabliblo");
	p2.setURI("URI2");
	p2.setValue("oui");
	p2.setType("fr");
	Entity e2 = new Entity();
	e2.setImage("image");
	e2.setName("Pain");
	e2.setType("Object");
	e2.setURI("URI");
	p2.setE(e2);
	list.add(p2);

	Property[] ret = new Property[list.size()];
	return (Property[]) list.toArray(ret);
    }

    @Override
    public Entity[] SearchOurEntitiesFromText(String needle) {
	ArrayList<Entity> list = new ArrayList<Entity>();

	Entity e1 = new Entity();
	e1.setImage(needle);
	e1.setName(needle);
	e1.setURI(needle);
	e1.setType(needle);
	list.add(e1);

	Entity e2 = new Entity();
	e2.setImage(needle);
	e2.setName(needle);
	e2.setURI(needle);
	e2.setType(needle);
	list.add(e2);

	Entity[] ret = new Entity[list.size()];
	return (Entity[]) list.toArray(ret);
    }

    @Override
    public Entity[] SearchAllEntitiesFromText(String needle) {
	ArrayList<Entity> list = new ArrayList<>();

	Entity e1 = new Entity();
	e1.setImage(needle);
	e1.setName(needle);
	e1.setURI(needle);
	e1.setType(needle);
	list.add(e1);

	Entity e2 = new Entity();
	e2.setImage(needle);
	e2.setName(needle);
	e2.setURI(needle);
	e2.setType(needle);
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

}
