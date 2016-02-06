/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import Dialog.Comment;
import Dialog.Entity;
import Dialog.Property;
import Dialog.PropertyAdmin;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import static model.Connector.deleteAll;
import ws.AXIS_MoM_WS;

/**
 *
 * @author loannguyen
 */
public class TestWS {

    public static long startTime;
    public static long endTime;

    public static void main(String args[]) {

        //testComments();
        testFonctionnel(true);
//        testComments();
        //System.out.println("test");
        //testConstructEntity();
//        testPerson();
//        testObject();
        //testRecherche();
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
//        testFonctionnel(true);
//        String s = "test;http://dbpedia.org/resource/Vinci,_Tuscany;http://dbpedia.org/resource/Vinci,_Tuscany;http://dbpedia.org/resource/Vinci,_Tuscany";
//        
//        ArrayList list = new ArrayList() ;
//        String[] split = s.split(";");
//        System.out.println("string:"+s);
//        for (int i = 0 ; i < split.length; i++) {
//            list.add(split[i]);
//            System.out.println("list:"+split[i]);
//        }
//        Set set = new HashSet() ;
//        set.addAll(list) ;
//        ArrayList distinctList = new ArrayList(set) ;
//
//        
//        java.lang.Object[] a = distinctList.toArray();
//        for (int i = 0; i < a.length; i++) {
//            System.out.println("distinctList:"+a[i]);
//        }
        //testRecherche();
        //testPropertiesDbpedia();
        //testInference();
//        AXIS_MoM_WS ws = new AXIS_MoM_WS();
//        Entity leonard = new Entity();
//        leonard.setURI("http://titan.be/axis-poc2015/5e1a737f-4f8e-421e-b6fe-2324a4bad28c");
//        leonard.constructEntity();
//        Property[] props = ws.LoadEntityProperties(leonard);
//
//        System.out.println(" - Property[4] : "+props[4]);
//        
//        System.out.println("_______");
//        Entity leonard2 = new Entity();
//        leonard2.setURI("http://titan.be/axis-poc2015/5e1a737f-4f8e-421e-b6fe-2324a4bad28c");
//        leonard2.constructEntity();
//        Property[] props2 = ws.LoadEntityProperties(leonard2);
//
//        System.out.println(" - Property[4] : "+props2[4]); 
        
//            AXIS_MoM_WS ws = new AXIS_MoM_WS();
//            
//            Entity vinciDB = new Entity();
//            vinciDB.setURI("http://dbpedia.org/resource/Vinci,_Tuscany");
//            vinciDB.constructEntity();
//            
//
//            Property[] props2 = ws.LoadEntityProperties(vinciDB);
//
//            System.out.println(vinciDB);
//            System.out.println("_____ AFFICHAGE DE LA PROPERTY : ");
//            System.out.println("\nProperty Vinci (URI Dbpedia) :");
//            for (int i = 0; i < props2.length; i++) {
//                System.out.println(" - Property[" + i + "] : " + props2[i]);
//            }
            
    }
    
    public static void testPropertiesDbpedia() {
        Entity e = new Entity();
        e.setURI("http://dbpedia.org/resource/Vinci,_Tuscany");
        e.constructEntity();

        Place pla = new Place();
        pla.setURI("http://dbpedia.org/resource/Vinci,_Tuscany");
        pla.constructEntity();
        pla.constructPlace(true);

        System.out.println("Entity : " + e);
        System.out.println("Place : " + pla);

    }

    public static void testFonctionnel(boolean dbpedia) {

        deleteAll();
        
        AXIS_MoM_WS ws = new AXIS_MoM_WS();

        Entity bourgeois = new Entity("Les Bourgeois de Calais", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/Bourgeois_de_Calais,_mus%C3%A9e_Rodin.JPG/250px-Bourgeois_de_Calais,_mus%C3%A9e_Rodin.JPG", "object");
        Entity leonard = new Entity("Léonard Da Vinci", "http://www.ccjc-neuilly.com/wp-content/uploads/2015/12/Leonard.jpg", "person");
        Entity caterina = new Entity("Caterina Da Vinci", "https://pbs.twimg.com/profile_images/514575733126365185/u_xPRRKq_400x400.jpeg", "person");
        Entity antonio = new Entity("Antonio Da Vinci", "https://s-media-cache-ak0.pinimg.com/736x/b8/d7/51/b8d7512c624b786baad3ab1bfa3f0163.jpg", "person");
        Entity amboise = new Entity("Amboise", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/64/Ch%C3%A2teau_d'Amboise_07.jpg/220px-Ch%C3%A2teau_d'Amboise_07.jpg", "location");
        Entity joconde = new Entity("La Joconde", "https://download.vikidia.org/vikidia/fr/images/thumb/1/13/La_gioconda.jpg/200px-La_gioconda.jpg", "object");
        Entity louvre = new Entity("Musée du Louvre", "https://upload.wikimedia.org/wikipedia/en/4/42/Louvre_Pyramid.jpg", "location");

        Entity vinci = new Entity("Vinci", "http://www.lepoint.fr/images/2011/03/22/italie-unita-274376-jpg_165194.JPG", "location");
        Entity italie = new Entity("Italie", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/03/Flag_of_Italy.svg/langfr-225px-Flag_of_Italy.svg.png", "location");
        Entity toscane = new Entity("Toscane", "http://www.franceinfo.fr/sites/default/files/2013/06/22/1036825/images/principale/yes.jpg", "location");
        Entity louvreOrg = new Entity("Musée du Louvre", "https://upload.wikimedia.org/wikipedia/en/4/42/Louvre_Pyramid.jpg", "organisation");
        Entity mlkSpeech = new Entity("Discours de Martin Luther King", "http://www.saphirnews.com/photo/art/default/5798576-8644705.jpg?v=1377633337", "event");
        Entity lincolnMemorial = new Entity("Lincoln Memorial", "https://fr.wikipedia.org/wiki/Lincoln_Memorial#/media/File:Aerial_view_of_Lincoln_Memorial_-_east_side_EDIT.jpeg", "location");
        Entity mlk = new Entity("Martin Luther King", "http://a4.files.biography.com/image/upload/c_fit,cs_srgb,dpr_1.0,h_1200,q_80,w_1200/MTE5NTU2MzE2MjgwNDg5NDgz.jpg", "person");
        bourgeois = ws.AddEntity(bourgeois);
        vinci = ws.AddEntity(vinci);
        italie = ws.AddEntity(italie);
        toscane = ws.AddEntity(toscane);
        mlk = ws.AddEntity(mlk);
        lincolnMemorial = ws.AddEntity(lincolnMemorial);
        mlkSpeech = ws.AddEntity(mlkSpeech);
        //création Léonard Da Vinci
        leonard = ws.AddEntity(leonard);
        louvreOrg = ws.AddEntity(louvreOrg);
        //ajouter Léonard Da vinci => sameas => dbpedia
        //ajouter Léonard Da vinci => birthdate => String
        lierEntity(ws, leonard, "birthdate", "1400-01-25");

        //ajouter Léonard Da vinci => deathdate => String
        lierEntity(ws, leonard, "deathdate", "1450-03-21");
        
        //création Joconde
        joconde = ws.AddEntity(joconde);
        louvre = ws.AddEntity(louvre);
        
        
        //création Caterina Da Vinci
        caterina = ws.AddEntity(caterina);

        //lier Léonard de vinci => mother => Caterina
        //lierEntity(ws, leonard, "parent", caterina);

        //création de Antonio Da Vinci
        antonio = ws.AddEntity(antonio);

        //création Amboise
        amboise = ws.AddEntity(amboise);

        //lier Léonard de vinci => father => Antonio
        lierEntity(ws, leonard, "parent", antonio);
        lierEntity(ws, leonard, "parent", caterina);
        lierEntity(ws, leonard, "parent", amboise);
        lierEntity(ws, leonard, "socialnetwork", "leoHashtag");

        //lier Léonard Da vinci => restinplace => Amboise
        lierEntity(ws, leonard, "restinplace", amboise);
        lierEntity(ws, leonard, "birthplace", vinci);
        lierEntity(ws, vinci, "country", italie);
        lierEntity(ws, vinci, "region", toscane);
        lierEntity(ws, vinci, "birthplaceof", leonard);
        lierEntity(ws, vinci, "postalcode", "50059");
        //lierEntity(ws, vinci, "locationof", leonard);
        lierEntity(ws, vinci, "description", "Vinci est une commune dans la ville métropolitaine de Florence en Toscane (Italie).");
        //ajouter Léonard Da vinci => description => String
        lierEntity(ws, leonard, "description", "Léonard Da Vinci est un super artiste");
        
        lierEntity(ws, louvreOrg, "description", "Le musée du Louvre est un musée d'art et d'antiquités situé au centre de Paris dans le palais du Louvre.");
        lierEntity(ws, louvreOrg, "dateofcreation", "1792");
        lierEntity(ws, louvreOrg, "socialnetwork", "#MuséeDuLouvre");
        lierEntity(ws, louvreOrg, "website", "http://www.louvre.fr/");
        lierEntity(ws, louvreOrg, "leader", "Jean-Luc Martinez");
        lierEntity(ws, louvreOrg, "hasobject", joconde);
        lierEntity(ws, louvreOrg, "placeoforganisation", louvre);
        
        lierEntity(ws, mlkSpeech, "website", "http://www.americanrhetoric.com/speeches/mlkihaveadream.htm");
        lierEntity(ws, mlkSpeech, "socialnetwork", "#DiscoursDeMartinLutherKing");
        lierEntity(ws, mlkSpeech, "description", "I have a dream (traduit en français par « Je fais un rêve ») est à la fois le nom du discours le plus célèbre de Martin Luther King et le point d'orgue du Mouvement des droits civiques.");
        lierEntity(ws, mlkSpeech, "placeofevent", lincolnMemorial);
        lierEntity(ws, mlkSpeech, "dateofevent", "28 August 1963");
        lierEntity(ws, mlkSpeech, "hasparticipant", mlk);
        
        
        
        
        lierEntity(ws, joconde, "location", louvre);
        //ajouter Joconde => description => String
        lierEntity(ws, joconde, "description", "La Joconde est un super tableau");
        lierEntity(ws, joconde, "author", leonard);
        //lier Léonard Da vinci => isauthorof => Joconde
        lierEntity(ws, leonard, "isauthorof", joconde);
        //lierEntity(ws, leonard, "birthplace", vinci);
        Property p = new Property("parent", null, null, "uri", "fr");
        ws.RemoveEntityProperty(leonard, p, amboise);

        System.out.println("URI leonard : " + leonard.getURI());
        joconde = ws.GetEntity(joconde);
        System.out.println("\n\n\n_______________");

        if (dbpedia) {
            Entity leonardDB = new Entity();
            leonardDB.setURI("http://dbpedia.org/resource/Leonardo_da_Vinci");
            leonardDB.constructEntity();

            Entity vinciDB = new Entity();
            vinciDB.setURI("http://dbpedia.org/resource/Vinci,_Tuscany");
            vinciDB.constructEntity();
            
            Entity rodinDB = new Entity();
            rodinDB.setURI("http://dbpedia.org/resource/Auguste_Rodin");
            rodinDB.constructEntity();
            
            Entity louvreDB = new Entity();
            louvreDB.setURI("http://dbpedia.org/resource/Louvre");
            louvreDB.constructEntity();
            
            Entity jocondeDB = new Entity();
            jocondeDB.setURI("http://dbpedia.org/resource/I,_Mona_Lisa");
            jocondeDB.constructEntity();
            
            
            lierEntity(ws, bourgeois, "author", rodinDB);
//            lierEntity(ws, leonard, "birthplace", vinciDB);
            lierEntity(ws, leonard, "parent", rodinDB);
            lierEntity(ws, leonard, "sameas", leonardDB);
            lierEntity(ws, joconde, "sameas", jocondeDB);
            lierEntity(ws, louvre, "sameas", louvreDB);
            lierEntity(ws, vinci, "sameas", vinciDB);
            Property[] props2 = ws.LoadEntityProperties(vinciDB);

            System.out.println("\nProperty Vinci (URI Dbpedia) :");
            for (int i = 0; i < props2.length; i++) {
                System.out.println(" - Property[" + i + "] : " + props2[i]);
            }
        }

        Property[] props = ws.LoadEntityProperties(leonard);
        PropertyAdmin[] propsAdmin = ws.GetAllPropertiesAdmin(leonard);
        PropertyAdmin[] propsVinci = ws.GetAllPropertiesAdmin(vinci);
        Property[] propsJoconde = ws.LoadEntityProperties(joconde);
        Property[] propsLouvre = ws.LoadEntityProperties(louvre);
        
        PropertyAdmin[] propsMlkSpeech = ws.GetAllPropertiesAdmin(mlkSpeech);
        
        System.out.println("\nProperty Leonard (type Person) :");
        for (int i = 0; i < props.length; i++) {
            System.out.println(" - Property[" + i + "] : " + props[i]);
        }

        System.out.println("\nPropertyAdmin Vinci (type Place) :");
        for (int i = 0; i < propsVinci.length; i++) {
            System.out.println(" - Property[" + i + "] : " + propsVinci[i]);
        }
        
        System.out.println("\nProperty Joconde (type Object) :");
        for (int i = 0; i < propsJoconde.length; i++) {
            System.out.println(" - Property[" + i + "] : " + propsJoconde[i]);
        }
        
        System.out.println("\nProperty Louvre (type Place) :");
        for (int i = 0; i < propsLouvre.length; i++) {
            System.out.println(" - Property[" + i + "] : " + propsLouvre[i]);
        }
        
        System.out.println("\nPropertyAdmin Leonard (type Person) :");
        for (int i = 0; i < propsAdmin.length; i++) {
            System.out.println(" - PropertyAdmin[" + i + "] : " + propsAdmin[i]);
        }
        
        System.out.println("\nPropertyAdmin MLK Speech (type Event) :");
        for (int i = 0; i < propsMlkSpeech.length; i++) {
            System.out.println(" - PropertyAdmin[" + i + "] : " + propsMlkSpeech[i]);
        }
        
        
        //leonard.delete("dbont:mother", caterina.getURI());
//        Property[] props3 = ws.LoadEntityProperties(leonard);
//        
//        System.out.println("\nProperty Leonard (type Person) :");
//        for(int i=0; i<props3.length;i++) {
//            System.out.println(" - Property["+i+"] : "+props3[i]);
//        } 
    }

    
    public static void lierEntity(AXIS_MoM_WS ws, Entity e1, String s, Entity e2) {

        Property p = new Property(s, null, null, "uri", "fr");
        ws.SetEntityProperty(e1, p, e2);
    }

    public static void lierEntity(AXIS_MoM_WS ws, Entity e1, String s, String val) {
        Property p = new Property(s, val, null, "string", "fr");
        ws.SetEntityProperty(e1, p, null);
    }

    public static void debutTimer() {
        startTime = System.currentTimeMillis();
        System.out.println("_____ DEBUT FONCTION");
    }

    public static void finTimer() {
        endTime = System.currentTimeMillis();
        System.out.println("_____ FIN FONCTION : " + (endTime - startTime));
    }

    public static void testAll() {
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
        System.out.println("\n\n>>>>>>>>>>>" + e4.getURI() + "<<<<<<<<<<<<<<\n\n");
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
        per.insertParent(p6);
        per.insertParent(p5);
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

    public static void testConstructWithLod() {

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
        System.out.println(">>>>>>>>>>>>>>>>>\n\n\n" + napDB + "\n\n\n<<<<<<<<<<<<<<<<<");
        Entity nap = testAddEntity("nap.jpg", "nap", "person");

        AXIS_MoM_WS ws = new AXIS_MoM_WS();

        Place obj2 = new Place();

        lierEntity(ws, nap, "sameas", napDB);

        obj2.setURI(napDB.getURI());
        obj2.constructEntity();
        System.out.println((Entity) obj2);
        obj2.constructPlace(true);

        System.out.println("obj = " + obj2);
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

        System.out.println("-!- Entité ajoutée : " + e2);

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
        System.out.println("e11:" + e11);
        obj.insertLocation(p22);

        Object obj1 = new Object();
        obj1.setURI(obj.getURI());
        obj1.constructEntity();
        System.out.println(obj1.getName() + ":" + obj1.getImage() + ":" + obj1.getType() + ":" + obj1.getURI());
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
        for (int i = 0; i < tab.length; i++) {
            System.out.println("Entity trouvée : " + tab[i]);
        }
    }
    
    public static void testRechercheComments() {
        
        AXIS_MoM_WS ws = new AXIS_MoM_WS();
        
        Comment[] tab = ws.LoadComment(null);
        //System.out.println("test");
        for(int i=0; i<tab.length;i++) {
            System.out.println("Comment trouvée : "+tab[i]);
        }
    }
    
    public static void testComments() {
        
        AXIS_MoM_WS ws = new AXIS_MoM_WS();
        
        Entity joconde = new Entity("La Joconde", "https://download.vikidia.org/vikidia/fr/images/thumb/1/13/La_gioconda.jpg/200px-La_gioconda.jpg", "object");
        
        joconde = ws.AddEntity(joconde);
        
        Comment c = new Comment(null, "loan", "loan@loan.com", "super tableau", false, "02-02-2016", joconde);
        c = ws.AddComment(c, joconde);
        
        Comment[] tab = ws.LoadComment(null);
        //System.out.println("test");
        for(int i=0; i<tab.length;i++) {
            System.out.println("Comment trouvée : "+tab[i]);
        }
        
        System.out.println("----");
        ws.GrantComment(c);
        ws.RemoveComment(c, joconde);
        
        Comment[] tab2 = ws.LoadComment(joconde);
        //System.out.println("test");
        for(int i=0; i<tab2.length;i++) {
            System.out.println("Comment trouvée : "+tab2[i]);
        }
        
        
    }
}
