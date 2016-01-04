/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

/**
 *
 * @author APP-Riad.Belmahi
 */
public class chargerOntologie {
    public static void main(String args[]) {
        sparqlTest();
    }
    static void sparqlTest() {
           FileManager.get().addLocatorClassLoader(chargerOntologie.class.getClassLoader());
           // chargement du fichier 
           Model model = FileManager.get().loadModel("D:\\4-PRP\\Git_WebService\\sources\\model\\axis-csrm-datamodel-MoM.owl");
           // ici on doit lire le modèle le stocker en mémoire afin d'effectuer les opérations SPARQL par la suite ...
           // je ne sais pas encore comment !!
           // transformer le modèle en format JSON
           //model.write(System.out,"RDF/JSON");
        // Définition de prefixe pour simplifier l'utilisation de SPARQL
           // 
      String queryString
                = "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.2#>"
                + "SELECT ?Entity"
                + " WHERE "
                + "{"
                + "?Entity axis-datamodel:isDeclaredBy ?x "
                + "}";
      
        Query query = QueryFactory.create(queryString);
        final QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            System.out.println("requete executé !!");
            ResultSet rs = qexec.execSelect();
            // Affichage des resultats
            System.out.println("solution trouvée !!"+rs.toString());
           
            // étape non fonctionnelle
            
            for (;rs.hasNext();) {
                //System.out.print("");
                 
                QuerySolution rb = rs.nextSolution();
               
                String y = rb.getLiteral("isDeclaredBy").getString();
                
                System.out.print(y);
                
                //frame.addItem(rs.getString(y.toString()));
                //list.addItem(y.toString() + ";n");
                //combo.addItem(y.getSelectedItem().toString());
            }
        } finally {
            qexec.close();
        }
    }
}
