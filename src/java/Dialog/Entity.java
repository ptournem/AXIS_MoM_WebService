/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import static model.Connector.*;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;

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

        String uri = null;
        switch (this.type) {
            case "person":
                uri = insert("rdf:type", "axis-datamodel:PhysicalPerson");
                insert(mainURI, "axis-datamodel:uses", uri);
                this.setURI(uri);
                
                String uri3 = insert("rdf:type", "axis-datamodel:RegOfAgent");
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
                
                String uri5 = insert("rdf:type", "axis-datamodel:EmbodimentOfObject");
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
                break;
        }

        String uri7 = insert("rdf:type", "axis-datamodel:Document");
        insert(uri, "axis-datamodel:hasRepresentation", uri7);
        
        this.insertName(new Property("name", this.getName(), "fr", null));
        this.insertImage(new Property("image", this.getImage(), "fr", null));
        
        
        
        
        
        //insert(e.getURI(), "rdfs:label", e.getName(), "fr");
        return this;
    }

    public List<List> browseModel(Resource resource, String predicate) {

        List<List> l2 = new ArrayList<List>();
        StmtIterator stmtit = resource.listProperties();
        while (stmtit.hasNext()) {
            Statement stmt = stmtit.nextStatement();
            if (stmt.getPredicate().getLocalName().equals(predicate)) {
                if (stmt.getObject().isLiteral()) {
                    List l = new ArrayList();
                    l.add(stmt.getSubject().toString());
                    l.add(stmt.getPredicate().toString());
                    l.add(stmt.getObject().asLiteral().getString());
                    l2.add(l);
                } else {
                    List l = new LinkedList();
                    l.add(stmt.getSubject().toString());
                    l.add(stmt.getPredicate().toString());
                    l.add(stmt.getObject().toString());
                    l2.add(l);
                }
            }
        }
        return l2;
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

            String req = String.format("prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                    + "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
                    + "prefix owl: <http://www.w3.org/2002/07/owl#> "
                    + "prefix axis: <http://titan.be/axis-csrm/datamodel/ontology/0.3#> "
                    + "prefix poc: <http://titan.be/axis-poc2015/> "
                    + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                    + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
                    + "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
                    + "select ?name ?image ?type where {?s axis-datamodel:uses <%s> ."
                    + "                   	?s rdf:type axis-datamodel:Entity ."
                    + "					<%s> rdfs:label ?name ."
                    + "					<%s> axis-datamodel:hasRepresentation ?reg ."
                    + "  					?reg axis-datamodel:hasExpression ?emb ."
                    + "  					?emb axis-datamodel:fileName ?image ."
                    + "  					<%s> rdf:type ?type"
                    + "					}", this.URI, this.URI, this.URI, this.URI);
            QueryExecution qe = QueryExecutionFactory.sparqlService(
                    "http://localhost:3030/ds/query", req);

            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                QuerySolution n = rs.next();
//                System.out.println("type:" + n.get("type").asResource().getLocalName());
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
//                System.out.println("name:"+n.get("name").asLiteral().getString());
                this.name = n.get("name").asLiteral().getString();
                

            }
            qe.close();
        }
    }
//    public void constructEntity() {
//        if (this.URI.contains("dbpedia")) {
//            selectlodFromEntity(this);
//        } else {
//            Model m = selectFromEntity(this.URI);
//            Resource resource = m.getResource(this.URI);
//
//            List<List> l = browseModel(resource, "uses");
//
//            m = selectFromEntityWithPredicat(this.URI, "axis-datamodel:uses");
//            if (m.isEmpty()) {
//                l = browseModel(resource, "label");
//                if (!l.isEmpty()) {
//                    this.name = (String) l.get(0).get(2);
//                }
//                l = browseModel(resource, "type");
//                if (!l.isEmpty()) {
//                    String type = (String) l.get(0).get(2);
//                    if (type.contains("PhysicalPerson")) {
//                        this.type = "person";
//                    }
//                    if (type.contains("Event")) {
//                        this.type = "event";
//                    }
//                    if (type.contains("PhysicalObject")) {
//                        this.type = "object";
//                    }
//                    if (type.contains("Place")) {
//                        this.type = "location";
//                    }
//                    if (type.contains("MoralPerson")) {
//                        this.type = "organisation";
//                    }
//                    //        if(type.contains(""))
//                    //            this.type = "activity";
//                }
//                ResultSet rs = selectFromEntity("?s", "axis-datamodel:uses", "<" + this.URI + ">");
//                if (rs.hasNext()) {
//                    String newUri = rs.nextSolution().get("s").toString();
//                    m = selectFromEntity(newUri);
//                    resource = m.getResource(newUri);
//                    l = browseModel(resource, "uses");
//                    m = selectFromEntityWithPredicat(newUri, "axis-datamodel:uses");
//                }
//            }
//            Iterator it = l.iterator();
//            while (it.hasNext()) {
//                List list = (List) it.next();
//                resource = m.getResource(list.get(2).toString());
//                l = browseModel(resource, "label");
//                if (!l.isEmpty()) {
//                    this.name = (String) l.get(0).get(2);
//                }
//                l = browseModel(resource, "type");
//                if (!l.isEmpty()) {
//                    String type = (String) l.get(0).get(2);
//                    if (type.contains("PhysicalPerson")) {
//                        this.type = "person";
//                    }
//                    if (type.contains("Event")) {
//                        this.type = "event";
//                    }
//                    if (type.contains("PhysicalObject")) {
//                        this.type = "object";
//                    }
//                    if (type.contains("Place")) {
//                        this.type = "location";
//                    }
//                    if (type.contains("MoralPerson")) {
//                        this.type = "organisation";
//                    }
//                    //        if(type.contains(""))
//                    //            this.type = "activity";
//                }
//                List l1 = browseModel(resource, "hasRepresentation");
//                Iterator it1 = l1.iterator();
//                if (!l1.isEmpty()) {
//                    while (it1.hasNext()) {
//                        List o = (List) it1.next();
//                        Model m1 = selectFromEntityWithPredicat(o.get(2).toString(), "axis-datamodel:hasExpression");
//
//                        Iterator itt = m1.listSubjects();
//                        while (itt.hasNext()) {
//                            Resource re = m1.getResource(itt.next().toString());
//                            List<List> l2 = browseModel(re, "type");
//                            if (!l2.isEmpty()) {
//                                if (l2.get(0).get(2).toString().contains("EmbodimentOfImageFile")) {
//                                    List<List> l3 = browseModel(re, "fileName");
//                                    this.image = l3.get(0).get(2).toString();
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

    public void insertName(Property p) {
        insert(selectRegOfEntity(this.URI, "Document"), "rdfs:label", p.getValue(), "fr");
    }

    public void insertSameAs(Property p) {
        insert(this.URI, "owl:sameAs", p.getEnt()[0].getURI());
    }

    public void insertDescription(Property p) {
        insert(selectRegOfEntity(this.URI, "Document"), "rdf:Description", p.getValue(), "fr");
    }

    public void insertImage(Property p) {

        String uri1 = insert("rdf:type", "axis-datamodel:RegOfPhotoItem");
        String uri2 = insert("rdf:type", "axis-datamodel:Location");
        String uri3 = insert("rdf:type", "axis-datamodel:EmbodimentOfImageFile");

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

    public PropertyAdmin getPropertyAdmin(String propertyName, String p) {
        String req = String.format("prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
                + "prefix owl: <http://www.w3.org/2002/07/owl#> "
                + "prefix poc: <http://titan.be/axis-poc2015/> "
                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
                + "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
                + "PREFIX schema: <https://schema.org/>"
                + "PREFIX dbont: <http://dbpedia.org/ontology/>"
                + "select ?var where {?s axis-datamodel:uses <%s> ."
                + "?s rdf:type axis-datamodel:Entity ."
                + "<%s> %s ?var "
                + "}", this.getURI(), this.getURI(), p);

//        String req2 = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
//                + "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
//                + "prefix owl: <http://www.w3.org/2002/07/owl#> "
//                + "prefix poc: <http://titan.be/axis-poc2015/> "
//                + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
//                + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
//                + "PREFIX axis-datamodel: <http://titan.be/axis-csrm/datamodel/ontology/0.3#>"
//                + "PREFIX schema: <https://schema.org/>"
//                + "PREFIX dbont: <http://dbpedia.org/ontology/>"
//                + "select * where { <%s> owl:sameAs ?o"
//                + "}";

        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", req);

        ResultSet rs = qe.execSelect();
        PropertyAdmin pa = new PropertyAdmin();
        ArrayList<Entity> ale = new ArrayList<Entity>();
        while (rs.hasNext()) {
            QuerySolution n = rs.next();
            pa.setName(propertyName);
            if (n.get("var").isResource()) {
                Entity e = new Entity();
//                QueryExecution qe1 = QueryExecutionFactory.sparqlService(
//                        "http://localhost:3030/ds/query", String.format(req2, n.get("var").asResource().getURI()));
//                ResultSet rs1 = qe1.execSelect();
//                if (rs1.hasNext()) {
//                    e.setURI(rs1.next().get("o").asResource().getURI());
//                    e.constructEntity();
//                    ale.add(e);
//                    pa.setType("uri");
//                    qe1.close();
//                } else {
                    e.setURI(n.get("var").asResource().getURI());
                    e.constructEntity();
                    ale.add(e);
                    pa.setType("uri");
//                }
            }
            if (n.get("var").isLiteral()) {
                    pa.setType("fr");
                    pa.setValue_locale(n.get("var").asLiteral().getString());
            }

        }
        if (!ale.isEmpty()) {
            Entity[] ret = new Entity[ale.size()];
            pa.setEntity_locale((Entity[]) ale.toArray(ret));
            qe.close();
            return pa;
        }

        qe.close();
        return pa;
    }
    
//    public PropertyAdmin getPropertyAdmin(String propertyName, String type) {
//        PropertyAdmin pa = new PropertyAdmin();
//        ArrayList<Entity> ale = new ArrayList<Entity>();
//        Model m = selectFromEntity(this.getURI());
//        Resource resource = m.getResource(this.getURI());
//        List l = browseModel(resource, "uses");
//
//        m = selectFromEntityWithPredicat(this.getURI(), "axis-datamodel:uses");
//        if (m.isEmpty()) {
//            ResultSet rs = selectFromEntity("?s", "axis-datamodel:uses", "<" + this.getURI() + ">");
//            if (rs.hasNext()) {
//                String newUri = rs.nextSolution().get("s").toString();
//                m = selectFromEntity(newUri);
//                resource = m.getResource(newUri);
//                l = browseModel(resource, "uses");
//                m = selectFromEntityWithPredicat(newUri, "axis-datamodel:uses");
//            }
//        }
//        Iterator it = l.iterator();
//
//        if (type.equals("entity")) {
//            while (it.hasNext()) {
//                List list = (List) it.next();
//                resource = m.getResource(list.get(2).toString());
//                List<List> l2 = browseModel(resource, propertyName);
//                if (!l2.isEmpty()) {
//                    Iterator<List> itt = l2.listIterator();
//                    int i = 0;
//                    while (itt.hasNext()) {
//                        String nextUri = itt.next().get(2).toString();
//                        ResultSet rset = selectFromEntity("?s", "axis-datamodel:uses", "<" + nextUri + ">");
//                        if (rset.hasNext()) {
//                            Entity e = new Entity();
//                            e.setURI(l2.get(i).get(2).toString());
//                            e.constructEntity();
//                            ale.add(e);
//                            pa.setType("uri");
//                            i = i + 1;
//                        } else {
//                            ResultSet rs = selectFromEntity("<" + nextUri + ">", "?p", "?o");
//                            while (rs.hasNext()) {
//                                QuerySolution qs = rs.nextSolution();
//                                String nextSol = qs.get("p").toString();
//                                if (nextSol.contains("sameAs")) {
//                                    pa.setType("uri");
//                                    Entity e = new Entity();
//                                    ResultSet rst = selectFromEntity("<" + nextUri + ">", "owl:sameAs", "?o");
//                                    if (rst.hasNext()) {
//                                        e.setURI(rst.nextSolution().get("o").toString());
////                                        System.out.println("URI:"+e.URI);
//                                    }
//                                    ale.add(selectlodFromEntity(e));
//                                    pa.setValue_locale(null);
//                                    pa.setEntity_dbpedia(null);
//                                    pa.setValue_dbpedia(null);
//                                } else {
//                                    if (qs.get("o").isLiteral()) {
//                                        Literal aut = qs.get("o").asLiteral();
//                                        pa.setType(aut.getLanguage());
//                                        pa.setValue_locale(aut.getString());
//                                        pa.setEntity_locale(null);
//
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } else if (type.equals("literal") || (pa.getEntity_dbpedia() == null && pa.getEntity_locale() == null)) {
//            java.lang.Object o = null;
//            if (it.hasNext()) {
//                o = it.next();
//                List list = (List) o;
//                resource = m.getResource(list.get(2).toString());
//                StmtIterator p = resource.listProperties();
//                while (p.hasNext()) {
//                    Statement n = p.nextStatement();
//                    if (n.getPredicate().toString().contains(propertyName)) {
//                        pa.setType(n.getLiteral().getLanguage());
//                        pa.setValue_locale(n.getLiteral().getString());
//                    }
//                }
//            }
//        }
//        Entity[] ret = new Entity[ale.size()];
//        pa.setEntity_locale((Entity[]) ale.toArray(ret));
//        return pa;
//
//    }

    public boolean delete(String prop, String uri2) {
        deleteLinkEntity(this.URI, prop, uri2);
        return true;
    }

    @Override
    public String toString() {
        return "Entity{" + "URI=" + URI + ", name=" + name + ", image=" + image + ", type=" + type + '}';
    }

}
