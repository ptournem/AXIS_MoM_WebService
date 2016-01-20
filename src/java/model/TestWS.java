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
//        Entity e = testAddEntity();
        
//        testPerson(e);
        testObject();
        
    }
    
    public static Entity testAddEntity(String img, String name, String type) {
        Entity e = new Entity();
        e.setImage(img);
        e.setName(name);
        e.setType(type);
        
        
        TestWebService ws = new TestWebService();
        Entity e2 = ws.AddEntity(e);
        
        System.out.println("-!- Entité ajoutée : "+e2);
        
        return e2;
        
    }
    
    public static void testObject() {
        Entity e1 = testAddEntity("test.jpg", "lolo", "person");
        Entity e2 = testAddEntity("photo.jpg", "tableau", "object");
        Property p1 = new Property();
	p1.setName("author");
	p1.setValue("robite");
	p1.setType("fr");
        
        Property p2 = new Property();
	p2.setName("author");
	p2.setValue(null);
	p2.setType("uri");
        p2.setEnt(e1);
        
        Property p3 = new Property();
	p3.setName("author");
	p3.setValue(null);
        p3.setType("uri");
	p3.setEnt(new Entity("http://dbpedia.com/blabla", "blabla", "bla.jpg", "aaaaaa"));
        
        
        Entity e11 = testAddEntity("paris.jpg", "Paris", "location");
        
        Property p11 = new Property();
	p11.setName("location");
	p11.setValue("Paris");
	p11.setType("fr");
        p11.setEnt(null);
        
        Property p22 = new Property();
	p22.setName("location");
	p22.setValue(null);
	p22.setType("uri");
        p22.setEnt(e11);
        
        Property p33 = new Property();
	p33.setName("location");
	p33.setValue(null);
        p33.setType("uri");
	p33.setEnt(new Entity("http://dbpedia.com/Paris", "Pariss", "paris.jpg", "location"));
        
        
        
        Object obj = new Object();
        obj.setImage(e2.getImage());
        obj.setURI(e2.getURI());
        obj.setType(e2.getType());
        obj.setName(e2.getName());
        obj.insertAuthor(p2);
        System.out.println("e11:"+e11);
        obj.insertLocation(p22);
        
        Object obj1 = new Object();
        obj1.setURI(obj.getURI());
        obj1.constructEntity();
        System.out.println(obj1.getName()+":"+obj1.getImage()+":"+obj1.getType()+":"+obj1.getURI());
        obj1.constructObject();
        System.out.println(obj1);
    
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
    
        
        //PropertyAdmin[] props = obj.getPropertiesObject();
//        Entity[] maListe = ws.SearchOurEntitiesFromText("G2");
//        System.out.println("-!- Résultat de la recherche pour 'G2' : ");
//        for(int i =0; i<maListe.length; i++) {
//            System.out.println(maListe[i]);
//        }
    }
}
