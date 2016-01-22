/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Dialog.Entity;
import Dialog.Property;
import Dialog.PropertyAdmin;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
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
//            testRecherche();
//        testLoadEntityProperties();
        testAll();
        //testLoadEntityProperties();
        

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
        
        Entity e = testAddEntity("http://www.ccjc-neuilly.com/wp-content/uploads/2015/12/Leonard.jpg", "Leonard de Vinci", "person");
        
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
        
//        Entity e3 = testAddEntity("http://wikitravel.org/upload/shared//thumb/d/da/Paris_Eiffel.jpg/320px-Paris_Eiffel.jpg", "Paris", "location");
        Entity e3 = new Entity();
        e3.setURI("http://dbpedia.org/resource/Vinci,_Tuscany");
        e3.constructEntity();
        Property p4 = new Property();
        p4.setName("birthplace");
        p4.setType("uri");
        p4.setValue(null);
        p4.setEnt(e3);
        
        Person per = new Person();
        per.setImage(e.getImage());
        per.setURI(e.getURI());
        per.setType(e.getType());
        per.setName(e.getName());
        
        //ajout de la mother
        Entity e4 = testAddEntity("https://pbs.twimg.com/profile_images/514575733126365185/u_xPRRKq_400x400.jpeg", "Caterina da Vinci", "person");
        Person per2 = new Person();
        per2.setImage(e4.getImage());
        System.out.println("\n\n>>>>>>>>>>>"+e4.getURI()+"<<<<<<<<<<<<<<\n\n");
        per2.setURI(e4.getURI());
        per2.setType(e4.getType());
        per2.setName(e4.getName());
        
        Property p5 = new Property();
        p5.setName("mother");
        p5.setType("uri");
        p5.setValue(null);
        p5.setEnt(e4);
        //fin ajout de la mother
        
        //ajout du pere
        Entity e5 = testAddEntity("https://s-media-cache-ak0.pinimg.com/736x/b8/d7/51/b8d7512c624b786baad3ab1bfa3f0163.jpg", "Antonio da Vinci", "person");
        Person per3 = new Person();
        per3.setImage(e5.getImage());
        per3.setURI(e5.getURI());
        per3.setType(e5.getType());
        per3.setName(e5.getName());

        Property p6 = new Property();
        p6.setName("father");
        p6.setType("uri");
        p6.setValue(null);
        p6.setEnt(e5);
        //fin ajout du pere
        
        //ajout du restinplace
        Entity e6 = testAddEntity("https://upload.wikimedia.org/wikipedia/commons/thumb/6/64/Ch%C3%A2teau_d'Amboise_07.jpg/220px-Ch%C3%A2teau_d'Amboise_07.jpg", "Amboise", "location");
        Person per4 = new Person();
        per4.setImage(e6.getImage());
        per4.setURI(e6.getURI());
        per4.setType(e6.getType());
        per4.setName(e6.getName());

        Property p7 = new Property();
        p7.setName("restinplace");
        p7.setType("uri");
        p7.setValue(null);
        p7.setEnt(e6);
        //fin ajout du restinplace
        
        
        //debut ajout description leonard da vinci
        Property p8 = new Property();
        p8.setName("description");
        p8.setType("fr");
        p8.setValue("Léonard de Vinci (Leonardo di ser Piero da VinciLeonardo di ser Piero da Vinci, dit Leonardo da Vinci, né à Vinci le 15 avril 1452 et mort à Amboise le 2 mai 1519, est un peintre florentin et un homme d'esprit universel");
        p8.setEnt(null);
        //fin ajout description leonard da vinci
        
        
        //ajout de la joconde
        Entity e7 = testAddEntity("https://download.vikidia.org/vikidia/fr/images/thumb/1/13/La_gioconda.jpg/200px-La_gioconda.jpg", "La Joconde", "object");
        Object obj5 = new Object();
        obj5.setImage(e7.getImage());
        obj5.setURI(e7.getURI());
        obj5.setType(e7.getType());
        obj5.setName(e7.getName());

        Property p9 = new Property();
        p9.setName("isauthorof");
        p9.setType("uri");
        p9.setValue(null);
        p9.setEnt(e7);
        
        Property p10 = new Property();
        p10.setName("description");
        p10.setType("fr");
        p10.setValue("La Joconde, ou Portrait de Mona Lisa, est un tableau de l'artiste italien Léonard de Vinci, réalisé entre 1503 et 1506, qui représente un portrait mi-corps, probablement celui de la florentine Lisa Gherardini, épouse de Francesco del Giocondo.");
        p10.setEnt(null);
        obj5.insertDescription(p10);
        //fin de la joconde
        
        per.insertIsAuthorOf(p9);
        per.insertRestInPlace(p7);
        per.insertFather(p6);
        per.insertMother(p5);
        per.insertBirthDate(p2);
        per.insertPlaceOfBirth(p4);
        per.insertDeathDate(p3);
        per.insertDescription(p8);
        
        AXIS_MoM_WS ws = new AXIS_MoM_WS();
        ws.SetEntityProperty(per, p1, e2);
        
        Person per1 = new Person();
        per1.setURI(per.getURI());
        per1.constructEntity();
        per1.constructPerson();
        System.out.println(per1);
        System.out.println(e);
        
        
        
        
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


        Person obj2 = new Person();
        obj2.setURI("http://dbpedia.org/resource/Leonardo_da_Vinci");
        obj2.constructEntity();
        obj2.constructPerson();
        

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
