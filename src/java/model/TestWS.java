/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Dialog.Entity;
import Dialog.Property;
import Dialog.PropertyAdmin;

/**
 *
 * @author loannguyen
 */
public class TestWS {
    public static void main(String args[]) {
        Entity e = testAddEntity();
        
        testPerson(e);
        
        testRecherche();
    }
    
    public static Entity testAddEntity() {
        Entity e = new Entity();
        e.setImage("ig2i.jpg");
        e.setName("IG2I");
        e.setType("object");
        
        
        TestWebService ws = new TestWebService();
        Entity e2 = ws.AddEntity(e);
        
        System.out.println("-!- Entité ajoutée : "+e2);
        
        return e2;
        
    }
    
    public static void testObject(Entity e) {
        Property p1 = new Property();
	p1.setName("author");
	p1.setValue("robite");
	p1.setType("fr");
        
        Property p2 = new Property();
	p2.setName("author");
	p2.setValue(null);
	p2.setType("uri");
        p2.setEnt(new Entity("http://blablabla.com", "blabla", "bla.jpg", "aaaaaa"));
        
        Property p3 = new Property();
	p3.setName("author");
	p3.setValue(null);
        p3.setType("uri");
	p3.setEnt(new Entity("http://dbpedia.com/blabla", "blabla", "bla.jpg", "aaaaaa"));
        
        Object obj = new Object();
        obj.setImage(e.getImage());
        obj.setURI(e.getURI());
        obj.setType(e.getType());
        obj.setName(e.getName());
        
        obj.insertAuthor(p1);
    
    }
    
    public static void testPerson(Entity e) {
        Property p1 = new Property();
	p1.setName("birthdate");
	p1.setValue("20/01");
	p1.setType("fr");
        
        Property p2 = new Property();
	p2.setName("birthplace");
	p2.setValue(null);
	p2.setType("uri");
        p2.setEnt(new Entity("http://blablabla.com", "blabla", "bla.jpg", "aaaaaa"));
        
        Property p3 = new Property();
	p3.setName("birthplace");
	p3.setValue(null);
        p3.setType("uri");
	p3.setEnt(new Entity("http://dbpedia.com/blabla", "blabla", "bla.jpg", "aaaaaa"));
        
        Person per = new Person();
        per.setImage(e.getImage());
        per.setURI(e.getURI());
        per.setType(e.getType());
        per.setName(e.getName());
        
        per.insertBirthDate(p1);
    
    }
    
    public static void testRecherche() {

        TestWebService ws = new TestWebService();
        Entity[] tab = ws.SearchOurEntitiesFromText("G2");

        
        System.out.println("-!- nb de résultats : "+tab.length);
    }
}
