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

    public Property[] getPropertiesObject() {
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
        this.placeOfBirth = getPersonPropertyAdmin("placeOfBirth");
    }
    
    public PropertyAdmin getPersonPropertyAdmin(String propertyName){
        PropertyAdmin pa = new PropertyAdmin();
        pa.setName(propertyName);
        Model m = selectFromEntity(this.getURI());
        Resource resource = m.getResource(this.getURI());
        List l = browseModel(resource, "uses");
        
        m = selectFromEntityWithPredicat(this.getURI(), "axis-datamodel:uses");
        if(m.isEmpty()){
            ResultSet rs = selectFromEntity("?s", "axis-datamodel:uses", "<"+this.getURI()+">");
            if(rs.hasNext()){
                QuerySolution qso = rs.nextSolution();
                    String newUri = qso.get("s").toString();
                    m = selectFromEntity(newUri);
                    resource = m.getResource(newUri);
                    l = browseModel(resource, "uses");
                    if(!l.isEmpty()){
                        m = selectFromEntityWithPredicat(newUri, "axis-datamodel:uses");
                        System.out.println("newURI>>"+newUri);
                        System.out.println("m>>>"+m);
                    }
            }
        }
        Iterator it = l.iterator();
        java.lang.Object o = null;
        while(it.hasNext()){
            o = it.next();
            System.out.println("oooooooooooo>>>"+o);
            }
        List list = (List) o;
        resource = m.getResource(list.get(2).toString());
        StmtIterator p = resource.listProperties();
        while(p.hasNext()){
            Statement n = p.nextStatement();
            System.out.println("n>>>"+n);
            if(n.getPredicate().toString().contains("birthDate")){
                System.out.println("birthdate:");
                System.out.println(n.getLiteral());
            }
        }
        return null;
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
}
