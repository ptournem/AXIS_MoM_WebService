/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.InputStream;
import java.net.URI;
import java.util.UUID;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
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
 * @author loannguyen
 */
public class Connector {
   

    public static void main(String args[]) {
        //test
        //test selectFromEntity (1 variable)
        System.out.println("Construct result = " + selectFromEntity("http://titan.be/axis-poc2015/Entity_TheMuseumObjects").toString());
        
        //test selectFromEntity (2 variables)
        System.out.println("Construct result = " + selectFromEntity("http://titan.be/axis-csrm/datamodel/ontology/0.3#fileName", "MLK_speech.bwf").toString());

    }

    

    public static Model loadModels(String url) { //mélanoche
        //todo
     url = "/Users/Mélanie/Documents/AXIS_MoM_WebService/src/java/resources/axis-csrm-datamodel-MoM.owl";
	
	// Create an empty model
	OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
	
	// Use the FileManager to find the input file
	InputStream in = FileManager.get().open(url);

	if (in == null)
		throw new IllegalArgumentException("File: "+url+" not found");

	// Read the RDF/XML file
	model.read(in, null);
       model.write(System.out);
        return model;
    }
    
    public static boolean executeQuery(String str) {

        //pas prio

        return true;
    }
    
    
    public static Model selectFromEntity(String uri) { //loan
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
    
    public static boolean selectlod(URI uri) { //riad
        //on construct toutes les propriétés et valeurs de l'URI passé en paramètre
        // l'URI est externe, et fait donc référence à un lien dbpedia, freebase...
        return true;
    }
    
    public static int insert(String s, String p) { //robine
        
        int uid = 0;
        return uid;
    }
    
    public static boolean insert(String s, String p, String o) { //robine
        //insert en suivant la logique de sujet-prédicat-objet
        

        
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
