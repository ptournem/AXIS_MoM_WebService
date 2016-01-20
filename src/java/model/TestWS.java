/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Dialog.Entity;
import Dialog.Property;
import Dialog.PropertyAdmin;
import ws.AXIS_MoM_WS;

/**
 *
 * @author loannguyen
 */
public class TestWS {
    public static void main(String args[]) {

        //System.out.println("test");
        //testConstructEntity();
//        testPerson();
//        testObject();
        //testRecherche();
//        testLoadEntityProperties();
        testAll();
        

    }
    
    public static void testAll(){
        Entity e2 = new Entity();
        e2.setURI("http://dbpedia.org/resource/Leonardo_da_Vinci");
        e2.constructEntity();
        
        Property p1 = new Property();
	p1.setName("sameas");
	p1.setValue(null);
	p1.setType("uri");
        p1.setEnt(e2);
        
        Entity e = testAddEntity("da_Vinci.jpg", "Leonard de Vinci", "person");
        
        Property p2 = new Property();
        p2.setName("birthdate");
        p2.setType("fr");
        p2.setValue("20/03");
        p2.setEnt(null);
        
        Property p3 = new Property();
        p3.setName("deathdate");
        p3.setType("fr");
        p3.setValue("21/03");
        p3.setEnt(null);
        
        Entity e3 = testAddEntity("Paris.jpg", "Paris", "location");
        Property p4 = new Property();
        p4.setName("birthplace");
        p4.setType("fr");
        p4.setValue(null);
        p4.setEnt(e3);
        
        Person per = new Person();
        per.setImage(e.getImage());
        per.setURI(e.getURI());
        per.setType(e.getType());
        per.setName(e.getName());
        
        per.insertBirthDate(p2);
        per.insertPlaceOfBirth(p4);
        per.insertDeathDate(p3);
        
        AXIS_MoM_WS ws = new AXIS_MoM_WS();
        ws.SetEntityProperty(per, p1, e2);
        
        Person per1 = new Person();
        per1.setURI(per.getURI());
        per1.constructEntity();
        per1.constructPerson();
        System.out.println(per1);
        
        
        
        
    }
    public static void testConstructWithLod(){
        
        Entity e = new Entity();
        e.setURI("http://dbpedia.org/resource/Leonardo_da_Vinci");
        e.constructEntity();
        
        Entity e1 = testAddEntity("Da_Vinci.jpg", "Leonard de Vinci", "person");
        
        Property p = new Property();
        p.setName(null);
//        SetEntityProperty
        
    }
    public static void testLoadEntityProperties() {


        Object obj2 = new Object();
        obj2.setURI("http://titan.be/axis-poc2015/7d0b5b6f-5471-4c17-bc43-1cb236795490");
        obj2.constructEntity();
        obj2.constructObject();
        

        System.out.println("obj = "+obj2);
//        PropertyAdmin[] tab = ws.GetAllPropertiesAdmin(e);
//        
//        for(int i=0; i<tab.length;i++) {
//            System.out.println("Property trouvée : "+tab[i]);
//        }
        
        //System.out.println(e);
    }
    
    public static void testConstructEntity() {
        Entity e = new Entity();
        
        e.setURI("http://titan.be/axis-poc2015/55cad415-5726-4e25-a9dc-5da1d2d483ed");
        
        e.constructEntity();
        
        System.out.println(e);
    }
    public static Entity testAddEntity(String img, String name, String type) {
        Entity e = new Entity();
        e.setImage(img);
        e.setName(name);
        e.setType(type);
        
        
        AXIS_MoM_WS ws = new AXIS_MoM_WS();
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
        
//        Entity e3 = new Entity();
//        e3.setURI("http://dbpedia.org/resource/Martin_Luther_King,_Jr.");
////        e3.constructEntity();
//        Property p3 = new Property();
//	p3.setName("author");
//	p3.setValue(null);
//        p3.setType("uri");
//	p3.setEnt(e3);
        
        
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
        obj.insertAuthor(p1);
        System.out.println("e11:"+e11);
        obj.insertLocation(p22);
        
        Object obj1 = new Object();
        obj1.setURI(obj.getURI());
        obj1.constructEntity();
        System.out.println(obj1.getName()+":"+obj1.getImage()+":"+obj1.getType()+":"+obj1.getURI());
        obj1.constructObject();
        System.out.println(obj1);
    
    }
    
    public static void testPerson() {
        Entity e = testAddEntity("test.jpg", "lolo", "person");
        Entity e1 = testAddEntity("paris.jpg", "Paris", "location");
        Property p1 = new Property();
	p1.setName("birthdate");
	p1.setValue("20/01");
	p1.setType("fr");
        
        Property p2 = new Property();
	p2.setName("birthplace");
	p2.setValue(null);
	p2.setType("uri");
        p2.setEnt(e1);
        
        Property p3 = new Property();
	p3.setName("birthplace");
	p3.setValue(null);
        p3.setType("uri");
	p3.setEnt(new Entity("http://dbpedia.com/blabla", "blabla", "bla.jpg", "aaaaaa"));
        
        Property p4 = new Property();
	p4.setName("deathdate");
	p4.setValue("20/06");
	p4.setType("fr");
        
        Person per = new Person();
        per.setImage(e.getImage());
        per.setURI(e.getURI());
        per.setType(e.getType());
        per.setName(e.getName());
        
        per.insertBirthDate(p1);
        per.insertPlaceOfBirth(p2);
        per.insertDeathDate(p4);
    
        Person per1 = new Person();
        per1.setURI(per.getURI());
        per1.constructEntity();
        per1.constructPerson();
        System.out.println(per1);
        
        //PropertyAdmin[] props = obj.getPropertiesObject();
//        Entity[] maListe = ws.SearchOurEntitiesFromText("G2");
//        System.out.println("-!- Résultat de la recherche pour 'G2' : ");
//        for(int i =0; i<maListe.length; i++) {
//            System.out.println(maListe[i]);
//        }
    }
    
    public static void testRecherche() {

        AXIS_MoM_WS ws = new AXIS_MoM_WS();
        Entity[] tab = ws.SearchOurEntitiesFromText("");
        //System.out.println("test");
        for(int i=0; i<tab.length;i++) {
            System.out.println("Entity trouvée : "+tab[i]);
        }
    }
}
