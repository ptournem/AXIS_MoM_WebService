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
        test1();
    }
    
    public static void test1() {
        Entity e = new Entity();
        e.setImage("ig2i.jpg");
        e.setName("IG2I");
        e.setType("object");
        
        
        TestWebService ws = new TestWebService();
        Entity e2 = ws.AddEntity(e);
        
        System.out.println("-!- Entité ajoutée : "+e2);
        
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
        obj.setImage(e2.getImage());
        obj.setURI(e2.getURI());
        obj.setType(e2.getType());
        obj.setName(e2.getName());
        
        obj.insertAuthor(p1);
        
        Object obj1 = new Object();
        obj1.setURI(obj.getURI());
        obj1.constructEntity();
        obj1.constructObject();
        System.out.println(obj1);
        //PropertyAdmin[] props = obj.getPropertiesObject();
//        Entity[] maListe = ws.SearchOurEntitiesFromText("G2");
//        System.out.println("-!- Résultat de la recherche pour 'G2' : ");
//        for(int i =0; i<maListe.length; i++) {
//            System.out.println(maListe[i]);
//        }
    }
}
