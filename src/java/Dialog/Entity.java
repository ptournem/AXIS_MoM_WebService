/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import static model.Connector.*;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class Entity {

    String URI;
    String name;
    String image;
    String type;

    public static void main(String args[]) {

//        Entity e = new Entity();
//        e.setImage("ig2i.jpg");
//        e.setName("IG2I");
//        e.setType("object");
//        
//        e.insertImage(p);
        Entity e3 = new Entity();
        String uri = "http://titan.be/axis-poc2015/Entity_TheMarchForJobsAndFreedom";
        e3.setURI(uri);
        e3.constructEntity();
//        System.out.println(e3);

        //String uri = "<http://titan.be/axis-poc2015/Entity_TheMarchForJobsAndFreedom>";
        //String uri1 = "<http://titan.be/axis-poc2015/8b281ed7-1514-4f29-842c-3a81a3dfd722>";
        //e.setURI(uri);
        //e.constructEntity();
        //System.out.println(e);
//        e.printEntity(uri);
//        
//        TestWebService ws = new TestWebService();
//        Entity e2 = ws.AddEntity(e);
//        
//
//        Entity e4 = new Entity();
//        e4.setURI(e2.getURI());
//        e4.constructEntity();
//        System.out.println(e4);
    }

    public Entity(String URI, String name, String image, String type) {
        this.URI = URI;
        this.name = name;
        this.image = image;
        this.type = type;
    }

    public Entity() {

    }

    public Entity(String name, String image, String type) {
        this.name = name;
        this.image = image;
        this.type = type;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Entity AddEntity() {
        String mainURI = insert("rdf:type", "axis-datamodel:Entity");
        String AFP = insert("rdf:type", "axis-datamodel:AFP");
        insert(AFP, "axis-datamodel:declaresTheExistenceOf", mainURI);
        insert(mainURI, "axis-datamodel:isDeclaredBy", AFP);
        String uri = null;
        switch (this.type) {
            case "person":
                uri = insert("rdf:type", "axis-datamodel:PhysicalPerson");
                insert(mainURI, "axis-datamodel:uses", uri);
                this.setURI(uri);

                String uri3 = insert("rdf:type", "axis-datamodel:RegOfPhysicalPerson");
                insert(uri, "axis-datamodel:hasRepresentation", uri3);

                break;
            case "event":
                uri = insert("rdf:type", "axis-datamodel:Event");
                insert(mainURI, "axis-datamodel:uses", uri);
                this.setURI(uri);

                String uri4 = insert("rdf:type", "axis-datamodel:RegOfEvent");
                insert(uri, "axis-datamodel:hasRepresentation", uri4);

                
                break;
            case "object":
                uri = insert("rdf:type", "axis-datamodel:PhysicalObject");
                insert(mainURI, "axis-datamodel:uses", uri);

                String uri5 = insert("rdf:type", "axis-datamodel:RegOfObjectItem");
                insert(uri, "axis-datamodel:hasRepresentation", uri5);

                this.setURI(uri);
                break;
            case "location":
                uri = insert("rdf:type", "axis-datamodel:Place");
                insert(mainURI, "axis-datamodel:uses", uri);
                this.setURI(uri);

                String uri6 = insert("rdf:type", "axis-datamodel:RegOfPlace");
                insert(uri, "axis-datamodel:hasRepresentation", uri6);

                break;
//              case "activity":
//                  uri = insert("rdf:type", "axis:RegOfPhysicalPerson");
//                  insert(e.URI, "axis:hasExpression", uri);
//                  break;
            case "organisation":
                
                uri = insert("rdf:type", "axis-datamodel:MoralPerson");
                insert(mainURI, "axis-datamodel:uses", uri);
                this.setURI(uri);

                String uri9 = insert("rdf:type", "axis-datamodel:RegOfMoralPerson");
                insert(uri, "axis-datamodel:hasRepresentation", uri9);

                break;
        }

        String uri7 = insert("rdf:type", "axis-datamodel:Document");
        insert(uri, "axis-datamodel:hasRepresentation", uri7);

        String uri8 = insert("rdf:type", "axis-datamodel:RegOfInformationItem");
        insert(uri, "axis-datamodel:hasRepresentation", uri8);

        this.insertName(new Property("name", this.getName(), null, "string", "fr"));
        this.insertImage(new Property("image", this.getImage(), null, "string", "fr"));

        //insert(e.getURI(), "rdfs:label", e.getName(), "fr");
        return this;
    }

    public ArrayList<Property> getPropertiesMapFromLod(Entity e) {
        if (e.getURI().contains("dbpedia")) {
            return entityBrowser(e);
        }
        String newUri = null;
        ResultSet rs = selectFromEntity("<" + e.getURI() + ">", "?p", "?o");

        while (rs.hasNext()) {
            QuerySolution qs = rs.nextSolution();
            if (qs.get("p").toString().contains("sameAs")) {
                newUri = qs.get("o").toString();
                Entity e1 = new Entity();
                e1.setURI(newUri);
                e1.constructEntity();
                return entityBrowser(e1);
            }
        }
        return null;
    }

    public void constructEntity() {
        if (this.URI.contains("dbpedia")) {
            selectlodFromEntity(this);
        } else {
            String req = String.format($PREFIXS
                    + " select ?name ?image ?type where {"
                    + " values ?uri {<%s>}"
                    + " ?s axis-datamodel:uses ?uri ."
                    + " ?s rdf:type axis-datamodel:Entity ."
                    + " ?uri axis-datamodel:hasRepresentation ?doc ."
                    + " ?doc a axis-datamodel:Document ."
                    + " ?uri axis-datamodel:hasRepresentation ?reg ."
                    + " ?reg axis-datamodel:hasExpression ?emb ."
                    + " ?emb axis-datamodel:fileName ?image ."
                    + " ?uri a ?type ."
                    + " ?doc rdfs:label ?name."
                    + " }", this.URI);
            QueryExecution qe = QueryExecutionFactory.sparqlService(
                    "http://localhost:3030/ds/query", req);

            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                QuerySolution n = rs.next();
                String type = n.get("type").asResource().getLocalName();
                if (type.contains("PhysicalPerson")) {
                    this.type = "person";
                }
                if (type.contains("Event")) {
                    this.type = "event";
                }
                if (type.contains("PhysicalObject")) {
                    this.type = "object";
                }
                if (type.contains("Place")) {
                    this.type = "location";
                }
                if (type.contains("MoralPerson")) {
                    this.type = "organisation";
                }
                //        if(type.contains(""))
                //            this.type = "activity";
//                System.out.println("image:"+n.get("image").asLiteral().getString());
                this.image = n.get("image").asLiteral().getString();
                this.name = n.get("name").asLiteral().getString();

            }
            qe.close();
        }
    }

    public void insertName(Property p) {
        insert(selectRegOfEntity(this.URI, "Document"), "rdfs:label", p.getValue(), "fr");
    }

    public void insertSameAs(Property p) {
        insert(this.URI, "owl:sameAs", p.getEnt()[0].getURI());
    }
    
    public void insertWebsite(Property p) {
        insert(selectRegOfEntity(this.URI, "Document"), "dbont:wikiPageExternalLink", p.getValue(), "fr");
    }

    public void insertSocialNetwork(Property p) {
        insert(selectRegOfEntity(this.URI, "Document"), "axis-datamodel:socialNetwork", p.getValue(), "fr");
    }
    
    public void insertDescription(Property p) {
        insert(selectRegOfEntity(this.URI, "Document"), "rdf:Description", p.getValue(), "fr");
    }

    public void insertImage(Property p) {

        String uri1 = insert("rdf:type", "axis-datamodel:RegOfPhotoItem");
        String uri2 = insert("rdf:type", "axis-datamodel:Location");
        String uri3 = insert("rdf:type", "axis-datamodel:EmbodimentOfImageFile");

        insert(this.URI, "axis-datamodel:hasRepresentation", uri1);

        insert(uri1, "axis-datamodel:isARepresentationOf", this.URI);

        insert(uri3, "axis-datamodel:fileName", p.getValue(), "file");

        insert(uri3, "axis-datamodel:hasLocation", uri2);

        insert(uri3, "axis-datamodel:expresses", uri1);

        insert(uri2, "axis-datamodel:locates", uri3);

        insert(uri1, "axis-datamodel:hasExpression", uri3);

        insert(uri3, "axis-datamodel:expresses", uri1);
        // Entity => RegOfPhotoItem => Location <=> EmbodimentOfFile => p.value

        // on crée un "RegOfPhotoItem"
        // on le lie à "Location" via "hasExpression"
        // on le lie à "EmbodimentOfFile" via "locates"
        // on le lie à "notre_fichier_jpg" via "fileName"
        // et on lie "EmbodimentOfFile" à "Location" via "hasLocation"
    }

    public String getTypeProperty(Property p) {

        String type = p.getType();
        if (p.getType().equals("uri")) {
            if (p.getEnt()[0].getURI().contains("dbpedia")) {
                return "dbpedia";
            } else {
                return "our";
            }

        } else {
            return "literal";
        }
    }

    public boolean delete(String uri, String prop, String uri2) {
        deleteLinkEntity(uri, prop, uri2);
        return true;
    }
    
    public boolean deleteTriplet(String s, String p, String o) {
        deleteTriple(s, p, o);
        return true;
    }
    
    public Entity[] getEntityTab(String Uri) {
        Entity e = new Entity();
        ArrayList<Entity> ale = new ArrayList<>();
        e.setURI(Uri);
        e.constructEntity();
        ale.add(e);
        Entity[] ret = new Entity[ale.size()];
        return (Entity[]) ale.toArray(ret);
    }

    public Entity[] getEntityTab(String[] tab) {
        
        ArrayList<Entity> ale = new ArrayList<>();
        ArrayList list = new ArrayList();
        for (int i = 0; i < tab.length; i++) {
            list.add(tab[i]);
        }
        Set set = new HashSet();
        set.addAll(list);
        ArrayList distinctList = new ArrayList(set);
        java.lang.Object[] myArray = distinctList.toArray();
        for (int i = 0; i < myArray.length; i++) {
            if (myArray[i].toString().contains("http")) {
                Entity e = new Entity();
                e.setURI(myArray[i].toString());
                e.constructEntity();
                ale.add(e);
            }
        }
        Entity[] ret = new Entity[ale.size()];
        return (Entity[]) ale.toArray(ret);
    }
    
    @Override
    public String toString() {
        return "Entity{" + "URI=" + URI + ", name=" + name + ", image=" + image + ", type=" + type + '}';
    }

}
