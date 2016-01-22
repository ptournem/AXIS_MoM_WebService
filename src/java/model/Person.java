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
 * @isAuthorOf loannguyen
 */
public class Person extends Entity {
    public PropertyAdmin birthDate;
    public PropertyAdmin deathDate;
    public PropertyAdmin placeOfBirth;
    public PropertyAdmin mother;
    public PropertyAdmin father;
    public PropertyAdmin isAuthorOf;
    public PropertyAdmin restInPlace;
    public PropertyAdmin sameAs;
    public PropertyAdmin description;
    
    public Property[] getPropertiesPerson() {
        ArrayList<Property> list = new ArrayList<Property>();

	list.add(new Property(this.birthDate.getName(), this.birthDate.getValue_locale(), this.birthDate.getType(), this.birthDate.getEntity_locale()));
        list.add(new Property(this.deathDate.getName(), this.deathDate.getValue_locale(), this.deathDate.getType(), this.deathDate.getEntity_locale()));
        list.add(new Property(this.placeOfBirth.getName(), this.placeOfBirth.getValue_locale(), this.placeOfBirth.getType(), this.placeOfBirth.getEntity_locale()));
	list.add(new Property(this.mother.getName(), this.mother.getValue_locale(), this.mother.getType(), this.mother.getEntity_locale()));
        list.add(new Property(this.father.getName(), this.father.getValue_locale(), this.father.getType(), this.father.getEntity_locale()));
        list.add(new Property(this.isAuthorOf.getName(), this.isAuthorOf.getValue_locale(), this.isAuthorOf.getType(), this.isAuthorOf.getEntity_locale()));
        list.add(new Property(this.restInPlace.getName(), this.restInPlace.getValue_locale(), this.restInPlace.getType(), this.restInPlace.getEntity_locale()));
        list.add(new Property(this.description.getName(), this.description.getValue_locale(), this.description.getType(), this.description.getEntity_locale()));
	Property[] ret = new Property[list.size()];
	return (Property[]) list.toArray(ret);
    }
        
    public PropertyAdmin[] getPropertiesAdminPerson() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();

	list.add(this.birthDate);
        list.add(this.deathDate);
        list.add(this.placeOfBirth);
        list.add(this.mother);
        list.add(this.father);
        list.add(this.isAuthorOf);
        list.add(this.restInPlace);
        //list.add(this.sameAs);
        list.add(this.description);
	
	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }
    
    public void constructPerson() {
        this.birthDate = getPersonPropertyAdmin("birthdate");
        this.placeOfBirth = getPersonPropertyAdmin("birthplace");
        this.deathDate = getPersonPropertyAdmin("deathdate");
        
        this.mother = getPersonPropertyAdmin("mother");
        this.father = getPersonPropertyAdmin("father");
        this.isAuthorOf = getPersonPropertyAdmin("isAuthorOf");
        this.restInPlace = getPersonPropertyAdmin("restinplace");
        this.description = getPersonPropertyAdmin("description");
        
        String testuri = this.getURI();
        ArrayList<Property> p = getPropertiesMapFromLod(this.getURI());
        if(p != null){
        Iterator<Property> it = p.iterator();
        while(it.hasNext()){
            Property n = it.next();
            System.out.println("n:"+n.getName());
            switch (n.getName()) {
                case "birthdate":
                    if(this.getURI().contains("dbpedia")){
                        this.birthDate.setEntity_locale(n.getEnt());
                        this.birthDate.setValue_locale(n.getValue());
                    }else {
                        this.birthDate.setEntity_dbpedia(n.getEnt());
                        this.birthDate.setValue_dbpedia(n.getValue());
                    }
                    break;
                case "deathdate":
                    if(this.getURI().contains("dbpedia")){
                        this.deathDate.setEntity_locale(n.getEnt());
                        this.deathDate.setValue_locale(n.getValue());
                    }else {
                        this.deathDate.setEntity_dbpedia(n.getEnt());
                        this.deathDate.setValue_dbpedia(n.getValue());
                    }
                    break;
                case "birthplace":
                    if(this.getURI().contains("dbpedia")){
                        this.placeOfBirth.setEntity_locale(n.getEnt());
                        this.placeOfBirth.setValue_locale(n.getValue());
                    }else {
                        this.placeOfBirth.setEntity_dbpedia(n.getEnt());
                        this.placeOfBirth.setValue_dbpedia(n.getValue());
                    }
                    break;
                case "description":
                    if(this.getURI().contains("dbpedia")){
                        this.description.setEntity_locale(n.getEnt());
                        this.description.setValue_locale(n.getValue());
                    }else {
                        this.description.setEntity_dbpedia(n.getEnt());
                        this.description.setValue_dbpedia(n.getValue());
                    }
                    break;
                case "mother":
                    if(this.getURI().contains("dbpedia")){
                        this.mother.setEntity_locale(n.getEnt());
                        this.mother.setValue_locale(n.getValue());
                    }else {
                        this.mother.setEntity_dbpedia(n.getEnt());
                        this.mother.setValue_dbpedia(n.getValue());
                    }
                    break;
                case "father":
                    if(this.getURI().contains("dbpedia")){
                        this.father.setEntity_locale(n.getEnt());
                        this.father.setValue_locale(n.getValue());
                    }else {
                        this.father.setEntity_dbpedia(n.getEnt());
                        this.father.setValue_dbpedia(n.getValue());
                    }
                    break;
                case "restinplace":
                    if(this.getURI().contains("dbpedia")){
                        this.restInPlace.setEntity_locale(n.getEnt());
                        this.restInPlace.setValue_locale(n.getValue());
                    }else {
                        this.restInPlace.setEntity_dbpedia(n.getEnt());
                        this.restInPlace.setValue_dbpedia(n.getValue());
                    }
                    break;
                case "isAuthorOf":
                    this.restInPlace.setEntity_dbpedia(n.getEnt());
                    this.restInPlace.setValue_dbpedia(n.getValue());
                    break;
            }
            
        }}
    }
    
    public PropertyAdmin getPersonPropertyAdmin(String propertyName){
          
        PropertyAdmin pa = new PropertyAdmin();
        pa.setName(propertyName);
        switch (propertyName) {
            case "birthdate":
                pa = getPropertyAdmin("birthDate", "literal");
                pa.setName(propertyName);
                break;
            case "deathdate":
                pa = getPropertyAdmin("deathDate", "literal");
                pa.setName(propertyName);
                break;
            case "birthplace":
                pa = getPropertyAdmin("birthPlace", "entity");
                pa.setName(propertyName);
                break;
            case "mother":
                pa = getPropertyAdmin("mother", "entity");
                pa.setName(propertyName);
                break;
            case "father":
                pa = getPropertyAdmin("father", "entity");
                pa.setName(propertyName);
                break;
            case "isAuthorOf":
                pa = getPropertyAdmin("performs", "entity");
                pa.setName(propertyName);
                break;
            case "restinplace":
                pa = getPropertyAdmin("restInPlace", "entity");
                pa.setName(propertyName);
                break;
                
            case "description":
                pa = getPropertyAdmin("Description", "literal");
                pa.setName(propertyName);
                break;
        }
        
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

    public void insertMother(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
	    case "dbpedia":
                uri1 = insert("rdf:type", "axis-datamodel:Person");
                insert(this.getURI(), "dbont:mother", uri1);
                insert(uri1, "dbont:child", this.getURI());
                insert(uri1, "owl:sameAs", p.getEnt().getURI());
                break;
                
            case "our":
                insert(this.getURI(), "dbont:mother", p.getEnt().getURI());
                break;
                
            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:Person");
                insert(this.getURI(), "dbont:mother", uri1);
                insert(uri1, "dbont:child", this.getURI());
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }
    }
    
    public void insertFather(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
	    case "dbpedia":
                uri1 = insert("rdf:type", "axis-datamodel:Person");
                insert(this.getURI(), "dbont:father", uri1);
                insert(uri1, "dbont:child", this.getURI());
                insert(uri1, "owl:sameAs", p.getEnt().getURI());
                break;
                
            case "our":
                insert(this.getURI(), "dbont:father", p.getEnt().getURI());
                break;
                
            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:Person");
                insert(this.getURI(), "dbont:father", uri1);
                insert(uri1, "dbont:child", this.getURI());
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }
    }
    
    public void insertRestInPlace(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
	    case "dbpedia":
                uri1 = insert("rdf:type", "axis-datamodel:Place");
                insert(this.getURI(), "dbont:restInPlace", uri1);
                insert(uri1, "owl:sameAs", p.getEnt().getURI());
                break;
                
            case "our":
                insert(this.getURI(), "dbont:restInPlace", p.getEnt().getURI());
                break;
                
            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:Place");
                insert(this.getURI(), "dbont:restInPlace", uri1);
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }
    }
    
    public void insertIsAuthorOf(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
	    case "dbpedia":
                uri1 = insert("rdf:type", "axis-datamodel:PhysicalObject");
                insert(this.getURI(), "axis-datamodel:performs", uri1);
                insert(uri1, "axis-datamodel:isPerformedBy", this.getURI());
                insert(uri1, "owl:sameAs", p.getEnt().getURI());
                break;
                
            case "our":
                insert(this.getURI(), "axis-datamodel:performs", p.getEnt().getURI());
                insert(p.getEnt().getURI(), "axis-datamodel:isPerformedBy", this.getURI());
                break;
                
            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:PhysicalObject");
                insert(this.getURI(), "axis-datamodel:performs", uri1);
                insert(uri1, "axis-datamodel:isPerformedBy", this.getURI());
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }
    }

    @Override
    public String toString() {
        return "Person{" + "birthDate=" + birthDate + ", \n deathDate=" + deathDate + ",\n placeOfBirth=" + placeOfBirth + ",\n mother=" + mother + ",\n father=" + father + ",\n isAuthorOf=" + isAuthorOf + ",\n restInPlace=" + restInPlace + ",\n sameAs=" + sameAs + ",\n description=" + description + '}';
    }
    
    
    
}
