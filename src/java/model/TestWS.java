/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Dialog.Entity;
import Dialog.Property;
import Dialog.PropertyAdmin;
import java.util.ArrayList;
import org.apache.jena.assembler.JA;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import ws.AXIS_MoM_WS;

/**
 *
 * @author loannguyen
 */
public class TestWS {
    
    public static long startTime;
    public static long endTime;
    
    public static void main(String args[]) {

        //System.out.println("test");
        //testConstructEntity();
//        testPerson();
//        testObject();
//            testRecherche();
//        testLoadEntityProperties();
//        Entity e1 = testAddEntity("paris.jpg", "Paris", "location");
//        Entity e2 = testAddEntity("france.jpg", "France", "location");
//        Entity e3 = testAddEntity("test.jpg", "Jean", "person");
//        
//        Person per = new Person();
//        per.setURI(e3.getURI());
//        
//        
//        Property p1 = new Property();
//	p1.setName("birthplace");
//	p1.setValue(null);
//	p1.setType("uri");
//        ArrayList<Entity> ale = new ArrayList<Entity> ();
//        ale.add(e1);
//        Entity [] eTab = new Entity[ale.size()];
//        
//        
//        Property p2 = new Property();
//	p2.setName("birthplace");
//	p2.setValue(null);
//	p2.setType("uri");
//        ArrayList<Entity> ale1 = new ArrayList<Entity> ();
//        ale1.add(e2);
//        Entity [] eTab1 = new Entity[ale1.size()];
//        
//        
//        p1.setEnt((Entity []) ale.toArray(eTab));
//        p2.setEnt((Entity []) ale1.toArray(eTab1));
//        
//        per.insertPlaceOfBirth(p1);
//        per.insertPlaceOfBirth(p2);
//        
//        Person per1 = new Person();
//        per1.setURI(e3.getURI());
//        per1.constructEntity();
//        per1.constructPerson();
//        System.out.println("per1>>>>"+per1);
//        
//        for (int i = 0; i < per1.placeOfBirth.getEntity_locale().length; i++) {
//            System.out.println("placeOfBirth>>>>>"+per1.placeOfBirth.getEntity_locale()[i]);
//        }
//        testAll();
        //testLoadEntityProperties();
        testFonctionnel(true);
        //testPropertiesDbpedia();
     
        //testInference();
        
//        AXIS_MoM_WS ws = new AXIS_MoM_WS();
//        Entity leonard = new Entity();
//        leonard.setURI("http://titan.be/axis-poc2015/1965cce4-1969-4f63-9e4d-2c6bed7102cf");
//        leonard.constructEntity();
//        Property[] props = ws.LoadEntityProperties(leonard);
//        for(int i=0; i<props.length;i++) {
//            System.out.println(" - Property["+i+"] : "+props[i]);
//        }
    }
    
    
    public static void testInference() {
        Entity leonard = new Entity("Léonard Da Vincii", "http://www.ccjc-neuilly.com/wp-content/uploads/2015/12/Leonard.jpg", "person");
        leonard.AddEntity();
        String uri = leonard.getURI();
        QueryExecution qe = QueryExecutionFactory.sparqlService(
                //                "http://localhost:3030/ds/query", String.format(
                //                "PREFIX poc: <http://titan.be/axis-poc2015/>" +
                //                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                //                "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.2#>" +
                //                "construct {%s ?p ?o}" +
                //                "WHERE { ?s ?p ?o }", uri));
                //        
                "http://localhost:3030/ds/query", String.format(
                        "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
                        + "construct{?s ?p ?o}"
                        + "WHERE { ?s ?p ?o . {"
                        + "SELECT * WHERE {"
                        + "<%s> ?p ?o }"
                        + "} }", uri));

        Model m = qe.execConstruct();
        
        System.out.println(m.getResource("label"));
        
        System.out.println(m.getProperty("label"));
        qe.close();
    }
    public static void testPropertiesDbpedia() {
        Entity e = new Entity();
        e.setURI("http://dbpedia.org/resource/Vinci,_Tuscany");
        e.constructEntity();
        
        Place pla = new Place();
        pla.setURI("http://dbpedia.org/resource/Vinci,_Tuscany");
        pla.constructEntity();
        pla.constructPlace(true);
        
        System.out.println("Entity : "+e);
        System.out.println("Place : "+pla);
        
        
    }
    
    
    public static void testFonctionnel(boolean dbpedia) {
        
        AXIS_MoM_WS ws = new AXIS_MoM_WS();
        
        Entity bourgeois = new Entity("Les Bourgeois de Calais", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/Bourgeois_de_Calais,_mus%C3%A9e_Rodin.JPG/250px-Bourgeois_de_Calais,_mus%C3%A9e_Rodin.JPG", "object");
        Entity leonard = new Entity("Léonard Da Vincii", "http://www.ccjc-neuilly.com/wp-content/uploads/2015/12/Leonard.jpg", "person");
        Entity caterina = new Entity("Caterina Da Vinci", "https://pbs.twimg.com/profile_images/514575733126365185/u_xPRRKq_400x400.jpeg", "person");
        Entity antonio = new Entity("Antonio Da Vinci", "https://s-media-cache-ak0.pinimg.com/736x/b8/d7/51/b8d7512c624b786baad3ab1bfa3f0163.jpg", "person");
        Entity amboise = new Entity("Amboise", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/64/Ch%C3%A2teau_d'Amboise_07.jpg/220px-Ch%C3%A2teau_d'Amboise_07.jpg", "location");
        Entity joconde = new Entity("La Joconde", "https://download.vikidia.org/vikidia/fr/images/thumb/1/13/La_gioconda.jpg/200px-La_gioconda.jpg", "object");

        
        
        bourgeois = ws.AddEntity(bourgeois);
        
        
        //création Léonard Da Vinci
        leonard = ws.AddEntity(leonard);
        
        //ajouter Léonard Da vinci => sameas => dbpedia
        
        
        //ajouter Léonard Da vinci => birthdate => String
        lierEntity(ws, leonard, "birthdate", "21-01");
        
        //ajouter Léonard Da vinci => deathdate => String
        lierEntity(ws, leonard, "deathdate", "21-05");
        
        
        
        //création Caterina Da Vinci
        caterina = ws.AddEntity(caterina);
        
        //lier Léonard de vinci => mother => Caterina
        lierEntity(ws, leonard, "mother", caterina);
        
        //création de Antonio Da Vinci
        antonio = ws.AddEntity(antonio);
        
        //lier Léonard de vinci => father => Antonio
        lierEntity(ws, leonard, "father", antonio);
        lierEntity(ws, leonard, "father", caterina);
        
        
        //création Amboise
        amboise = ws.AddEntity(amboise);
        
        //lier Léonard Da vinci => restinplace => Amboise
        lierEntity(ws, leonard, "restinplace", amboise);
        
        //ajouter Léonard Da vinci => description => String
        lierEntity(ws, leonard, "description", "Léonard Da Vinci est un super artiste");
        
        
        //création Joconde
        joconde = ws.AddEntity(joconde);
        
        
        //ajouter Joconde => description => String
        lierEntity(ws, joconde, "description", "La Joconde est un super tableau");
        
        //lier Léonard Da vinci => isauthorof => Joconde
        lierEntity(ws, leonard, "isauthorof", joconde);
        
        
        
        Property[] props = ws.LoadEntityProperties(leonard);
        PropertyAdmin[] propsAdmin = ws.GetAllPropertiesAdmin(leonard);
        
        System.out.println("\n\n\n_______________");
        
        if(dbpedia) {
            Entity leonardDB = new Entity();
            leonardDB.setURI("http://dbpedia.org/resource/Leonardo_da_Vinci");
            leonardDB.constructEntity();

            Entity vinciDB = new Entity();
            vinciDB.setURI("http://dbpedia.org/resource/Vinci,_Tuscany");
            vinciDB.constructEntity();
            System.out.println("type:"+vinciDB.getType());
            Entity rodinDB = new Entity();
            rodinDB.setURI("http://dbpedia.org/resource/Auguste_Rodin");
            rodinDB.constructEntity();

            lierEntity(ws, bourgeois, "author", rodinDB);
            lierEntity(ws, leonard, "birthplace", vinciDB);
            lierEntity(ws, leonard, "father", rodinDB);
            lierEntity(ws, leonard, "sameas", leonardDB);
            Property[] props2 = ws.LoadEntityProperties(vinciDB);
            
            System.out.println("\nProperty Vinci (URI Dbpedia) :");
            for(int i=0; i<props2.length;i++) {
                System.out.println(" - Property["+i+"] : "+props2[i]);
            }
        }
        
        
        
        
        System.out.println("\nProperty Leonard (type Person) :");
        for(int i=0; i<props.length;i++) {
            System.out.println(" - Property["+i+"] : "+props[i]);
        }
        
        System.out.println("\nPropertyAdmin Leonard (type Person) :");
        for(int i=0; i<propsAdmin.length;i++) {
            System.out.println(" - PropertyAdmin["+i+"] : "+propsAdmin[i]);
        }
        
        System.out.println(" ------- Leonard URI : "+leonard.getURI());
        System.out.println(" ------- Caterina URI : "+caterina.getURI());
//        System.out.println(" ------- Amboise URI : "+amboise.getURI());
        leonard.delete("dbont:mother", caterina.getURI());
        
        Property[] props3 = ws.LoadEntityProperties(leonard);
        
        System.out.println("\nProperty Leonard (type Person) :");
        for(int i=0; i<props3.length;i++) {
            System.out.println(" - Property["+i+"] : "+props3[i]);
        }
        
        
        
    }
    
    
    public static void lierEntity(AXIS_MoM_WS ws, Entity e1, String s, Entity e2) {
        
        Property p = new Property(s, null, "uri", null);
        ws.SetEntityProperty(e1, p, e2);
    }
    
    public static void lierEntity(AXIS_MoM_WS ws, Entity e1, String s, String val) {
        Property p = new Property(s, val, "fr", null);
        ws.SetEntityProperty(e1, p, null);
    }
    
    public static void debutTimer() {
        startTime = System.currentTimeMillis();
        System.out.println("_____ DEBUT FONCTION");
    }
    
    public static void finTimer() {
        endTime = System.currentTimeMillis();
        System.out.println("_____ FIN FONCTION : "+(endTime-startTime));
    }
    
    public static void testAll(){
        Entity e2 = new Entity();
        e2.setURI("http://dbpedia.org/resource/Leonardo_da_Vinci");
        e2.constructEntity();
        
        Property p1 = new Property();
	p1.setName("sameas");
	p1.setValue(null);
	p1.setType("uri");
        
        Entity[] tab = new Entity[1];
        tab[0] = e2;
        p1.setEnt(tab);
        
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
        tab[0] = e3;
        p4.setEnt(tab);
        
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
        
        tab[0] = e4;
        p5.setEnt(tab);
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
        tab[0] = e5;
        p6.setEnt(tab);
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
        tab[0] = e6;
        p7.setEnt(tab);
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
        tab[0] = e7;
        p9.setEnt(tab);
        
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
        per1.constructPerson(true);
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


        
        Entity napDB = new Entity();
        napDB.setURI("http://dbpedia.org/resource/Paris");
        napDB.constructEntity();
        System.out.println(">>>>>>>>>>>>>>>>>\n\n\n"+napDB+"\n\n\n<<<<<<<<<<<<<<<<<");
        Entity nap = testAddEntity("nap.jpg", "nap", "person");
        
        AXIS_MoM_WS ws = new AXIS_MoM_WS();
        
        Place obj2 = new Place();
        
        
        lierEntity(ws, nap, "sameas", napDB);
        
        obj2.setURI(napDB.getURI());
        obj2.constructEntity();
        System.out.println((Entity) obj2);
        obj2.constructPlace(true);
        

        System.out.println("obj = "+obj2);
//        PropertyAdmin[] tab = ws.GetAllPropertiesAdmin(e);
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
        
        Entity[] tab = new Entity[1];
        tab[0] = e1;
        p2.setEnt(tab);
        
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
        
        tab[0] = e11;
        p22.setEnt(tab);
        
        Property p33 = new Property();
	p33.setName("location");
	p33.setValue(null);
        p33.setType("uri");
        
        tab[0] = new Entity("http://dbpedia.com/Paris", "Pariss", "paris.jpg", "location");
	p33.setEnt(tab);
        
        
        
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
        obj1.constructObject(false);
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
        
        Entity[] tab = new Entity[1];
        tab[0] = e1;
        p2.setEnt(tab);
        
        Property p3 = new Property();
	p3.setName("birthplace");
	p3.setValue(null);
        p3.setType("uri");
        
        tab[0] = new Entity("http://dbpedia.com/blabla", "blabla", "bla.jpg", "aaaaaa");
	p3.setEnt(tab);
        
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
        per1.constructPerson(false);
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
