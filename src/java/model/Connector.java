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
import java.util.ArrayList;
import jena.query;
import static model.TestWS.startTime;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.WebContent;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.vocabulary.RDF;

/**
 *
 * @author lookout.ig2i
 */
public class Connector {

    public static String $PREFIXS = "PREFIX poc: <http://titan.be/axis-poc2015/>"
            + " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
            + "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.4#>"
            + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
            + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
            + "PREFIX dbont: <http://dbpedia.org/ontology/>"
            + "PREFIX dbp: <http://dbpedia.org/property/>"
            + "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"
            + "PREFIX dbr: <http://dbpedia.org/resource/>"
            + "PREFIX type: <http://dbpedia.org/class/yago/>"
            + "PREFIX schema: <https://schema.org/>";

    public static void main(String args[]) {

        String test = "vinci";
        selectlodFromKeyWord(test);
//        Entity e = new Entity("http://dbpedia.org/resource/Leonardo_da_Vinci", "", "", "person");
//        entityBrowser(e);
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
                "http://localhost:3030/ds/query", $PREFIXS + str);

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
        tProp = searchPropertyFromModel(m, tProp, null, null, false);
        //System.out.println("le type :"+e.getType().toString());
        switch (e.getType()) {
            case "person":
                //  m = lodQuery(uri, "http://dbpedia.org/property/dateOfBirth", "?o");
                m = lodQuery(uri, "http://dbpedia.org/property/dateOfBirth", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, "dateofbirth", false);
                m = lodQuery(uri, "http://dbpedia.org/property/birthDate", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, null, false);
                m = lodQuery(uri, "http://dbpedia.org/property/deathDate", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, null, false);
                m = lodQuery(uri, "http://dbpedia.org/property/dateOfDeath", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, null, false);

                m = lodQuery(uri, "http://dbpedia.org/ontology/birthPlace", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location", null, false);
                m = lodQuery(uri, "http://dbpedia.org/property/mother", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person", null, false);
                m = lodQuery(uri, "http://dbpedia.org/property/father", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person", null, false);
                m = lodQuery("?o", "http://dbpedia.org/property/author", uri);
                tProp = searchPropertyFromModel(m, tProp, "object", null, true);
                m = lodQuery(uri, "http://dbpedia.org/ontology/restingPlace", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location", null, false);
                m = lodQuery(uri, "http://dbpedia.org/ontology/parent", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person", null, false);
                m = lodQuery(uri, "http://dbpedia.org/ontology/child", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person", null, false);
                m = lodQuery(uri, "http://dbpedia.org/ontology/deathPlace", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location", null, false);
                m = lodQuery("?o", "http://dbpedia.org/ontology/leader", uri);
                tProp = searchPropertyFromModel(m, tProp, "organisation", null, true);
                m = lodQuery(uri, "http://dbpedia.org/ontology/website", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, null, false);
                break;
            case "object":
                m = lodQuery(uri, "http://dbpedia.org/property/artist", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person", null, false);
                m = lodQuery(uri, "http://dbpedia.org/property/author", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person", null, false);
                m = lodQuery(uri, "http://dbpedia.org/ontology/location", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location", null, false);
                m = lodQuery(uri, "http://dbpedia.org/property/location", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location", null, false);
                m = lodQuery(uri, "http://dbpedia.org/property/city", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location", null, false);
                m = lodQuery(uri, "http://dbpedia.org/property/museum", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location", "museum", false);
                m = lodQuery(uri, "http://dbpedia.org/property/website", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, null, false);
                m = lodQuery(uri, "http://dbpedia.org/property/year", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, null, false);
                m = lodQuery(uri, "http://dbpedia.org/property/type", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, null, false);

                break;
            case "location":
                m = lodQuery(uri, "http://dbpedia.org/ontology/region", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location", null, false);
                m = lodQuery(uri, "http://dbpedia.org/ontology/country", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location", null, false);
                m = lodQuery(uri, "http://dbpedia.org/ontology/postalCode", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, null, false);
                m = lodQuery(uri, "http://dbpedia.org/ontology/yearMeanC", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, null, false);
                m = lodQuery(uri, "http://dbpedia.org/ontology/language", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, null, false);
                m = lodQuery(uri, "http://dbpedia.org/property/leaderName", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person", null, false);
                m = lodQuery("?o", "http://dbpedia.org/property/birthPlace", uri);
                tProp = searchPropertyFromModel(m, tProp, "person", null, true);
                m = lodQuery("?o", "http://dbpedia.org/property/location", uri);
                tProp = searchPropertyFromModel(m, tProp, "object", null, true);
                m = lodQuery("?o", "http://dbpedia.org/ontology/location", uri);
                tProp = searchPropertyFromModel(m, tProp, "object", null, true);
                m = lodQuery(uri, "http://dbpedia.org/property/website", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, null, false);
                m = lodQuery("?o", "http://dbpedia.org/property/deathPlace", uri);
                tProp = searchPropertyFromModel(m, tProp, "person", null, true);
                m = lodQuery("?o", "http://dbpedia.org/property/birthPlace", uri);
                tProp = searchPropertyFromModel(m, tProp, "person", null, true);
                break;
            case "event":
                m = lodQuery(uri, "http://dbpedia.org/property/date", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, "dateofevent", false);
                m = lodQuery(uri, "http://dbpedia.org/property/location", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location", "placeofevent", false);
                m = lodQuery(uri, "http://dbpedia.org/ontology/location", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location", "placeofevent", false);
                m = lodQuery(uri, "http://dbpedia.org/property/place", "?o");
                tProp = searchPropertyFromModel(m, tProp, "place", "placeofevent", false);
                m = lodQuery(uri, "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#hasParticipant", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person", "hasparticipant", false);
                m = lodQuery(uri, "http://dbpedia.org/property/website", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, null, false);
                break;
            case "organisation":
                m = lodQuery(uri, "http://dbpedia.org/ontology/location", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location", "placeoforganisation", false);
                m = lodQuery(uri, "http://dbpedia.org/property/location", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location", "placeoforganisation", false);
                m = lodQuery(uri, "http://dbpedia.org/property/place", "?o");
                tProp = searchPropertyFromModel(m, tProp, "location", "placeoforganisation", false);
                m = lodQuery(uri, "http://dbpedia.org/property/introduced", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, "dateofcreation", false);
                m = lodQuery(uri, "http://dbpedia.org/property/managerClub", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person", "leader", false);
                m = lodQuery(uri, "http://dbpedia.org/property/mayor", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person", "leader", false);
                m = lodQuery(uri, "http://dbpedia.org/property/leaderName", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person", "leader", false);
                m = lodQuery(uri, "http://dbpedia.org/property/director", "?o");
                tProp = searchPropertyFromModel(m, tProp, "person", "leader", false);
                m = lodQuery(uri, "http://dbpedia.org/property/date", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, "dateofcreation", false);
                m = lodQuery(uri, "http://dbpedia.org/property/established", "?o");
                tProp = searchPropertyFromModel(m, tProp, null, "dateofcreation", false);
                m = lodQuery("?o", "http://dbpedia.org/property/museum", uri);
                tProp = searchPropertyFromModel(m, tProp, "object", "hasobject", true);
                m = lodQuery("?o", "http://dbpedia.org/ontology/museum", uri);
                tProp = searchPropertyFromModel(m, tProp, "object", "hasobject", true);

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

    public static ArrayList<Dialog.Property> searchPropertyFromModel(Model m, ArrayList<Dialog.Property> tProp, String type, String name, boolean revert) {
        StmtIterator iter = m.listStatements();
        ArrayList<Entity> ale = new ArrayList<Entity>();
        int setted = 0;
        Dialog.Property p2 = new Dialog.Property(null, null, null, null, null);
        while (iter.hasNext()) {
            Statement stmt = (Statement) iter.next();
//          System.out.println("test : "+stmt.asTriple().toString());
            org.apache.jena.rdf.model.Property predicate = stmt.getPredicate();
            String p = predicate.toString();
            RDFNode object;
            if(revert == true){
                object = stmt.getSubject();
            } else{
                object = stmt.getObject();
            }
//            System.out.println("----------------------");
//            System.out.println("Predicate :" + p);
//            System.out.println("Subject :" + subject.toString());
//            System.out.println("Object:" + object.toString());
//            System.out.println("----------------------");
            switch (p) {
                case "http://dbpedia.org/property/artist":
                    p2.setName("author");
                    break;
                case "http://dbpedia.org/property/child":
                    p2.setName("child");
                    break;
                case "http://dbpedia.org/property/parents":
                    p2.setName("parents");
                    break;
                case "http://dbpedia.org/property/author":
                    p2.setName("author");
                    break;
                // les travaux de la personne
                case "http://dbpedia.org/property/works":
                    p2.setName("isauthorof");
                    break;
                case "http://dbpedia.org/property/dateOfBirth":
                    p2.setName("birthdate");
                    break;

                // le maire d'une organisation de type ville
                case "http://dbpedia.org/ontology/language":
                    p2.setName("language");
                    break;
                case "http://dbpedia.org/ontology/mayor":
                    p2.setName("leader");
                    break;
                case "http://dbpedia.org/property/leaderName":
                    p2.setName("leader");
                    break;
                // le chef d'une organisation
                case "http://dbpedia.org/property/director":
                    p2.setName("leader");
                    break;
                case "http://dbpedia.org/ontology/managerClub ":
                    p2.setName("leader");
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
                case "http://dbpedia.org/property/museum":
                    p2.setName(name);
                    break;
                case "http://dbpedia.org/ontology/museum":
                    p2.setName(name);
                    break;
                case "http://dbpedia.org/property/year":
                    p2.setName("year");
                    break;

                case "http://dbpedia.org/ontology/abstract":
                    String test = stmt.getObject().asLiteral().getLanguage();
                    if (test.equals("fr")) {
                        p2.setEnt(null);
                        p2.setName(object.asLiteral().getString());
                        //System.out.println(object.asLiteral().getString());
                        p2.setName("description");
                        p2.setValue(object.asLiteral().getString());
                    } else if (p2.getType() == null) {
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
                    p2.setName("parent");
                    break;
                case "http://dbpedia.org/property/father":
                    p2.setName("parent");
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
                    p2.setName(name);
                    break;
                case "http://dbpedia.org/property/location":
                    p2.setName(name);
                    break;
                case "http://dbpedia.org/property/city":
                    p2.setName(name);
                    break;
                case "http://dbpedia.org/property/introduced":
                    p2.setName("dateofcreation");
                    break;
                case "http://dbpedia.org/property/established":
                    p2.setName("dateofcreation");
                    break;

                case "http://dbpedia.org/ontology/yearMeanC":
                    p2.setName("tempMean");
                    break;
                case "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#hasParticipant":
                    p2.setName("hasparticipant");
                    break;
                case "http://dbpedia.org/property/website":
                    p2.setName("website");
                    break;

                case "http://xmlns.com/foaf/0.1/homepage":
                    p2.setName("website");
                    break;
                case "http://dbpedia.org/ontology/deathPlace":
                    p2.setName("deathplace");
                    break;
                case "http://dbpedia.org/property/deathPlace":
                    p2.setName("deathplace");
                    break;

                default:
                    p2.setName("default");
                    break;
            }

            if (object.isResource()) {
                p2.setType("uri");
                p2.setLang("fr");
                String uri2 = object.toString();
                Entity e2 = new Entity(uri2, "", "", type);
                ale.add(selectlodFromEntity(e2));
                Entity[] ret = new Entity[ale.size()];
                p2.setEnt((Entity[]) ale.toArray(ret));

            } else {
                p2.setType("string");
                p2.setLang("fr");
                p2.setEnt(null);
                if (p2.getValue() == null) {
                    p2.setValue(object.toString().replace("^^http://www.w3.org/2001/XMLSchema#date", "").replace("@fr", "").replace("@en", ""));
                }
            }
            if (!p2.getName().contains("default")) {
                int s = tProp.size();
                if (s == 0) {
                    tProp.add(p2);
                } else {
                    int index = -1;
                    for (int i = 0; i < s; i++) {
                        if (tProp.get(i).getName() == p2.getName()) {
                            //System.out.println("name>>>>>"+p2.getName());
                            index = i;
                        }
                    }
                    if (index != -1) {
                        if (!tProp.get(index).getType().toString().equals("uri")) {
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
                            if (setted == 0) {
                                for (int j = 0; j < s1; j++) {
                                    al.add(tProp.get(index).getEnt()[j]);
                                }
                            }
                            for (int j = 0; j < p2.getEnt().length; j++) {
                                al.add(p2.getEnt()[j]);
                            }
                            Entity[] ent = new Entity[al.size()];
                            tProp.get(index).setEnt((Entity[]) al.toArray(ent));
                        }
                    } else {
                        tProp.add(p2);
                        setted = 1;
                    }
                }
            }

        }
        return tProp;
    }

    private static ResultSet lodQueryAmbigious(String s) {
        String sFinal = "";
        if (s.contains(" ")) {
            String[] s2 = s.split(" ");

            for (int i = 0; i < s2.length; i++) {

                int s2Taille = s2[0].length();
                if (i != s2.length - 1) {
                    if (s2Taille > 3) {
                        sFinal = sFinal + s2[i] + "*\"NEAR\"";
                    } else {
                        sFinal = sFinal + s2[i] + "\"NEAR\"";
                    }
                } else {
                    sFinal = sFinal + s2[i];
                }
            }
        } else {
            sFinal = s;
        }
        // '"Louv*"NEAR"lens"'
        //FILTER (contains(?label , \""+s+"\")
        String DBQueryString = $PREFIXS
                + "select ?uri ?label ?image"
                + "(group_concat(?type; separator=\"&&&&\") as ?types)"
                + "(group_concat(?typ; separator=\"&&&&\") as ?typs)"
                + "where {?uri rdfs:label ?label ."
                + " ?uri <http://dbpedia.org/ontology/abstract> ?description. "
                + " ?uri <http://dbpedia.org/ontology/thumbnail> ?image. "
                + " ?label <bif:contains> '\"" + sFinal + "\"' " // option (score ?sc).}"
                + " optional { ?uri rdf:type ?type . }"
                + " optional { ?uri dbp:type ?typ . }"
                + "FILTER (lang(?description) = 'fr')  FILTER (lang(?label) = 'fr')}"
                + "GROUP BY ?uri ?label ?image";
             // +"ORDER BY desc (?score)"
        // +"LIMIT 20";

        // on crée notre requete 
        Query DBquery = QueryFactory.create(DBQueryString);
        // on utilise cette methode afin d'éviter le parseur par défaut qui bloque certains caractéres
        QueryEngineHTTP qDBexec = new QueryEngineHTTP("http://dbpedia.org/sparql", DBquery);
        // QueryExecution qDBexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", DBquery);
        // on remplit le resultset par le résultat de la requete
        ResultSet r = qDBexec.execSelect();
        return r;
    }

    private static Model lodQuery(String s, String p, String o) {
        String DBQueryString;
        if (o.contains("http")) {
            DBQueryString = $PREFIXS
                    // on ajoute  ?s owl:sameAs ?Entity" aprés le construct pour comparer avec les resultats locales
                    + "construct where {" + s + " <" + p + "> <" + o + ">} limit 10";
        } else {
            DBQueryString = $PREFIXS
                    // on ajoute  ?s owl:sameAs ?Entity" aprés le construct pour comparer avec les resultats locales
                    + "construct where {<" + s + "> <" + p + "> " + o + "}";
        }
    
    Query DBquery = QueryFactory.create(DBQueryString);
    QueryExecution qDBexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", DBquery);

    Model m = qDBexec.execConstruct();

    qDBexec.close ();
    return m ;
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

//        long endTime = System.currentTimeMillis();
//        System.out.println("_____ FIN FONCTION SELECTLODENTITY: " + (endTime - startTime));
//        System.out.println("l'entité : " + e);
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
                    if (typ.contains("Person") || (typ.contains("Agent")) || (typ.contains("Artist"))) {
                        e.setType("person");
                    } else if (e.getType() == null && typ.contains("Event") || typ.contains("SocialEvent")) {
                        e.setType("event");
                    } else if (e.getType() == null && typ.contains("Location") || typ.contains("Place") || typ.contains("State") || typ.contains("PopulatedPlace")) {
                        e.setType("location");
                    } else if (e.getType() == null && typ.contains("SpatialThing") || typ.contains("Organization")) {
                        e.setType("organisation");
                    } else if (e.getType() == null && typ.contains("Activity")) {
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

                    e.setImage(object.asResource().getNameSpace());
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

    //on construct toutes les propriétés et valeurs de l'URI passé en paramètre (riad)
    // l'URI est externe, et fait donc référence à un lien dbpedia, freebase...
    public static ArrayList<Entity> selectlodFromKeyWord(String keyword) {
        // On créer une nouvelle liste d'entités de type Entity
        ArrayList<Entity> entities = new ArrayList<>();
        // on fait appel à la fonction qui reçoit le mot clef et retourne un resulset 
        // ce resultset contient toutes les entités relatives au mot clef
        ResultSet r = lodQueryAmbigious(keyword);
        // à cette étape j'aurai une liste d'uri à parcourir
        // tant qu'une entité existe on rentre dans la boucle
        while (r.hasNext()) {
            // on parour les élements de la list sous forme de QuerySolution
            QuerySolution s = r.next();
            //  System.out.println("stmt"+stmt.toString());
            // on crée une nouvelle entity
            Entity e = new Entity();
            // on affecte l'uri récupérer de la resource à notre entité
            e.setURI(s.get("uri").asResource().toString());
            // on affecte le nom récupérer de la resource à notre entité
            e.setName(s.get("label").asLiteral().getString());
            // on affecte le lien de l'image récupérer de la resource à notre entité
            String image = s.get("image").asResource().toString();
            // si l'image est nul on affecte une image par defaut à notre entité
            if (image.contains("null")) {
                e.setImage("null");
                //sinon on donne l'url de l'image récupere de dbpedia
            } else {
                e.setImage(image);
            }
            // on récupére les types sous forme de tablea, on enléve le séparateur ajouter lors de la requete
            String[] types = s.get("types").toString().split("&&&&");
            // on récupére la taille du tableau 
            int sizeTypes = types.length;
            // comme nous avons deux prefix différent on récupére un deuxiéme tableau avec les types
            String[] typs = s.get("typs").toString().split("&&&&");
            int sizeTyps = typs.length;
            // si le tableau n'est pas vide
            if (sizeTypes != 0) {

                for (int i = 0; i < sizeTypes; i++) {
                    // on compare le type récupérer si c'est un des choix suivant 
                    if (types[i].contains("Person") || (types[i].contains("Agent") || (types[i].contains("Artist")))) {
                        // on lui affecte le type que nous avons définit et qui est commun entre la db local et les types dbpedia
                        e.setType("person");
                    } else if (e.getType() == null && types[i].contains("Event")) {
                        e.setType("event");
                    } else if (e.getType() == null && types[i].contains("Location") || types[i].contains("Place") || types[i].contains("State") || types[i].contains("PopulatedPlace")) {
                        e.setType("location");
                    } else if (e.getType() == null && types[i].contains("SpatialThing") || types[i].contains("Organization")) {
                        e.setType("organisation");
                    } else if (e.getType() == null && types[i].contains("Activity")) {
                        e.setType("activity");
                    }
                    if (e.getType() == null) {
                        e.setType("object");
                    }
                }
                // si il s'agit du deuxéme prédicat on fait la même opération
            } else {
                for (int i = 0; i < sizeTyps; i++) {
                    if (typs[i].contains("Person") || (typs[i].contains("Agent") || (typs[i].contains("Artist")))) {
                        e.setType("person");
                    } else if (e.getType() == null && typs[i].contains("Event")) {
                        e.setType("event");
                    } else if (e.getType() == null && typs[i].contains("Location") || typs[i].contains("Place") || typs[i].contains("State") || typs[i].contains("PopulatedPlace")) {
                        e.setType("location");
                    } else if (e.getType() == null && typs[i].contains("SpatialThing") || typs[i].contains("Organization")) {
                        e.setType("organisation");
                    } else if (e.getType() == null && typs[i].contains("Activity")) {
                        e.setType("activity");
                    }
                    // si aucun des types n'est trouvé on le met à object qui le type génerique
                    if (e.getType() == null) {
                        e.setType("object");
                    }
                }
            }
            // on ajoute notre entité au tableau des entités
            entities.add(e);
        }
////        test d'affichage
//        for (int i = 0; i < entities.size(); i++) {
//            System.out.println("entiity n°" + i + "  :  " + entities.get(i));
//        }
        return entities;
    }

    public static Entity[] selectAllEntitiesURI() {

        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", String.format(
                        $PREFIXS
                        + "select ?o ?n ?type "
                        + "where {?s axis-datamodel:uses ?o ."
                        + "	?s rdf:type axis-datamodel:Entity ."
                        + "	?o axis-datamodel:hasRepresentation ?d ."
                        + "	?o axis-datamodel:hasRepresentation ?regof ."
                        + "	?d rdf:type axis-datamodel:Document ."
                        + "	?regof rdf:type ?type ."
                        + "     ?d rdfs:label ?n "
                        + "     MINUS {"
                        + "          ?regof rdf:type axis-datamodel:Document"
                        + "      }"
                        + "     MINUS {"
                        + "          ?regof rdf:type axis-datamodel:RegOfPhotoItem"
                        + "      }"
                        + "     MINUS {"
                        + "          ?regof rdf:type axis-datamodel:RegOfInformationItem"
                        + "      }}"
                        + "     ORDER BY ?type"));

        ResultSet rs = qe.execSelect();

        ArrayList<Entity> tab = new ArrayList<>();
        //List<QuerySolution> mList = null;
        while (rs.hasNext()) {
            QuerySolution n = rs.next();
            Entity e = new Entity();
            e.setURI(n.get("o").asResource().toString());
            e.setName(n.get("n").asLiteral().toString());
            //n.get("n").
            tab.add(e);
        }
        //mList = ResultSetFormatter.toList(rs);
        qe.close();

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
                        + "     ?c axis-datamodel:email ?email}"
                        + "     ORDER BY ?creationDate", e.getURI()));

        ResultSet rs = qe.execSelect();
        ArrayList<Comment> tab = new ArrayList<>();

        while (rs.hasNext()) {
            QuerySolution n = rs.next();
            Comment c = new Comment();
            c.setId(n.get("c").asResource().toString());
            c.setAuthorName(n.get("creator").asLiteral().getString());
            c.setComment(n.get("content").asLiteral().getString());
            c.setEmail(n.get("email").asLiteral().getString());
            c.setCreateDt(n.get("creationDate").asLiteral().getString());
            c.setValidated(Boolean.valueOf(n.get("validate").asLiteral().getString()));
            e.constructEntity();
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

        while (rs.hasNext()) {
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

        if (rs.hasNext()) {
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

    public static boolean deleteAll() {
        String req = $PREFIXS
                + "DELETE WHERE { "
                + " ?s rdf:type axis-datamodel:Entity . "
                + " ?s axis-datamodel:uses ?o}";
        UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                UpdateFactory.create(String.format(req)),
                "http://localhost:3030/ds/update");

        upp.execute();

        req = $PREFIXS
                + "DELETE WHERE { "
                + " ?s rdf:type axis-datamodel:Comment }";
        upp = UpdateExecutionFactory.createRemote(
                UpdateFactory.create(String.format(req)),
                "http://localhost:3030/ds/update");

        upp.execute();

        return true;
    }

}
