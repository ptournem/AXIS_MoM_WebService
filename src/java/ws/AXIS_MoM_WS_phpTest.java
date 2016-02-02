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
	e.setURI((int) URI + "");
	return e;
    }

    @Override
    public Boolean RemoveEntity(Entity e) {
	return true;
    }

    @Override
    public Boolean SetEntityProperty(Entity e, Property p, Entity valueEntity) {
	return true;
    }

    @Override
    public Boolean RemoveEntityProperty(Entity e, Property p, Entity valueEntity) {
	return true;
    }

    @Override
    public Boolean RemoveEntityObjectPropertyWithObject(Entity e, Property p, Entity valueEntity) {
	return true;
    }

    @Override
    public Property[] LoadEntityProperties(Entity e) {
	ArrayList<Property> list = new ArrayList<Property>();
	Property p1 = new Property();
	p1.setName("Surnom");
	p1.setValue("MLK");
	p1.setType("fr");
	list.add(p1);

	Property p2 = new Property();
	p2.setName("Aime");
	p2.setType("uri");
	Entity e2 = new Entity();
	e2.setImage("http://t2.uccdn.com/fr/images/2/0/5/img_comment_ouvrir_une_canette_de_coca_secouee_6502_orig.jpg");
	e2.setName("Coca");
	e2.setType("object");
	e2.setURI("coca");
        
        Entity e5 = new Entity();
	e5.setImage("http://smallbites.andybellatti.com/wp-content/uploads/2009/08/176.jpg");
	e5.setName("Redbull");
	e5.setType("object");
	e5.setURI("redbull");
        
        Entity[] tab = new Entity[2];
        tab[0] = e2;
        tab[1] = e5;
	p2.setEnt(tab);
	list.add(p2);

	Property p3 = new Property();
	p3.setName("Est de nationalité");
	p3.setType("uri");
	Entity e3 = new Entity();
	e3.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/e/e2/Flag_of_the_United_States_%28Pantone%29.svg/320px-Flag_of_the_United_States_%28Pantone%29.svg.png");
	e3.setName("Américain");
	e3.setType("organisation");
	e3.setURI("USA");
        
        Entity[] tab2 = new Entity[1];
        tab2[0] = e3;
	p3.setEnt(tab2);
	list.add(p3);

	Property p4 = new Property();
	p4.setName("Conjoint");
	p4.setType("uri");
	Entity e4 = new Entity();
	e4.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Martin_Luther_King_Jr_NYWTS_5.jpg/440px-Martin_Luther_King_Jr_NYWTS_5.jpg");
	e4.setName("Coretta Scott King");
	e4.setType("person");
	e4.setURI("CSK");
        
        Entity[] tab3 = new Entity[1];
        tab3[0] = e4;
	p4.setEnt(tab3);
	list.add(p4);

	Property[] ret = new Property[list.size()];
	return (Property[]) list.toArray(ret);
    }

    @Override
    public Entity[] SearchOurEntitiesFromText(String needle) {
	ArrayList<Entity> list = new ArrayList<Entity>();

	Entity e1 = new Entity();
	e1.setImage("http://t2.uccdn.com/fr/images/2/0/5/img_comment_ouvrir_une_canette_de_coca_secouee_6502_orig.jpg");
	e1.setName("Canette de coca");
	e1.setURI("coca");
	e1.setType("object");
	list.add(e1);

	Entity e2 = new Entity();
	e2.setImage("http://a4.files.biography.com/image/upload/c_fit,cs_srgb,dpr_1.0,h_1200,q_80,w_1200/MTE5NTU2MzE2MjgwNDg5NDgz.jpg");
	e2.setName("Martin Luther King");
	e2.setURI("MLK");
	e2.setType("person");
	list.add(e2);

	Entity[] ret = new Entity[list.size()];
	return (Entity[]) list.toArray(ret);
    }

    @Override
    public Entity[] SearchAllEntitiesFromText(String needle) {
	ArrayList<Entity> list = new ArrayList<>();

	Entity e1 = new Entity();
	e1.setImage("http://t2.uccdn.com/fr/images/2/0/5/img_comment_ouvrir_une_canette_de_coca_secouee_6502_orig.jpg");
	e1.setName("Canette de coca");
	e1.setURI("coca");
	e1.setType("object");
	list.add(e1);

	Entity e2 = new Entity();
	e2.setImage("http://a4.files.biography.com/image/upload/c_fit,cs_srgb,dpr_1.0,h_1200,q_80,w_1200/MTE5NTU2MzE2MjgwNDg5NDgz.jpg");
	e2.setName("Martin Luther King");
	e2.setURI("MLK");
	e2.setType("person");
	list.add(e2);

	Entity[] ret = new Entity[list.size()];
	return (Entity[]) list.toArray(ret);
    }

    @Override
    public Comment AddComment(Comment c, Entity e) {
	double URI = (Math.random() * 1000);
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
    public Boolean DenyComment(Comment c) {
	return true;
    }
    
    @Override
    public Comment[] LoadComment(Entity e) {
	ArrayList<Comment> list = new ArrayList<>();

	Comment c1 = new Comment();
	c1.setAuthorName("Babar");
	c1.setEmail("babar@gmail.com");
	c1.setComment("Une œuvre que j’ai vraiment apprécié regarder. Elle symbolise tellement pour moi et pour l’histoire de notre monde.");
	list.add(c1);

	Comment c2 = new Comment();
	c2.setAuthorName("Babibel");
	c2.setEmail("babibel@gmail.com");
	c2.setComment("Bluffant, je n’ai jamais été aussi émue devant une œuvre. Et pourtant j’ai visité de nombreux musées tout au long de ma vie...");
	list.add(c2);

	Comment[] ret = new Comment[list.size()];
	return list.toArray(ret);
    }

    @Override
    public Entity[] GetAllEntities() {
	ArrayList<Entity> list = new ArrayList<>();

	Entity e1 = new Entity();
	e1.setImage("http://t2.uccdn.com/fr/images/2/0/5/img_comment_ouvrir_une_canette_de_coca_secouee_6502_orig.jpg");
	e1.setName("Canette de coca");
	e1.setURI("coca");
	e1.setType("object");
	list.add(e1);

	Entity e2 = new Entity();
	e2.setImage("http://a4.files.biography.com/image/upload/c_fit,cs_srgb,dpr_1.0,h_1200,q_80,w_1200/MTE5NTU2MzE2MjgwNDg5NDgz.jpg");
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
	p2.setName("Apprécie");
	p2.setType("uri");

	Entity e2 = new Entity();
	e2.setImage("http://t2.uccdn.com/fr/images/2/0/5/img_comment_ouvrir_une_canette_de_coca_secouee_6502_orig.jpg");
	e2.setName("Coca");
	e2.setType("object");
	e2.setURI("coca");
        
        Entity e3 = new Entity();
	e3.setImage("http://smallbites.andybellatti.com/wp-content/uploads/2009/08/176.jpg");
	e3.setName("Redbull");
	e3.setType("object");
	e3.setURI("redbull");
        
        Entity[] tab = new Entity[2];
        tab[0] = e2;
        tab[1] = e3;
	p2.setEntity_locale(tab);

	Entity e1 = new Entity();
	e1.setImage("http://www.sushitime-france.fr/wp-content/uploads/2015/01/canette-coca-33cl.jpg");
	e1.setName("Coca Dbpedia");
	e1.setType("object");
	e1.setURI("coca");
        
        Entity[] tab2 = new Entity[1];
        tab2[0] = e1;
	p2.setEntity_dbpedia(tab2);
	list.add(p2);

	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }

    @Override
    public Entity GetEntity(Entity e) {
	if (e == null || e.getURI() == null) {
	    return null;
	}

	e.setImage("http://a4.files.biography.com/image/upload/c_fit,cs_srgb,dpr_1.0,h_1200,q_80,w_1200/MTE5NTU2MzE2MjgwNDg5NDgz.jpg");
	e.setName("Martin Luther King");
	e.setType("person");

	return e;
    }

}
