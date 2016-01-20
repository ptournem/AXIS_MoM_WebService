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
import java.util.ListIterator;
import java.util.Vector;
import static model.Connector.*;
import model.TestWebService;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;


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


public List<List> browseModel(Resource resource, String predicate){
        
        List<List> l2 = new ArrayList<List>();
        StmtIterator stmtit = resource.listProperties();
        while(stmtit.hasNext()){
            Statement stmt = stmtit.nextStatement();
            if(stmt.getPredicate().getLocalName().equals(predicate)){
                if(stmt.getObject().isLiteral()){
                    List l = new ArrayList();
                    l.add(stmt.getSubject().toString());
                    l.add(stmt.getPredicate().toString());
                    l.add(stmt.getObject().asLiteral().getString());
                    l2.add(l);
                }else{
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
    
public void constructEntity() {
        
        Model m = selectFromEntity(this.URI);
        Resource resource = m.getResource(this.URI);
        List<List> l = browseModel(resource, "label");
        if(!l.isEmpty()){
        this.name = (String) l.get(0).get(2);
        }
        l = browseModel(resource, "type");
        String type = (String) l.get(0).get(2);
        
        if(type.contains("PhysicalPerson"))
            this.type = "person";
        if(type.contains("Event"))
            this.type = "event";
        if(type.contains("PhysicalObject"))
            this.type = "object";
        if(type.contains("Place"))
            this.type = "location";
        if(type.contains("MoralPerson"))
            this.type = "organisation";
//        if(type.contains(""))
//            this.type = "activity";
        
        l = browseModel(resource, "uses");
        
        m = selectFromEntityWithPredicat(this.URI, "axis-datamodel:uses");
        if(m.isEmpty()){
            ResultSet rs = selectFromEntity("?s", "axis-datamodel:uses", "<"+this.URI+">");
            if(rs.hasNext()){
                String newUri = rs.nextSolution().get("s").toString();
                m = selectFromEntity(newUri);
                resource = m.getResource(newUri);
                l = browseModel(resource, "uses");
                m = selectFromEntityWithPredicat(newUri, "axis-datamodel:uses");
            }
        }
        Iterator it = l.iterator();
        while(it.hasNext()){
            List list = (List) it.next();
            resource = m.getResource(list.get(2).toString());
            l = browseModel(resource, "label");
            if(!l.isEmpty()){
            this.name = (String) l.get(0).get(2);
            }
            List l1 = browseModel(resource, "hasRepresentation");
            Iterator it1 = l1.iterator();
            if(!l1.isEmpty()){
                while(it1.hasNext()){
                    List o = (List) it1.next();
                    Model m1 = selectFromEntityWithPredicat(o.get(2).toString(), "axis-datamodel:hasExpression");
                    
                    Iterator itt = m1.listSubjects();
                    while(itt.hasNext()){
                        Resource re = m1.getResource(itt.next().toString());
                        List<List> l2 = browseModel(re, "type");
                        if(!l2.isEmpty()){
                            if(l2.get(0).get(2).toString().contains("EmbodimentOfImageFile")){
                            List<List> l3 = browseModel(re, "fileName");
                            this.image = l3.get(0).get(2).toString();
                            }
                        }
                    }
                    }
                }
        }
    }
    
    public void insertName(Property p) {
        insert(this.URI, "rdfs:label", p.getValue(), "fr");
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
        
        if(p.getType() == "uri") {
            if(p.getEnt().getURI().contains("dbpedia")) {
                return "dbpedia";
            }
            else {
                return "our";
            }
        }
        else {
            return "literal";
        }
    }
    

    @Override
    public String toString() {
        return "Entity{" + "URI=" + URI + ", name=" + name + ", image=" + image + ", type=" + type + '}';
    }
    
}
