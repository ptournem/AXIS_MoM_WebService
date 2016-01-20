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
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.StmtIterator;

/**
 *
 * @author APP-Riad.Belmahi
 */

public class Object extends Entity {

    public PropertyAdmin dateCreation;
    public PropertyAdmin location;
    public PropertyAdmin author;
    

    
    public Property[] getPropertiesObject() {
        ArrayList<Property> list = new ArrayList<Property>();

	list.add(new Property(this.author.getName(), this.author.getValue_locale(), this.author.getType(), this.author.getEntity_locale()));
        list.add(new Property(this.location.getName(), this.location.getValue_locale(), this.location.getType(), this.location.getEntity_locale()));
        list.add(new Property(this.dateCreation.getName(), this.dateCreation.getValue_locale(), this.dateCreation.getType(), this.dateCreation.getEntity_locale()));
	
	Property[] ret = new Property[list.size()];
	return (Property[]) list.toArray(ret);
    }
    
    public PropertyAdmin[] getPropertiesAdminObject() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();

	list.add(this.author);
        list.add(this.location);
        list.add(this.dateCreation);
	
	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }
    
    public void constructObject() {
        this.author = getObjectPropertyAdmin("author");
        this.location = getObjectPropertyAdmin("location");
        
    }

    public PropertyAdmin getObjectPropertyAdmin(String propertyName){
        PropertyAdmin pa = new PropertyAdmin();
        pa.setName(propertyName);
                
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
            List<List> l2 = null;
            switch (propertyName) {
                case "author":
                    l2 = browseModel(resource, "isPerformedBy");
                    break;
                case "location":
                    l2 = browseModel(resource, "takePlaceIn");
                    break;
                default:
                    throw new AssertionError();
            }
            if(!l2.isEmpty()){
                ResultSet rset = selectFromEntity("?s", "axis-datamodel:uses", "<"+l2.get(0).get(2).toString()+">");
                if(rset.hasNext()){
                    Entity e = new Entity();
                    e.setURI(l2.get(0).get(2).toString());
                    e.constructEntity();
                    pa.setEntity_locale(e);
                }else{
                ResultSet rs = selectFromEntity("<"+l2.get(0).get(2).toString()+">", "?p", "?o");
                while(rs.hasNext()){
                    QuerySolution qs = rs.nextSolution();
                    String nextSol = qs.get("p").toString();
                    if(nextSol.contains("sameAs")){
                        pa.setType("uri");
                        pa.setValue_locale(null);
                        pa.setEntity_locale(null);
                        Entity e = new Entity();
                        ResultSet rst = selectFromEntity("<"+l2.get(0).get(2).toString()+">", "owl:sameAs", "?o");
                        if(rst.hasNext())
                            e.setURI(rst.nextSolution().get("o").toString());
                        // ajout du construct entity de Riad pour le LoD
                        
                        e.setName(selectlodFromEntity(e).getName());
                        e.setImage(selectlodFromEntity(e).getImage());
                        e.setType(selectlodFromEntity(e).getType());
                        // pa.setValue_dbpedia(récup sur lod avec tes fonctions)
                        pa.setEntity_dbpedia(e);
                    }else{
                        if(qs.get("o").isLiteral()){
                            Literal aut = qs.get("o").asLiteral();
                            pa.setType(aut.getLanguage());
                            pa.setValue_locale(aut.getString());
                            pa.setEntity_locale(null);
                        }
                    }
                }
                }
            }
            }
        return pa;
        
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
                insert(this.getURI(), "axis-datamodel:isPerformedBy", p.getEnt().getURI());
                insert(p.getEnt().getURI(), "axis-datamodel:performs", this.getURI());
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
