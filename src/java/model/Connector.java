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
import java.util.ArrayList;
import org.apache.jena.rdf.model.NodeIterator;
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
        Entity e = new Entity("<http://dbpedia.org/resource/Racine>", null, null, null);
       // String uri = e.getURI().toString();
      entityBrowser(e);
      //  selectlod("http://dbpedia.org/resource/Racine");

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
                "http://localhost:3030/ds/query", String.format(
                        "PREFIX poc: <http://titan.be/axis-poc2015/>"
                        + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                        + "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.2#>"
                        + "construct{?s ?p ?o}"
                        + "WHERE { ?s ?p ?o . {"
                        + "SELECT * WHERE {"
                        + "%s ?p ?o }"
                        + "} }", uri));

        Model m = qe.execConstruct();

        return m;
    }

    public static Model selectFromEntityWithPredicat(String uri, String predicat) { //Robine
        //on construct toutes les propriétés et valeurs de l'URI passé en paramètre
        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", String.format(
                        "PREFIX poc: <http://titan.be/axis-poc2015/>"
                        + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                        + "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.2#>"
                        + "construct{?s ?p ?o}"
                        + "WHERE { ?s ?p ?o . {"
                        + "SELECT * WHERE {"
                        + "%s %s ?o }"
                        + "} }", uri, predicat));

        Model m = qe.execConstruct();

        return m;
    }

    // méthode pour supprimer des charactéres
    public static ArrayList<Property> entityBrowser(Entity e) {

       ArrayList<Property> tProp = new ArrayList<Property>();
        String uri = e.getURI().toString();
        String DBQueryString = "PREFIX dbont: <http://dbpedia.org/ontology/> "
                + "PREFIX dbp: <http://dbpedia.org/property/>"
                + "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"
                + "PREFIX dbr: <http://dbpedia.org/resource/>"
                + "PREFIX type: <http://dbpedia.org/class/yago/>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                // on ajoute  ?s owl:sameAs ?Entity" aprés le construct pour comparer avec les resultats locales
                + "construct where {" + uri + " ?p ?o}";

        Query DBquery = QueryFactory.create(DBQueryString);
        QueryExecution qDBexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", DBquery);

        Model m = qDBexec.execConstruct();
        StmtIterator iter = m.listStatements();
        while (iter.hasNext()) {

            Statement stmt = (Statement) iter.next();
           // Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            String p = predicate.toString();
           // RDFNode object = stmt.getObject();
            
            tProp.add(predicate);
            
            
        }

        System.out.println("tab de prop"+tProp);
        return tProp;
    }

    
    public static ArrayList<Entity> selectlod(String keyword) {
        //riad

        //on construct toutes les propriétés et valeurs de l'URI passé en paramètre
        // l'URI est externe, et fait donc référence à un lien dbpedia, freebase...
        ArrayList<Entity> entities = new ArrayList<>();

        String DBQueryString = "PREFIX dbont: <http://dbpedia.org/ontology/> "
                + "PREFIX dbp: <http://dbpedia.org/property/>"
                + "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"
                + "PREFIX dbr: <http://dbpedia.org/resource/>"
                + "PREFIX type: <http://dbpedia.org/class/yago/>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                // on ajoute  ?s owl:sameAs ?Entity" aprés le construct pour comparer avec les resultats locales
                + "construct where {<" + keyword + "> ?p ?o}";
//                "select ?s ?o" +
//                "where {" +
//                "  dbr:"+keyword+" dbont:wikiPageRedirects ?o." +
//                "  ?s dbont:wikiPageDisambiguates ?o." +
//                "}";
        // la langue française on ajoute <. FILTER langMatches( lang(?o), \"FR\" )>
        // FILTER contains(str(?s), \"++\")
        Query DBquery = QueryFactory.create(DBQueryString);
        QueryExecution qDBexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", DBquery);

        Model m = qDBexec.execConstruct();

        StmtIterator iter = m.listStatements();
        Entity e = new Entity();
        while (iter.hasNext()) {

            Statement stmt = (Statement) iter.next();
            Resource subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
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

//            if (p.contains("wikiPageDisambiguates")) {
//                Resource r2 = stmt.getResource();
//                selectlod(r2.toString().replace("http://dbpedia.org/resource/", ""));
//                // System.out.println("wikiPageDisambiguates:"+r2);
//
//            } else {
                switch (p) {

                    // si le predicat est un type
                    case "http://www.w3.org/1999/02/22-rdf-syntax-ns#type":
                        String typ = stmt.getObject().toString();
                        if (typ.contains("Object")) {
                            e.setType("object");
                        }
                        if (typ.contains("Event")) {
                            e.setType("event");
                        }
                        if (typ.contains("Person")) {
                            e.setType("person");
                        }
                        if (typ.contains("Location")) {
                            e.setType("location");
                        }
                        if (typ.contains("Activity")) {
                            e.setType("activity");
                        }
                        if (typ.contains("Organisation")) {
                            e.setType("organisation");
                        }

                        break;
                    case "http://dbpedia.org/ontology/thumbnail":
                        e.setImage(object.toString());
                        break;
                    case "http://www.w3.org/2000/01/rdf-schema#label":
                        String test = stmt.getObject().asLiteral().getLanguage();
                        if (test.equals("fr")) {
                            e.setName(object.toString().replace("@fr", ""));
                        }
                        break;
                }
            // e.setURI(subject.toString());
//            String uri = e.getURI();
//            String name = e.getName();
//            String type = e.getType();
//            String image = e.getImage();

//                if (image != null) {
//                    System.out.println("e : " + e);
//                }
//            }
                // System.out.println("L'entité est :"+e);
                //  System.out.println("--------------- DBPedia Resultset ------------------");
                //  ResultSetFormatter.out(System.out, resultsDB);
                qDBexec.close();
            }
            // on ajoute nos entités a notre tableau 
          entities.add(e);
      //  }
        System.out.println("entity :" + e);
        // System.out.println("les entites : "+entities);
        return entities;
    }

    public static String insert(String p, String o) { //robine
        String req = "PREFIX axis: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
                + "PREFIX poc: <http://titan.be/axis-poc2015/>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
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
        String req = "PREFIX axis: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
                + "PREFIX poc: <http://titan.be/axis-poc2015/>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "INSERT DATA { "
                + " %s "
                + " %s "
                + " %s "
                + ".}";

        UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                UpdateFactory.create(String.format(req, s, p, o)),
                "http://localhost:3030/ds/update");
        upp.execute();

        return true;
    }

    public static boolean insert(String s, String p, String o, String lang) { //robine
        String req = "PREFIX axis: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
                + "PREFIX poc: <http://titan.be/axis-poc2015/>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "INSERT DATA { "
                + " %s "
                + " %s "
                + "\"%s\"@%s"
                + ".}";

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
