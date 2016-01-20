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
import java.util.Iterator;
import java.util.List;
import static model.Connector.*;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

/**
 *
 * @author loannguyen
 */
public class Person extends Entity {
    public PropertyAdmin birthDate;
    public PropertyAdmin deathDate;
    public PropertyAdmin placeOfBirth;

    public Property[] getPropertiesPerson() {
        ArrayList<Property> list = new ArrayList<Property>();

	list.add(new Property(this.birthDate.getName(), this.birthDate.getValue_locale(), this.birthDate.getType(), this.birthDate.getEntity_locale()));
        list.add(new Property(this.deathDate.getName(), this.deathDate.getValue_locale(), this.deathDate.getType(), this.deathDate.getEntity_locale()));
        list.add(new Property(this.placeOfBirth.getName(), this.placeOfBirth.getValue_locale(), this.placeOfBirth.getType(), this.placeOfBirth.getEntity_locale()));
	
	Property[] ret = new Property[list.size()];
	return (Property[]) list.toArray(ret);
    }
        
    public PropertyAdmin[] getPropertiesAdminPerson() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();

	list.add(this.birthDate);
        list.add(this.deathDate);
        list.add(this.placeOfBirth);
	
	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }
    
    public void constructPerson() {
        this.birthDate = getPersonPropertyAdmin("birthDate");
        this.deathDate = getPersonPropertyAdmin("deathDate");
        this.placeOfBirth = getPersonPropertyAdmin("birthPlace");
        
        ArrayList<Property> p = getPropertiesMapFromLod(this.getURI());
        Iterator<Property> it = p.iterator();
        while(it.hasNext()){
            Property n = it.next();
            switch (n.getName()) {
                case "birthDate":
                    this.birthDate.setEntity_dbpedia(n.getEnt());
                    this.birthDate.setValue_dbpedia(n.getValue());
                    break;
                case "deathDate":
                    this.deathDate.setEntity_dbpedia(n.getEnt());
                    this.deathDate.setValue_dbpedia(n.getValue());
                    break;
                case "birthPlace":
                    this.placeOfBirth.setEntity_dbpedia(n.getEnt());
                    this.placeOfBirth.setValue_dbpedia(n.getValue());
                default:
                    throw new AssertionError();
            }
            
        }
    }
    
    public PropertyAdmin getPersonPropertyAdmin(String propertyName){
          
        PropertyAdmin pa = new PropertyAdmin();
        pa.setName(propertyName);
        switch (propertyName) {
            case "birthDate":
                pa = getPropertyAdmin(propertyName, "literal");
                pa.setName(propertyName);
                break;
            case "deathDate":
                pa = getPropertyAdmin(propertyName, "literal");
                pa.setName(propertyName);
                break;
            case "birthPlace":
                pa = getPropertyAdmin(propertyName, "entity");
                pa.setName(propertyName);
                break;
            default:
                throw new AssertionError();
        }
        
//        Model m = selectFromEntity(this.getURI());
//        Resource resource = m.getResource(this.getURI());
//        List l = browseModel(resource, "uses");
//        
//        m = selectFromEntityWithPredicat(this.getURI(), "axis-datamodel:uses");
//        if(m.isEmpty()){
//            ResultSet rs = selectFromEntity("?s", "axis-datamodel:uses", "<"+this.getURI()+">");
//            if(rs.hasNext()){
//                QuerySolution qso = rs.nextSolution();
//                    String newUri = qso.get("s").toString();
//                    m = selectFromEntity(newUri);
//                    resource = m.getResource(newUri);
//                    l = browseModel(resource, "uses");
//                    if(!l.isEmpty()){
//                        m = selectFromEntityWithPredicat(newUri, "axis-datamodel:uses");
//                    }
//            }
//        }
//        Iterator it = l.iterator();
//        java.lang.Object o = null;
//        if(it.hasNext()){
//            o = it.next();
//            }
//        List list = (List) o;
//        resource = m.getResource(list.get(2).toString());
//        StmtIterator p = resource.listProperties();
//        
//        while(p.hasNext()){
//            Statement n = p.nextStatement();
//            if(propertyName.equals("birthDate")){
////                System.out.println("birthDate:n.getPredicate().toString():"+n.getPredicate().toString());
//                if(n.getPredicate().toString().contains("birthDate")){
//                    pa.setType(n.getLiteral().getLanguage());
//                    pa.setValue_locale(n.getLiteral().getString());
//                }
//            }
//            if(propertyName.equals("deathDate")){
//                if(n.getPredicate().toString().contains("deathDate")){
////                    System.out.println("deathDate:n.getPredicate().toString():"+n.getPredicate().toString());
//                    pa.setType(n.getLiteral().getLanguage());
//                    pa.setValue_locale(n.getLiteral().getString());
//                }
//            }
//            if(propertyName.equals("birthplace")){
//                if(n.getPredicate().toString().contains("birthPlace")){
//                    pa = getPropertyAdmin("birthPlace");
//                }
//            }
//        }
        return pa;
    }
    
    public void insertBirthDate(Property p) {
        insert(this.getURI(), "schema:birthDate", p.getValue(), p.getType());
    }
    
    public void insertDeathDate(Property p) {
        insert(this.getURI(), "schema:deathDate", p.getValue(), p.getType());
    }
    
    public void insertPlaceOfBirth(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
	    case "dbpedia":
                uri1 = insert("rdf:type", "axis-datamodel:Place");
                insert(this.getURI(), "dbont:birthPlace", uri1);
                insert(uri1, "owl:sameAs", p.getEnt().getURI());
                break;
                
            case "our":
                insert(this.getURI(), "dbont:birthPlace", p.getEnt().getURI());
                break;
                
            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:Place");
                insert(this.getURI(), "dbont:birthPlace", uri1);
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }
    }

    @Override
    public String toString() {
        return "Person{" + "birthDate=" + birthDate + ", deathDate=" + deathDate + ", placeOfBirth=" + placeOfBirth + '}';
    }
    
}
