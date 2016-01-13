/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.UUID;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.util.FileManager;

/**
 *
 * @author APP-Riad.belmahi
 */
public class Connector {

    public static void main(String args[]) {
        //test
        
        System.out.println("main");
        
        String myUID = insert("Entity", "RegOfPhysicalPerson");
        insertLitteral(myUID, "label", "Martin Luther King");
        
        Model m = loadModels("test");
        System.out.println(m.toString());
        
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
                "http://localhost:3030/ds/query", " PREFIX axis: <http://titan.be/axis-csrm/datamodel/ontology/0.3#> "+
                str);


        Model constructModel = qe.execConstruct();

        return constructModel;
        //pas prio
    }

    public static Model selectFromEntity(URI uri) { //loan
        //on construct toutes les propriétés et valeurs de l'URI passé en paramètre
        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", "PREFIX axis: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"+
                "CONSTRUCT WHERE {<"+uri+"> ?p ?o}");


        Model constructModel = qe.execConstruct();

        return constructModel;
    }

    public static Model selectFromEntity(String pred, String obj) { //loan
        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", "PREFIX axis: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"+
                        "CONSTRUCT WHERE {?s <"+pred+"> \""+obj+"\"}");

        Model constructModel = qe.execConstruct();
        
        return constructModel;

    }

    public static void selectlod(String keyword) {
        //riad
        //on construct toutes les propriétés et valeurs de l'URI passé en paramètre
        // l'URI est externe, et fait donc référence à un lien dbpedia, freebase...

        String DBQueryString = "PREFIX dbont: <http://dbpedia.org/ontology/> "
                + "PREFIX dbp: <http://dbpedia.org/property/>"
                + "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"
                + "PREFIX dbr: <http://dbpedia.org/resource/>"
                + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
                + // on ajoute  ?s owl:sameAs ?Entity" aprés le construct pour comparer avec les resultats locales
                " SELECT * WHERE {dbr:"+keyword+" ?p ?o FILTER langMatches(lang(?o), \"EN\")}"; // la langue française on ajoute <FILTER langMatches(lang(?o), \"FR\")>

        Query DBquery = QueryFactory.create(DBQueryString);
        QueryExecution qDBexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", DBquery);

        ResultSet resultsDB = qDBexec.execSelect();
          //  System.out.println("--------------- DBPedia Resultset ------------------");
          //  ResultSetFormatter.out(System.out, resultsDB);
 
           
            System.out.println("--------------- DBPedia JSON ------------------");
            ResultSetFormatter.outputAsJSON(System.out, resultsDB);

//            String FBQueryString
//                    = "prefix fb: <http://rdf.freebase.com/ns/>"
//                    + "prefix fn: <http://www.w3.org/2005/xpath-functions#>"
//                    + "select * {"
//                    + " ?s fb:type.object.type fb:visual_art.artwork ."
//                    + " } ";
//
//            Query FBquery = QueryFactory.create(FBQueryString);
//
//            QueryExecution qFBexec = QueryExecutionFactory.sparqlService("http://www.freebase.com/query", FBquery);
//
//            ResultSet resultsFB = qFBexec.execSelect();
//            System.out.println("FreeBase");
//            ResultSetFormatter.out(System.out, resultsFB);

            qDBexec.close();
   //         qFBexec.close();

        }

    public static String insert(String p, String o) { //robine
        String req = "PREFIX axis: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
        + "PREFIX poc: <http://titan.be/axis-poc2015/>"
        + "INSERT DATA { "
        + "poc:%s "
        + "axis:%s "
        + "axis:%s "
        + ".}";
        
        String id = UUID.randomUUID().toString();
        System.out.println(String.format("Adding %s", id));
        UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                                                                  UpdateFactory.create(String.format(req, id, p, o)),
                                                                  "http://localhost:3030/ds/update");
        upp.execute();
        return id;
    }
    
    public static boolean insert(String s, String p, String o) { //robine
        String req = "PREFIX axis: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
        + "PREFIX poc: <http://titan.be/axis-poc2015/>"
        + "INSERT DATA { "
        + "poc:%s "
        + "axis:%s "
        + "poc:%s"
        + ".}";

        UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                                                                  UpdateFactory.create(String.format(req, s , p, o)),
                                                                  "http://localhost:3030/ds/update");
        upp.execute();
        
        return true;
    }
    public static boolean insertLitteral(String s, String p, String o) { //robine
        String req = "PREFIX axis: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
        + "PREFIX poc: <http://titan.be/axis-poc2015/>"
        + "INSERT DATA { "
        + "poc:%s "
        + "axis:%s "
        + "\"%s\"@fr "
        + ".}";

        UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                                                                  UpdateFactory.create(String.format(req, s , p, o)),
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
