/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.UUID;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import Dialog.Entity;
import Dialog.PropertyAdmin;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

/**
 *
 * @author APP-Riad.belmahi
 */
public class Connector {

    public static void main(String args[]) {
        //test

//        System.out.println("main");
//        
//        String myUID = insert("Entity", "RegOfPhysicalPerson");
//        insert(myUID, "label", "Martin Luther King", "fr");
//        
//        Model m = loadModels("test");
//        System.out.println(m.toString());
        Entity e = new Entity("http://dbpedia.org/resource/Paris", null, null, null);
        // String uri = e.getURI().toString();
        entityBrowser(e);
        // String test = "Racine";
        // selectlodFromKeyWord(test);
        // selectlodFromEntity(e);
        // e = selectlodFromEntity(e);
        //System.out.println(e);

    }

    public static Model loadModels(String url) { //mélanoche

        String serviceURI = "http://localhost:3030/ds";
        DatasetAccessor accessor;
        accessor = DatasetAccessorFactory.createHTTP(serviceURI);

        Model model = accessor.getModel();

        return model;
    }

    public static Model executeQueryConstruct(String str) {

        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", " PREFIX axis: <http://titan.be/axis-csrm/datamodel/ontology/0.3#> "
                + str);

        Model constructModel = qe.execConstruct();

        return constructModel;
        //pas prio
    }

    public static Model selectFromEntity(String uri) { //robine
        //on construct toutes les propriétés et valeurs de l'URI passé en paramètre
        QueryExecution qe = QueryExecutionFactory.sparqlService(
                //                "http://localhost:3030/ds/query", String.format(
                //                "PREFIX poc: <http://titan.be/axis-poc2015/>" +
                //                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                //                "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.2#>" +
                //                "construct {%s ?p ?o}" +
                //                "WHERE { ?s ?p ?o }", uri));
                //        
                "http://localhost:3030/ds/query", String.format(
                        "PREFIX poc: <http://titan.be/axis-poc2015/>"
                        + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                        + "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
                        + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                        + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                        + "construct{?s ?p ?o}"
                        + "WHERE { ?s ?p ?o . {"
                        + "SELECT * WHERE {"
                        + "<%s> ?p ?o }"
                        + "} }", uri));

        Model m = qe.execConstruct();

        return m;
    }

    public static ResultSet selectFromEntity(String s, String p, String o) { //robine
        //on construct toutes les propriétés et valeurs de l'URI passé en paramètre
        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", String.format(
                        "PREFIX poc: <http://titan.be/axis-poc2015/>"
                        + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                        + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                        + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                        + "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
                        + "SELECT ?s ?p ?o "
                        + "WHERE {"
                        + "  %s %s %s "
                        + "}", s, p, o));

        ResultSet rs = qe.execSelect();

        return rs;
    }

    public static Model selectFromEntityWithPredicat(String uri, String predicat) { //Robine
        //on construct toutes les propriétés et valeurs de l'URI passé en paramètre
        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", String.format(
                        "PREFIX poc: <http://titan.be/axis-poc2015/>"
                        + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                        + "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
                        + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                        + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                        + "construct{?s ?p ?o}"
                        + "WHERE { ?s ?p ?o . {"
                        + "SELECT * WHERE {"
                        + "<%s> %s ?s }"
                        + "} }", uri, predicat));

        Model m = qe.execConstruct();

        return m;
    }

    // méthode pour supprimer des charactéres
    public static ArrayList<Dialog.Property> entityBrowser(Entity e) {
        ArrayList<Dialog.Property> tProp = new ArrayList<Dialog.Property>();
        String uri = e.getURI().toString();
        Model m = lodQuery(uri, "http://dbpedia.org/ontology/abstract", "?o");
        tProp = searchPropertyFromModel(m, tProp, "description");
        switch (e.getType()) {
            case "person":
                m = lodQuery(uri, "http://dbpedia.org/property/dateOfBirth", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person");
                m = lodQuery(uri, "http://dbpedia.org/property/dateOfDeath", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person");
                m = lodQuery(uri, "http://dbpedia.org/ontology/birthPlace", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person");
                m = lodQuery(uri, "http://dbpedia.org/property/mother", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person");
                m = lodQuery(uri, "http://dbpedia.org/property/father", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person");
                m = lodQuery(uri, "http://dbpedia.org/ontology/restingPlace", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person");
                break;
            case "object":
                m = lodQuery(uri, "http://dbpedia.org/property/artist", "?o");
                tProp = searchPropertyFromModel(m, tProp, "object");
                m = lodQuery(uri, "http://dbpedia.org/property/author", "?o");
                tProp = searchPropertyFromModel(m, tProp, "object");
                m = lodQuery(uri, "http://dbpedia.org/ontology/location", "?o");
                tProp = searchPropertyFromModel(m, tProp, "object");
                m = lodQuery(uri, "http://dbpedia.org/property/location", "?o");
                tProp = searchPropertyFromModel(m, tProp, "object");
                break;
            case "location":
                m = lodQuery(uri, "http://dbpedia.org/ontology/region", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location");
                m = lodQuery(uri, "http://dbpedia.org/ontology/country", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location");
                m = lodQuery(uri, "http://dbpedia.org/ontology/postalCode", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location");
                break;
            case "event":
                
                break;
            case "organisation":
                m = lodQuery(uri, "http://dbpedia.org/ontology/location", "?o");
                tProp = searchPropertyFromModel(m, tProp, "organisation");
                m = lodQuery(uri, "http://dbpedia.org/property/location", "?o");
                tProp = searchPropertyFromModel(m, tProp, "organisation");
                m = lodQuery(uri, "http://dbpedia.org/property/introduced", "?o");
                tProp = searchPropertyFromModel(m, tProp, "organisation");
                break;
            case "activity":
                
                break;
        }
        

//        for (int i = 0; i < tProp.size(); i++) {
//            System.out.println("prop n°" + i + "  :  " + tProp.get(i));
//        }
        return tProp;
    }

    public static ArrayList<Dialog.Property> searchPropertyFromModel(Model m, ArrayList<Dialog.Property> tProp, String type) {
        StmtIterator iter = m.listStatements();
        while (iter.hasNext()) {
            Dialog.Property p2 = new Dialog.Property(null, null, null, null);
            Statement stmt = (Statement) iter.next();
//          System.out.println("test : "+stmt.asTriple().toString());
            org.apache.jena.rdf.model.Property predicate = stmt.getPredicate();
            String p = predicate.toString();
            RDFNode object = stmt.getObject();
//            System.out.println("----------------------");
//            System.out.println("Predicate :" + p);
//            System.out.println("Subject :" + subject.toString());
//            System.out.println("Object:" + object.toString());
//            System.out.println("----------------------");
            switch (p) {
                case "http://dbpedia.org/property/artist":
                    p2.setName("author");
                    break;
                case "http://dbpedia.org/property/author":
                    p2.setName("author");
                    break;
                case "http://dbpedia.org/property/dateOfBirth":
                    p2.setName("birthdate");
                    break;
//                   case "http://dbpedia.org/ontology/birthDate":
//                    p2.setName("birthdate");
//                    break;
                case "http://dbpedia.org/property/dateOfDeath":
                    p2.setName("deathdate");
                    break;
                case "http://dbpedia.org/ontology/birthPlace":
                    p2.setName("birthplace");
                    break;
                case "http://dbpedia.org/ontology/restingPlace":
                    p2.setName("restinplace");
                    break;
                case "http://dbpedia.org/ontology/abstract":
                    String test = stmt.getObject().asLiteral().getLanguage();
                    if (test.equals("fr")) {
                        p2.setName(object.asLiteral().getString());
                        p2.setName("description");
                    } else {
                        p2.setName("default");
                    }

                    break;
                case "http://www.w3.org/2002/07/owl#sameAs":
                    p2.setName("sameas");
                    break;
                case "http://dbpedia.org/property/mother":
                    p2.setName("mother");
                    break;
                case "http://dbpedia.org/property/father":
                    p2.setName("father");
                    break;
                case "http://dbpedia.org/ontology/country":
                    p2.setName("country");
                    break;
                case "http://dbpedia.org/ontology/region":
                    p2.setName("region");
                    break;
                case "http://dbpedia.org/ontology/postalCode":
                    p2.setName("postalCode");
                    break;
                case "http://dbpedia.org/property/birthPlace":
                    p2.setName("birthPlace");
                    break;
                case "http://dbpedia.org/ontology/location":
                    p2.setName("location");
                    break;
                case "http://dbpedia.org/property/location":
                    p2.setName("location");
                    break;
                case "http://dbpedia.org/property/introduced":
                    p2.setName("dateOfCreation");
                    break;
                default:
                    p2.setName("default");
                    break;
            }
            p2.setValue(object.toString().replace("^^http://www.w3.org/2001/XMLSchema#date", "").replace("@fr", ""));

            if (object.isResource()) {
                p2.setType("uri");
                String uri2 = object.toString();
                Entity e2 = new Entity(uri2, "", "", type);
                p2.setEnt(selectlodFromEntity(e2));
            } else {
                p2.setType("fr");
                p2.setEnt(null);
            }
            if (!p2.getName().contains("default")) {
//                System.out.println("--------------------");
//                System.out.println("entity :" + p2.getEnt());
//                System.out.println("name :" + p2.getName());
//                System.out.println("type :" + p2.getType());
//                System.out.println("value :" + p2.getValue());  
//                System.out.println("--------------------");
                tProp.add(p2);
            }

        }

        return tProp;
    }

    private static Model lodQuery(String s, String p, String o) {
        String DBQueryString = "PREFIX dbont: <http://dbpedia.org/ontology/> "
                + "PREFIX dbp: <http://dbpedia.org/property/>"
                + "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"
                + "PREFIX dbr: <http://dbpedia.org/resource/>"
                + "PREFIX type: <http://dbpedia.org/class/yago/>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                // on ajoute  ?s owl:sameAs ?Entity" aprés le construct pour comparer avec les resultats locales
                + "construct where {<" + s + "> <" + p + "> " + o + "}";
        Query DBquery = QueryFactory.create(DBQueryString);
        QueryExecution qDBexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", DBquery);

        Model m = qDBexec.execConstruct();

        return m;
    }

    public static Entity selectlodFromEntity(Entity e) {

        String uri = e.getURI();
//        Model m = lodQuery(uri, "http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "?o");
//        e = searchFromModel(m, e);
//        if (e.getType() == null) {
//            m = lodQuery(uri, "http://dbpedia.org/property/type", "?o");
//            e = searchFromModel(m, e);
//        }
        Model m = lodQuery(uri, "http://dbpedia.org/ontology/thumbnail", "?o");
        e = searchFromModel(m, e);
        m = lodQuery(uri, "http://www.w3.org/2000/01/rdf-schema#label", "?o");
        e = searchFromModel(m, e);
        m = lodQuery(uri, "http://dbpedia.org/ontology/alias", "?o");
        e = searchFromModel(m, e);
        m = lodQuery(uri, "http://dbpedia.org/ontology/birthName", "?o");
        e = searchFromModel(m, e);

      //  System.out.println("l'entité : "+e);
        return e;
    }

    public static Entity searchFromModel(Model m, Entity e) {
        StmtIterator iter = m.listStatements();
        while (iter.hasNext()) {

            Statement stmt = (Statement) iter.next();
            org.apache.jena.rdf.model.Property predicate = stmt.getPredicate();
            String p = predicate.toString();
            RDFNode object = stmt.getObject();

            switch (p) {
                // si le predicat est un type
//                case "http://www.w3.org/1999/02/22-rdf-syntax-ns#type":
//                    String typ = stmt.getObject().toString();
//                    if (typ.contains("Object")) {
//                        e.setType("object");
//                    }
//                    if (typ.contains("Event")) {
//                        e.setType("event");
//                    }
//                    if (typ.contains("Person")) {
//                        e.setType("person");
//                    }
//                    if (typ.contains("location") || typ.contains("Place") || typ.contains("State")) {
//                        e.setType("location");
//                    }
//                    if (typ.contains("Activity")) {
//                        e.setType("activity");
//                    }
//                    if (typ.contains("Organisation") || (typ.contains("Museum"))) {
//                        e.setType("organisation");
//                    }
//
//                    break;
//                case "http://dbpedia.org/property/type":
//                    String typ2 = stmt.getObject().toString();
//                    if (typ2.contains("Object")) {
//                        e.setType("object");
//                    }
//                    if (typ2.contains("Event")) {
//                        e.setType("event");
//                    }
//                    if (typ2.contains("Person")) {
//                        e.setType("person");
//                    }
//                    if (typ2.contains("Location") || typ2.contains("Place") || typ2.contains("State")) {
//                        e.setType("location");
//                    }
//                    if (typ2.contains("Activity")) {
//                        e.setType("activity");
//                    }
//                    if (typ2.contains("Organisation") || typ2.contains("Museum")) {
//                        e.setType("organisation");
//                    }
//
//                    break;
                case "http://dbpedia.org/ontology/thumbnail":
                    e.setImage(object.toString());
                    break;

                case "http://www.w3.org/2000/01/rdf-schema#label":
                    String test = stmt.getObject().asLiteral().getLanguage();
                    if (test.equals("fr")) {
                        e.setName(object.toString().replace("@fr", ""));
                    }
                    break;
                case "http://dbpedia.org/ontology/alias":
                    e.setName(object.toString().replace("@en", ""));
                    break;
                case "http://dbpedia.org/ontology/birthName":
                    e.setName(object.toString().replace("@en", ""));
                    break;
                case "http://dbpedia.org/property/name":
                    e.setName(object.toString().replace("@en", ""));
                    break;

            }
        }
        return e;
    }

    public static ArrayList<Entity> selectlodFromKeyWord(String keyword) {
        //riad

        //on construct toutes les propriétés et valeurs de l'URI passé en paramètre
        // l'URI est externe, et fait donc référence à un lien dbpedia, freebase...
        ArrayList<Entity> entities = new ArrayList<>();

        Model m = lodQuery("http://dbpedia.org/resource/" + keyword, "?p", "?o");

        StmtIterator iter = m.listStatements();
        Entity e = new Entity();
        while (iter.hasNext()) {

            Statement stmt = (Statement) iter.next();
            Resource subject = stmt.getSubject();
            org.apache.jena.rdf.model.Property predicate = stmt.getPredicate();
            String p = predicate.toString();
            RDFNode object = stmt.getObject();

            // 
//            System.out.println("Predicate :" + p);
//            System.out.println("Subject :" + subject);
//            System.out.println("Object:" + object);
            e.setURI(subject.toString());
            // System.out.println("----------------------------");
            // on ajoute l'uri qui sera notre sujet à l'entité
            // System.out.println("uri : "+e.getURI());
            // on vérifie les prédicats
            if (p.contains("wikiPageDisambiguates")) {
                Resource r2 = stmt.getResource();
                String rString = r2.toString().substring(28);
                String rString2 = new String(rString.getBytes(), Charset.forName("UTF-8"));
                // System.out.println("r2 :"+rString2);
                selectlodFromKeyWord(rString2);
                // System.out.println("wikiPageDisambiguates:"+r2);
            } else {
                searchFromModel(m, e);
            }
            // on ajoute nos entités a notre tableau 
            entities.add(e);
        }
        System.out.println("entity :" + e);
        System.out.println("les entites : " + entities);
        return entities;
    }

    public static String[] selectAllEntitiesURI() {

        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", String.format(
                "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "select ?o where {?s axis-datamodel:uses ?o ." +
"	?s rdf:type axis-datamodel:Entity}"));


        
        ResultSet rs = qe.execSelect();
        
        
        
        List<QuerySolution> mList = null;
        mList = ResultSetFormatter.toList(rs);

        ArrayList<String> tab = new ArrayList<>();

        //System.out.println("mlist size = "+mList.size());
        for (int i = 0; i < mList.size(); i++) {
            System.out.println(mList.get(i).getResource("o").toString());
            tab.add(mList.get(i).getResource("o").toString());
        }

        String[] ret = new String[tab.size()];
        return (String[]) tab.toArray(ret);
    }

    public static String insert(String p, String o) { //robine
        String req = "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
                + "PREFIX poc: <http://titan.be/axis-poc2015/>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX dbont: <http://dbpedia.org/ontology/>"
                + "INSERT DATA { "
                + " poc:%s "
                + " %s "
                + " %s "
                + ".}";

        String id = UUID.randomUUID().toString();
        System.out.println(String.format("Adding %s", id));
        UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                UpdateFactory.create(String.format(req, id, p, o)),
                "http://localhost:3030/ds/update");
        upp.execute();
        return "http://titan.be/axis-poc2015/" + id;
    }

    public static boolean insert(String s, String p, String o) { //robine
        String req = "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
                + "PREFIX poc: <http://titan.be/axis-poc2015/>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX dbont: <http://dbpedia.org/ontology/>"
                + "INSERT DATA { "
                + "<%s>"
                + " %s "
                + "<%s>"
                + ".}";

        UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                UpdateFactory.create(String.format(req, s, p, o)),
                "http://localhost:3030/ds/update");
        upp.execute();

        return true;
    }

    public static boolean insert(String s, String p, String o, String lang) { //robine
        String req = "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
                + "PREFIX poc: <http://titan.be/axis-poc2015/>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX schema: <https://schema.org/>"
                + "PREFIX dbont: <http://dbpedia.org/ontology/>"
                + "INSERT DATA { "
                + "<%s>"
                + " %s "
                + "\"%s\"%s"
                + ".}";
        if (lang.equals("file")) {
            lang = "";
        } else {
            lang = "@" + lang;
        }
        UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                UpdateFactory.create(String.format(req, s, p, o, lang)),
                "http://localhost:3030/ds/update");

        upp.execute();

        return true;
    }

    public static boolean update(String s, String p, String o) {
        //pas prio
        return true;
    }

    public static boolean delete(String s, String p, String o) {
        //pas prio
        return true;
    }

}
