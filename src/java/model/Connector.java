/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Dialog.Comment;
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
import Dialog.Property;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static org.apache.commons.lang3.StringUtils.capitalize;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.update.UpdateAction;

/**
 *
 * @author APP-Riad.belmahi
 */
public class Connector {
    
    public static String $PREFIXS = "PREFIX poc: <http://titan.be/axis-poc2015/>"
                        + " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                        + "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.4#>"
                        + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                        + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                        + "PREFIX dbont: <http://dbpedia.org/ontology/> "
                        + "PREFIX dbp: <http://dbpedia.org/property/>"
                        + "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"
                        + "PREFIX dbr: <http://dbpedia.org/resource/>"
                        + "PREFIX type: <http://dbpedia.org/class/yago/>"
                        + "PREFIX schema: <https://schema.org/> ";
    
    public static void main(String args[]) {

        String test = "RACINE";
        selectlodFromKeyWord(test);

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
                "http://localhost:3030/ds/query", $PREFIXS+ str);

        Model constructModel = qe.execConstruct();

        qe.close();
        return constructModel;
    }

    public static Model selectFromEntity(String uri) { //robine
        //on construct toutes les propriétés et valeurs de l'URI passé en paramètre
        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", String.format(
                        $PREFIXS
                        + "construct{?s ?p ?o}"
                        + "WHERE { ?s ?p ?o . {"
                        + "SELECT * WHERE {"
                        + "<%s> ?p ?o }"
                        + "} }", uri));

        Model m = qe.execConstruct();

        qe.close();
        return m;
    }

    public static ResultSet selectFromEntity(String s, String p, String o) { //robine
        //on construct toutes les propriétés et valeurs de l'URI passé en paramètre
        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", String.format(
                        $PREFIXS
                        + "SELECT ?s ?p ?o "
                        + "WHERE {"
                        + "  %s %s %s "
                        + "}", s, p, o));

        ResultSet rs = qe.execSelect();
        //qe.close(); //IMPOSSIBLE car sinon rs est vide
        return rs;
    }

    public static Model selectFromEntityWithPredicat(String uri, String predicat) { //Robine
        //on construct toutes les propriétés et valeurs de l'URI passé en paramètre
        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", String.format(
                        $PREFIXS
                        + "construct{?s ?p ?o}"
                        + "WHERE { ?s ?p ?o . {"
                        + "SELECT * WHERE {"
                        + "<%s> %s ?s }"
                        + "} }", uri, predicat));

        Model m = qe.execConstruct();

        qe.close();
        return m;
    }

    // méthode pour supprimer des charactéres
    public static ArrayList<Dialog.Property> entityBrowser(Entity e) {
//        
//        long startTime = System.currentTimeMillis();
        
        ArrayList<Dialog.Property> tProp = new ArrayList<Dialog.Property>();
        String uri = e.getURI().toString();
        Model m = lodQuery(uri, "http://dbpedia.org/ontology/abstract", "?o");
        tProp = searchPropertyFromModel(m, tProp, null);
        //System.out.println("le type :"+e.getType().toString());
        switch (e.getType()) {
            case "person":
                m = lodQuery(uri, "http://dbpedia.org/property/dateOfBirth", "?o");
                tProp = searchPropertyFromModel(m, tProp, null);
                m = lodQuery(uri, "http://dbpedia.org/property/dateOfDeath", "?o");
                tProp = searchPropertyFromModel(m, tProp, null);
                m = lodQuery(uri, "http://dbpedia.org/ontology/birthPlace", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location");
                m = lodQuery(uri, "http://dbpedia.org/property/mother", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person");
                m = lodQuery(uri, "http://dbpedia.org/property/father", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person");
                m = lodQuery(uri, "http://dbpedia.org/ontology/restingPlace", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location");
                break;
            case "object":
                m = lodQuery(uri, "http://dbpedia.org/property/artist", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person");
                m = lodQuery(uri, "http://dbpedia.org/property/author", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person");
                m = lodQuery(uri, "http://dbpedia.org/ontology/location", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location");
                m = lodQuery(uri, "http://dbpedia.org/property/location", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location");
                m = lodQuery(uri, "http://dbpedia.org/property/city", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location");
                m = lodQuery(uri, "http://dbpedia.org/property/museum", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location");
                break;
            case "location":
                m = lodQuery(uri, "http://dbpedia.org/ontology/region", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location");
                m = lodQuery(uri, "http://dbpedia.org/ontology/country", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location");
                m = lodQuery(uri, "http://dbpedia.org/ontology/postalCode", "?o");
                tProp = searchPropertyFromModel(m, tProp, null);
                m = lodQuery(uri, "http://dbpedia.org/ontology/yearMeanC", "?o");
                tProp = searchPropertyFromModel(m, tProp, null);
                m = lodQuery(uri, "http://dbpedia.org/ontology/language", "?o");
                tProp = searchPropertyFromModel(m, tProp, null);
                m = lodQuery(uri, "http://dbpedia.org/property/leaderName", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person");
                break;
            case "event":

                break;
            case "organisation":
                m = lodQuery(uri, "http://dbpedia.org/ontology/location", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location");
                m = lodQuery(uri, "http://dbpedia.org/property/location", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location");
                m = lodQuery(uri, "http://dbpedia.org/property/introduced", "?o");
                tProp = searchPropertyFromModel(m, tProp, null);
                m = lodQuery(uri, "http://dbpedia.org/property/managerClub", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person");
                m = lodQuery(uri, "http://dbpedia.org/property/mayor", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person");
                m = lodQuery(uri, "http://dbpedia.org/property/leaderName", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person");

                break;
            case "activity":

                break;
        }

//        for (int i = 0; i < tProp.size(); i++) {
//            System.out.println("prop n°" + i + "  :  " + tProp.get(i));
//        }
//        
//        long endTime = System.currentTimeMillis();
//        System.out.println("_____ FIN FONCTION SELECTLODENTITY: "+(endTime-startTime));
        return tProp;
    }

    public static ArrayList<Dialog.Property> searchPropertyFromModel(Model m, ArrayList<Dialog.Property> tProp, String type) {
        StmtIterator iter = m.listStatements();
        ArrayList<Entity> ale = new ArrayList<Entity>();
        int setted = 0;
        Dialog.Property p2 = new Dialog.Property(null, null, null, null);
        while (iter.hasNext()) {
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
                // les travaux de la personne
                case "http://dbpedia.org/property/works":
                    p2.setName("isAuthorOf");
                    break;
                case "http://dbpedia.org/property/dateOfBirth":
                    p2.setName("birthdate");
                    break;

                // le maire d'une organisation de type ville
                case "http://dbpedia.org/ontology/language":
                    p2.setName("language");
                    break;
                case "http://dbpedia.org/ontology/mayor":
                    p2.setName("mayor");
                    break;
                case "http://dbpedia.org/property/leaderName":
                    p2.setName("leader");
                    break;
                // le chef d'une organisation

                case "http://dbpedia.org/ontology/managerClub ":
                    p2.setName("manager");
                    break;

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
                        p2.setEnt(null);
                        p2.setName(object.asLiteral().getString());
                        //System.out.println(object.asLiteral().getString());
                        p2.setName("description");
                        p2.setValue(object.asLiteral().getString());
                    } else if(p2.getType()==null){
                        p2.setName("default");
                    }

                    break;
                case "http://www.w3.org/2002/07/owl#sameAs":
                    p2.setName("sameas");
                    break;
                case "http://dbpedia.org/ontology/wikiPageDisambiguates":
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
                    p2.setName("postalcode");
                    break;
                case "http://dbpedia.org/ontology/location":
                    p2.setName("location");
                    break;
                case "http://dbpedia.org/property/location":
                    p2.setName("location");
                    break;
                case "http://dbpedia.org/property/city":
                    p2.setName("location");
                    break;
                case "http://dbpedia.org/property/museum":
                    p2.setName("location");
                    break;
                case "http://dbpedia.org/property/introduced":
                    p2.setName("dateOfCreation");
                    break;

                case "http://dbpedia.org/ontology/yearMeanC":
                    p2.setName("tempMean");
                    break;
                default:
                    p2.setName("default");
                    break;
            }
            
            if (object.isResource()) {
                p2.setType("uri");
                String uri2 = object.toString();
                Entity e2 = new Entity(uri2, "", "", type);
                ale.add(selectlodFromEntity(e2));
                Entity[] ret = new Entity[ale.size()];
                p2.setEnt((Entity[]) ale.toArray(ret));

            } else {
                p2.setType("fr");
                p2.setEnt(null);
                if(p2.getValue() == null)
                    p2.setValue(object.toString().replace("^^http://www.w3.org/2001/XMLSchema#date", "").replace("@fr", "").replace("@en", ""));
//                tProp.add(p2);
            }
            //System.out.println("p>>" + p+"; p2>>>"+p2);
            if (!p2.getName().contains("default")) {
////                System.out.println("--------------------");
////                System.out.println("entity :" + p2.getEnt());
////                System.out.println("name :" + p2.getName());
////                System.out.println("type :" + p2.getType());
////                System.out.println("value :" + p2.getValue());  
////                System.out.println("--------------------");
                int s = tProp.size();
                if(s==0){
                    tProp.add(p2);
                }else{
                    int index = -1;
                    for (int i = 0; i < s; i++) {
                        if (tProp.get(i).getName() == p2.getName()) {
                            //System.out.println("name>>>>>"+p2.getName());
                            index =i;
                        }
                    }
                    if(index != -1){
                        if (!tProp.get(index).getType().toString().equals("uri") ) {
                            tProp.get(index).setValue(p2.getValue());
                            tProp.get(index).setType(p2.getType());
                            tProp.get(index).setName(p2.getName());
                            tProp.get(index).setEnt(p2.getEnt());
                        } else {
                            //System.out.println("else2");
                            tProp.get(index).setType(p2.getType());
                            tProp.get(index).setName(p2.getName());
                            tProp.get(index).setValue(null);
                            ArrayList<Entity> al = new ArrayList<Entity>();
                            
                            int s1 = tProp.get(index).getEnt().length;
                            if(setted==0){
                                for (int j = 0; j < s1; j++) {
                                    al.add(tProp.get(index).getEnt()[j]);
                                }
                            }
                            
                            for (int j = 0; j < s1; j++) {
                                al.add(p2.getEnt()[j]);
                            }
                            Entity [] ent =new Entity[al.size()];
                            tProp.get(index).setEnt((Entity[]) al.toArray(ent));
                        }
                    }else{
                        tProp.add(p2);
                        setted = 1;
                    }
                }
            }

        }

//        tProp.add(p2);
        return tProp;
    }

    private static Model lodQueryAmbigious(String s) {
        String DBQueryString = $PREFIXS
                // on ajoute  ?s owl:sameAs ?Entity" aprés le construct pour comparer avec les resultats locales
                + "construct where {<http://dbpedia.org/resource/" + s + "> dbont:wikiPageDisambiguates ?o}";
        Query DBquery = QueryFactory.create(DBQueryString);
        QueryExecution qDBexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", DBquery);

        Model m = qDBexec.execConstruct();

        qDBexec.close();
        return m;
    }

    private static Model lodQuery(String s, String p, String o) {
        String DBQueryString = $PREFIXS
                // on ajoute  ?s owl:sameAs ?Entity" aprés le construct pour comparer avec les resultats locales
                + "construct where {<" + s + "> <" + p + "> " + o + "}";
        Query DBquery = QueryFactory.create(DBQueryString);
        QueryExecution qDBexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", DBquery);

        Model m = qDBexec.execConstruct();

        qDBexec.close();
        return m;
    }

    public static Entity selectlodFromEntity(Entity e) {

        //long startTime = System.currentTimeMillis();
        
        String uri = e.getURI();
        Model m = lodQuery(uri, "http://dbpedia.org/ontology/thumbnail", "?o");
        e = searchFromModel(m, e);
        if (e.getType() == null) {
            m = lodQuery(uri, "http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "?o");
            e = searchFromModel(m, e);
            if (e.getType() == null) {
                m = lodQuery(uri, "http://dbpedia.org/property/type", "?o");
                e = searchFromModel(m, e);
            }
        }

        m = lodQuery(uri, "http://www.w3.org/2000/01/rdf-schema#label", "?o");
        e = searchFromModel(m, e);
        m = lodQuery(uri, "http://dbpedia.org/ontology/alias", "?o");
        e = searchFromModel(m, e);
        m = lodQuery(uri, "http://dbpedia.org/ontology/birthName", "?o");
        e = searchFromModel(m, e);
        m = lodQuery(uri, "http://dbpedia.org/ontology/birthname", "?o");
        e = searchFromModel(m, e);

        //long endTime = System.currentTimeMillis();
        //System.out.println("_____ FIN FONCTION SELECTLODENTITY: "+(endTime-startTime));
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
                case "http://www.w3.org/1999/02/22-rdf-syntax-ns#type":
                    String typ = stmt.getObject().toString();
                    if (typ.contains("Organisation") || (typ.contains("Museum"))) {
                        e.setType("organisation");
                    } else if (typ.contains("Event")) {
                        e.setType("event");
                    } else if (typ.contains("Person")) {
                        e.setType("person");
                    } else if (typ.contains("location") || typ.contains("Place") || typ.contains("State")) {
                        e.setType("location");
                    } else if (typ.contains("Activity")) {
                        e.setType("activity");
                    }
                    if (e.getType() == null) {
                        e.setType("object");
                    }

                    break;
                case "http://dbpedia.org/property/type":
                    String typ2 = stmt.getObject().toString();
                    if (typ2.contains("Organisation") || (typ2.contains("Museum"))) {
                        e.setType("organisation");
                    } else if (typ2.contains("Event")) {
                        e.setType("event");
                    } else if (typ2.contains("Person")) {
                        e.setType("person");
                    } else if (typ2.contains("location") || typ2.contains("Place") || typ2.contains("State")) {
                        e.setType("location");
                    } else if (typ2.contains("Activity")) {
                        e.setType("activity");
                    }
                    if (e.getType() == null) {
                        e.setType("object");
                    }

                    break;
                case "http://dbpedia.org/ontology/thumbnail":
                    e.setImage(object.toString());
                    break;

                case "http://www.w3.org/2000/01/rdf-schema#label":
                    String test = stmt.getObject().asLiteral().getLanguage();
                    switch (test) {
                        case "fr":
                            e.setName(object.toString().replace("@fr", ""));
                            break;
                        case "en":
                            e.setName(object.toString().replace("@en", ""));
                            break;
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
       // String capKeyword = capitalize(keyword);
        keyword = keyword.substring(0,1).toUpperCase() + keyword.substring(1).toLowerCase();
        Model m = lodQueryAmbigious(keyword);
        StmtIterator iter = m.listStatements();
       // System.out.println("iter" + iter.toString());
        while (iter.hasNext()) {

            Statement stmt = (Statement) iter.next();
            //  System.out.println("stmt"+stmt.toString());
 
            Entity e = new Entity();
            RDFNode object = stmt.getObject();
            String uri = object.toString();
            e.setURI(uri);
            Entity e2 = selectlodFromEntity(e);
            entities.add(e2);

        }

//        for (int i = 0; i < entities.size(); i++) {
//            System.out.println("entiity n°" + i + "  :  " + entities.get(i));
//        }
        return entities;
    }

    public static Entity[] selectAllEntitiesURI() {

        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", String.format(
                        $PREFIXS
                        + "select ?o ?n where {?s axis-datamodel:uses ?o ."
                        + "	?s rdf:type axis-datamodel:Entity ."
                        + "     ?o rdfs:label ?n}"));

        ResultSet rs = qe.execSelect();

        ArrayList<Entity> tab = new ArrayList<>();
        //List<QuerySolution> mList = null;
        while(rs.hasNext()){
            QuerySolution n = rs.next();
            Entity e = new Entity();
            e.setURI(n.get("o").asResource().toString());
            e.setName(n.get("n").asLiteral().toString());
            
            //n.get("n").
            tab.add(e);
        }
        //mList = ResultSetFormatter.toList(rs);
        qe.close();
        
//
//        //System.out.println("mlist size = "+mList.size());
//        for (int i = 0; i < mList.size(); i++) {
//            //System.out.println(mList.get(i).getResource("o").toString());
//            Entity e = new Entity();
//            e.setURI(mList.get(i).getResource("o").toString());
//            e.setName(mList.get(i).getResource("n").toString());
//            tab.add(e);
//        }

        Entity[] ret = new Entity[tab.size()];
        return (Entity[]) tab.toArray(ret);
    }
    
    public static Comment[] selectAllComments(Entity e) {
        
        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", String.format(
                        $PREFIXS
                        + "select ?c ?creator ?content ?creationDate ?validate ?email where {<%s> axis-datamodel:hasRepresentation ?regof ."
                        + "     ?regof rdf:type axis-datamodel:RegOfInformationItem ."
                        + "     ?regof axis-datamodel:hasComment ?c ."
                        + "	?c rdf:type axis-datamodel:Comment ."
                        + "     ?c axis-datamodel:creator ?creator ."
                        + "     ?c axis-datamodel:content ?content ."
                        + "     ?c axis-datamodel:creationDate ?creationDate ."
                        + "     ?c axis-datamodel:validate ?validate ."
                        + "     ?c axis-datamodel:email ?email}", e.getURI()));
           
        ResultSet rs = qe.execSelect();
        ArrayList<Comment> tab = new ArrayList<>();
        
        while(rs.hasNext()){
            QuerySolution n = rs.next();
            Comment c = new Comment();
            c.setId(n.get("c").asResource().toString());
            c.setAuthorName(n.get("creator").asLiteral().getString());
            c.setComment(n.get("content").asLiteral().getString());
            c.setEmail(n.get("email").asLiteral().getString());
            c.setCreateDt(n.get("creationDate").asLiteral().getString());
            c.setValidated(Boolean.valueOf(n.get("validate").asLiteral().getString()));
            c.setEntity(e);
            tab.add(c);
        }
        
        qe.close();
        

        Comment[] ret = new Comment[tab.size()];
        return (Comment[]) tab.toArray(ret);
    }
    
    public static Comment[] selectAllComments() {
        
        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", String.format(
                        $PREFIXS
                        + "select ?e ?c ?creator ?content ?creationDate ?validate ?email where {?e axis-datamodel:hasRepresentation ?regof ."
                        + "     ?regof rdf:type axis-datamodel:RegOfInformationItem ."
                        + "     ?regof axis-datamodel:hasComment ?c ."
                        + "	?c rdf:type axis-datamodel:Comment ."
                        + "?c axis-datamodel:creator ?creator ."
                        + "?c axis-datamodel:content ?content ."
                        + "?c axis-datamodel:creationDate ?creationDate ."
                        + "?c axis-datamodel:validate ?validate ."
                        + "?c axis-datamodel:email ?email}"));

        ResultSet rs = qe.execSelect();
        ArrayList<Comment> tab = new ArrayList<>();
        
        while(rs.hasNext()){
            QuerySolution n = rs.next();
            Comment c = new Comment();
            c.setId(n.get("c").asResource().toString());
            c.setAuthorName(n.get("creator").asLiteral().getString());
            c.setComment(n.get("content").asLiteral().getString());
            c.setEmail(n.get("email").asLiteral().getString());
            c.setCreateDt(n.get("creationDate").asLiteral().getString());
            c.setValidated(Boolean.valueOf(n.get("validate").asLiteral().getString()));
            Entity e = new Entity();
            e.setURI(n.get("e").asResource().toString());
            e.constructEntity();
            c.setEntity(e);
            tab.add(c);
        }
        
        qe.close();
        

        Comment[] ret = new Comment[tab.size()];
        return (Comment[]) tab.toArray(ret);
    }

    public static String insert(String p, String o) { //robine
        String req = $PREFIXS
                + "INSERT DATA { "
                + " poc:%s "
                + " %s "
                + " %s "
                + ".}";

        String id = UUID.randomUUID().toString();
        //System.out.println(String.format("Adding %s", id));
        UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                UpdateFactory.create(String.format(req, id, p, o)),
                "http://localhost:3030/ds/update");
        upp.execute();
        return "http://titan.be/axis-poc2015/" + id;
    }

    public static boolean insert(String s, String p, String o) { //robine
        String req = $PREFIXS
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
        String req = $PREFIXS
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
    
    public static String selectRegOfEntity(String entity, String regof) {
        String uri = "null";

        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", String.format(
                        $PREFIXS
                        + "select ?ret where {<%s> axis-datamodel:hasRepresentation ?ret ."
                        + "	?ret rdf:type axis-datamodel:%s }", entity, regof));

        ResultSet rs = qe.execSelect();
        
        if(rs.hasNext()) {
            QuerySolution qs = rs.next();
            uri = qs.get("ret").toString();
        }
        
        qe.close();
        return uri;

    }
    
    

    public static boolean update(String s, String p, String o) {
        //pas prio
        return true;
    }

    public static boolean deleteLinkEntity(String s, String p, String o) {

        String req = $PREFIXS
                + "DELETE WHERE { "
                + " <%s> %s %s "
                + "}";
        
        
        UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                UpdateFactory.create(String.format(req, s, p, o)),
                "http://localhost:3030/ds/update");

        upp.execute();
        
        

        return true;
    }

}
