/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import Dialog.Entity;
import Dialog.Property;
import Dialog.PropertyAdmin;
import java.util.ArrayList;
import static model.Connector.*;
import java.util.Iterator;
import java.util.List;
import static model.Connector.selectFromEntity;
import static model.Connector.selectFromEntityWithPredicat;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

/**
 *
 * @author APP-Riad.Belmahi
 */

public class Object extends Entity {

    public PropertyAdmin dateCreation;
    public PropertyAdmin location;
    public PropertyAdmin author;
    

    
    public PropertyAdmin[] getPropertiesObject() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();

	list.add(this.author);
        list.add(this.location);
        list.add(this.dateCreation);
	
	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }
    
    public void constructObject() {
        Model m = selectFromEntity(this.getURI());
        Resource resource = m.getResource(this.getURI());
        List l = browseModel(resource, "uses");
        
        m = selectFromEntityWithPredicat(this.getURI(), "axis-datamodel:uses");
        if(m.isEmpty()){
            ResultSet rs = selectFromEntity("?s", "axis-datamodel:uses", "<"+this.getURI()+">");
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
            List<List> l2 = browseModel(resource, "date");
            if(!l2.isEmpty()){
//                PropertyAdmin pa = new PropertyAdmin();
//                pa.setName("dateCreation");
//                pa.setType("fr");
//                pa.setValue_locale(l2.get(0).get(2).toString());
//                pa.setEntity_locale(null);
//                this.dateCreation = pa;
            }
            
//            l2 = browseModel(resource, "takesPlaceIn");
//            if(!l2.isEmpty()){
//                PropertyAdmin pa = new PropertyAdmin();
//                pa.setName("location");
//                pa.setType("uri");
//                pa.setValue_locale(null);
//                Entity e = new Entity();
//                e.setURI(l2.get(0).get(2).toString());
//                e.constructEntity();
//                pa.setEntity_locale(null);
//                this.location = pa;
//            }
            
            l2 = browseModel(resource, "isPerformedBy");
            if(!l2.isEmpty()){
                PropertyAdmin pa = new PropertyAdmin();
                pa.setName("author");
                System.out.println(l2.get(0).get(2).toString());
                ResultSet rs = selectFromEntity("<"+l2.get(0).get(2).toString()+">", "?p", "?o");
                while(rs.hasNext()){
                    QuerySolution qs = rs.nextSolution();
                    String nextSol = qs.get("p").toString();
                    if(nextSol.contains("takePlaceIn")){
                        pa.setType("uri");
                        pa.setValue_locale(null);
                        Entity e = new Entity();
                        e.setURI(l2.get(0).get(2).toString());
                        e.constructEntity();
                        pa.setEntity_locale(e);
                        this.author = pa;
                    }else if(nextSol.contains("sameAs")){
                        pa.setType("uri");
                        pa.setValue_locale(null);
                        Entity e = new Entity();
                        e.setURI(l2.get(0).get(2).toString());
                        
                        // ajout du construct entity de Riad pour le LoD
                        pa.setEntity_locale(e);
                        this.author = pa;
                    }else{
                        pa.setType("fr");
                        System.out.println(qs.get("o").asLiteral().toString());
//                        pa.setValue_locale();
                        pa.setEntity_locale(null);
                        this.author = pa;
                    }
                }
            }
            }
        
    }

    public void insertDateCreation(Property p) {
        
    }
    
    public void insertLocation(Property p) {

        String uri1 = null;
        switch (this.getTypeProperty(p)) {
	    case "dbpedia":
                uri1 = insert("rdf:type", "axis-datamodel:Place");
                insert(this.getURI(), "axis-datamodel:takePlaceIn", uri1);
                insert(uri1, "axis-datamodel:isAPlaceOfObject", this.getURI());
                insert(uri1, "owl:sameAs", p.getEnt().getURI());
                break;
                
            case "our":
                insert(this.getURI(), "axis-datamodel:takePlaceIn", p.getEnt().getURI());
                insert(p.getEnt().getURI(), "axis-datamodel:isAPlaceOfObject", this.getURI());
                break;
                
            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:Place");
                insert(this.getURI(), "axis-datamodel:takePlaceIn", uri1);
                insert(uri1, "axis-datamodel:isAPlaceOfObject", this.getURI());
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }
    }
    
    public void insertAuthor(Property p) {
        
        String uri1 = null;
        
        switch (this.getTypeProperty(p)) {
	    case "dbpedia":
                uri1 = insert("rdf:type", "axis-datamodel:PhysicalPerson");
                insert(this.getURI(), "axis-datamodel:isPerformedBy", uri1);
                insert(uri1, "axis-datamodel:performs", this.getURI());
                insert(uri1, "owl:sameAs", p.getEnt().getURI());
                break;
                
            case "our":
                insert(this.getURI(), "axis-datamodel:takePlaceIn", p.getEnt().getURI());
                insert(p.getEnt().getURI(), "axis-datamodel:isAPlaceOfObject", this.getURI());
                break;
                
            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:PhysicalPerson");
                insert(this.getURI(), "axis-datamodel:isPerformedBy", uri1);
                insert(uri1, "axis-datamodel:performs", this.getURI());
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }
        
    }

    @Override
    public String toString() {
        return "Object{" + "dateCreation=" + dateCreation + ", location=" + location + ", author=" + author + '}';
    }
    

}
