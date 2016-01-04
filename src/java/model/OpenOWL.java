/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

/**
 *
 * @author APP-Riad.Belmahi
 */
public class OpenOWL {

    public static void main(String args[]) {

       OpenConnectOWL();
        TestOpenOwl();
    }

    static String s;

    // Open a connection to MonFichierOwl.OWL
    static OntModel OpenConnectOWL() {

        //OntModel mode = null;
        String pathOntologie = "C:/Users/App-riad.belmahi/Documents/NetBeansProjects/SemanticMuseum1/src/java/model/ontologieMoM.owl";
        OntModel mode = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
        java.io.InputStream in = FileManager.get().open(pathOntologie);  //MyFile
        

        //test
        if (in == null) {
            throw new IllegalArgumentException("Pas de base de connaissance");  // there i no file to connect
        }
        return (OntModel) mode.read(in, "");
    }

    // jena.query.ResultSet  return
    static ResultSet ExecSparQl(String Query) {

        Query query = QueryFactory.create(Query);

        QueryExecution qe = QueryExecutionFactory.create(query, OpenConnectOWL());
        ResultSet results = qe.execSelect();

        return results;  // Retrun jena.query.ResultSet 

    }

    // String return (convert jena.query.ResultSet  to String)
    static String ExecSparQlString(String Query) {
        try {
            Query query = QueryFactory.create(Query);

            QueryExecution qe = QueryExecutionFactory.create(query, OpenConnectOWL());

            ResultSet results = qe.execSelect();

            // Test
            if (results.hasNext()) {

                // if iS good 
                ByteArrayOutputStream go = new ByteArrayOutputStream();
                ResultSetFormatter.out((OutputStream) go, results, query);
                //  String s = go.toString();
                s = new String(go.toByteArray(), "UTF-8");
            } // not okay
            else {

                s = "rien";
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OpenOWL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;   // return  jena.query.ResultSet  as string 
    }

    // example using  jena.query.ResultSet  
    public static void TestOpenOwl() {  // method

       //OntModel model = OpenOWL.OpenConnectOWL();
      // System.out.println("");  // get the activity from my File OWL 
        String queryString;
       
       queryString = "PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#>" +
                    "SELECT ?g" +
                    "WHERE" +
                    "{ ?y vcard:Given ?g.}";
        /* queryString = "PREFIX axis: <http://titan.be/axis-csrm/datamodel/ontology/0.1#>"
                + "SELECT  ?hasRepresentation "
                + "where { ?y axis:aperture ?hasRepresentation.}";
          */      
        // call method ExecSparQl from class OpenOWL
        // ExecSparQl return a Resultset 
        String results = OpenOWL.ExecSparQlString(queryString);
        
        System.out.println("le resultat"+ results);

    }
}