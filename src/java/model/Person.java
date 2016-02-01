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
import static model.Connector.*;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

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

        if (!this.birthDate.getName().isEmpty()) {
            list.add(new Property(this.birthDate.getName(), this.birthDate.getValue_locale(), this.birthDate.getType(), this.birthDate.getEntity_locale()));
        }
        if (!this.deathDate.getName().isEmpty()) {
            list.add(new Property(this.deathDate.getName(), this.deathDate.getValue_locale(), this.deathDate.getType(), this.deathDate.getEntity_locale()));
        }
        if (!this.placeOfBirth.getName().isEmpty()) {
            list.add(new Property(this.placeOfBirth.getName(), this.placeOfBirth.getValue_locale(), this.placeOfBirth.getType(), this.placeOfBirth.getEntity_locale()));
        }
        if (!this.mother.getName().isEmpty()) {
            list.add(new Property(this.mother.getName(), this.mother.getValue_locale(), this.mother.getType(), this.mother.getEntity_locale()));
        }
        if (!this.father.getName().isEmpty()) {
            list.add(new Property(this.father.getName(), this.father.getValue_locale(), this.father.getType(), this.father.getEntity_locale()));
        }
        if (!this.isAuthorOf.getName().isEmpty()) {
            list.add(new Property(this.isAuthorOf.getName(), this.isAuthorOf.getValue_locale(), this.isAuthorOf.getType(), this.isAuthorOf.getEntity_locale()));
        }
        if (!this.restInPlace.getName().isEmpty()) {
            list.add(new Property(this.restInPlace.getName(), this.restInPlace.getValue_locale(), this.restInPlace.getType(), this.restInPlace.getEntity_locale()));
        }
        if (!this.description.getName().isEmpty()) {
            list.add(new Property(this.description.getName(), this.description.getValue_locale(), this.description.getType(), this.description.getEntity_locale()));
        }
        Property[] ret = new Property[list.size()];
        return (Property[]) list.toArray(ret);
    }

    public PropertyAdmin[] getPropertiesAdminPerson() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();

        //DEBUT TEST COCO
//        PropertyAdmin testsameas = new PropertyAdmin();
//        testsameas.setName("sameas");
//        testsameas.setType("uri");
//        Entity vinciDB = new Entity();
//        vinciDB.setURI("http://dbpedia.org/resource/Vinci,_Tuscany");
//        vinciDB.constructEntity();
//        Entity[] tab = new Entity[1];
//        tab[0] = vinciDB;
//        testsameas.setEntity_locale(tab);
        //FIN TEST COCO
        
        list.add(this.birthDate);
        list.add(this.deathDate);
        list.add(this.placeOfBirth);
        list.add(this.mother);
        list.add(this.father);
        list.add(this.isAuthorOf);
        list.add(this.restInPlace);
        list.add(this.sameAs);
        list.add(this.description);

        PropertyAdmin[] ret = new PropertyAdmin[list.size()];
        return (PropertyAdmin[]) list.toArray(ret);
    }

    public void constructPerson(boolean getdbpedia) {
        if (!this.getURI().contains("dbpedia")) {
//            this.placeOfBirth = getPersonPropertyAdmin("birthplace");
//            this.birthDate = getPersonPropertyAdmin("birthdate");
//            this.deathDate = getPersonPropertyAdmin("deathdate");
//            this.mother = getPersonPropertyAdmin("mother");
//            this.father = getPersonPropertyAdmin("father");
//            this.isAuthorOf = getPersonPropertyAdmin("isAuthorOf");
//            this.restInPlace = getPersonPropertyAdmin("restinplace");
//            this.description = getPersonPropertyAdmin("description");
//            this.sameAs = getPersonPropertyAdmin("sameAs");
            this.placeOfBirth = getPropertyAdmin("birthplace", "dbont:birthPlace");
            this.birthDate = getPropertyAdmin("birthdate", "schema:birthDate");
            this.deathDate = getPropertyAdmin("deathdate", "schema:deathDate");
            this.mother = getPropertyAdmin("mother", "dbont:mother");
            this.father = getPropertyAdmin("father", "dbont:father");
            this.isAuthorOf = getPropertyAdmin("isAuthorOf", "axis-datamodel:performs");
            this.restInPlace = getPropertyAdmin("restinplace", "dbont:restInPlace");
            this.description = getPropertyAdmin("description", "rdf:Description");
            this.sameAs = getPropertyAdmin("sameas", "owl:sameAs");
        } else {
            this.birthDate = new PropertyAdmin();
            this.birthDate.setName("birthdate");
            this.placeOfBirth = new PropertyAdmin();
            this.placeOfBirth.setName("birthplace");
            this.deathDate = new PropertyAdmin();
            this.deathDate.setName("deathdate");
            this.mother = new PropertyAdmin();
            this.mother.setName("mother");
            this.father = new PropertyAdmin();
            this.father.setName("father");
            this.isAuthorOf = new PropertyAdmin();
            this.isAuthorOf.setName("isAuthorOf");
            this.restInPlace = new PropertyAdmin();
            this.restInPlace.setName("restinplace");
            this.description = new PropertyAdmin();
            this.description.setName("description");
            this.sameAs = new PropertyAdmin();
            this.sameAs.setName("sameas");
        }
        if (this.getURI().contains("dbpedia") || getdbpedia == true) {
            ArrayList<Property> p = getPropertiesMapFromLod(this);
            if (p != null) {
                Iterator<Property> it = p.iterator();
                while (it.hasNext()) {
                    Property n = it.next();
                    switch (n.getName()) {
                        case "birthdate":
                            this.birthDate.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.birthDate.setEntity_locale(n.getEnt());
                                this.birthDate.setValue_locale(n.getValue());
                            } else {
                                this.birthDate.setEntity_dbpedia(n.getEnt());
                                this.birthDate.setValue_dbpedia(n.getValue());
                            }
                            break;
                        case "deathdate":
                            this.birthDate.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.deathDate.setEntity_locale(n.getEnt());
                                this.deathDate.setValue_locale(n.getValue());
                            } else {
                                this.deathDate.setEntity_dbpedia(n.getEnt());
                                this.deathDate.setValue_dbpedia(n.getValue());
                            }
                            break;
                        case "birthplace":
                            this.placeOfBirth.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.placeOfBirth.setEntity_locale(n.getEnt());
                                this.placeOfBirth.setValue_locale(n.getValue());
                            } else {
                                this.placeOfBirth.setEntity_dbpedia(n.getEnt());
                                this.placeOfBirth.setValue_dbpedia(n.getValue());
                            }
                            break;
                        case "description":
                            this.description.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.description.setEntity_locale(n.getEnt());
                                this.description.setValue_locale(n.getValue());
                            } else {
                                this.description.setEntity_dbpedia(n.getEnt());
                                this.description.setValue_dbpedia(n.getValue());
                            }
                            break;
                        case "mother":
                            this.mother.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.mother.setEntity_locale(n.getEnt());
                                this.mother.setValue_locale(n.getValue());
                            } else {
                                this.mother.setEntity_dbpedia(n.getEnt());
                                this.mother.setValue_dbpedia(n.getValue());
                            }
                            break;
                        case "father":
                            this.father.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.father.setEntity_locale(n.getEnt());
                                this.father.setValue_locale(n.getValue());

                            } else {
                                this.father.setEntity_dbpedia(n.getEnt());
                                this.father.setValue_dbpedia(n.getValue());
                            }
                            break;
                        case "restinplace":
                            this.restInPlace.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.restInPlace.setEntity_locale(n.getEnt());
                                this.restInPlace.setValue_locale(n.getValue());
                            } else {
                                this.restInPlace.setEntity_dbpedia(n.getEnt());
                                this.restInPlace.setValue_dbpedia(n.getValue());
                            }
                            break;
                        case "isAuthorOf":
                            this.isAuthorOf.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.isAuthorOf.setEntity_locale(n.getEnt());
                                this.isAuthorOf.setValue_locale(n.getValue());
                            } else {
                                this.isAuthorOf.setEntity_dbpedia(n.getEnt());
                                this.isAuthorOf.setValue_dbpedia(n.getValue());
                            }
                            break;
                    }

                }
            }
        }
    }

//    public PropertyAdmin getPersonPropertyAdmin(String propertyName) {
//
//        PropertyAdmin pa = new PropertyAdmin();
//        switch (propertyName) {
//            case "birthdate":
//                pa = getPropertyAdmin("birthDate", "literal");
//                pa.setName(propertyName);
//                break;
//            case "deathdate":
//                pa = getPropertyAdmin("deathDate", "literal");
//                pa.setName(propertyName);
//                break;
//            case "birthplace":
//                pa = getPropertyAdmin("birthPlace", "entity");
//                pa.setName(propertyName);
//                break;
//            case "mother":
//                pa = getPropertyAdmin("mother", "entity");
//                pa.setName(propertyName);
//                break;
//            case "father":
//                pa = getPropertyAdmin("father", "entity");
//                pa.setName(propertyName);
//                break;
//            case "isAuthorOf":
//                pa = getPropertyAdmin("performs", "entity");
//                pa.setName(propertyName);
//                break;
//            case "restinplace":
//                pa = getPropertyAdmin("restInPlace", "entity");
//                pa.setName(propertyName);
//                break;
//
//            case "description":
//                pa = getPropertyAdmin("Description", "literal");
//                pa.setName(propertyName);
//                break;
//        }
//
//        return pa;
//    }

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
                insert(this.getURI(), "dbont:birthPlace", p.getEnt()[0].getURI());
//                uri1 = insert("rdf:type", "axis-datamodel:Place");
//                insert(this.getURI(), "dbont:birthPlace", uri1);
//                insert(uri1, "owl:sameAs", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(this.getURI(), "dbont:birthPlace", p.getEnt()[0].getURI());
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
                insert(this.getURI(), "dbont:mother", p.getEnt()[0].getURI());
                insert(p.getEnt()[0].getURI(), "dbont:child", this.getURI());
                
//                uri1 = insert("rdf:type", "axis-datamodel:Person");
//                insert(this.getURI(), "dbont:mother", uri1);
//                insert(uri1, "dbont:child", this.getURI());
//                insert(uri1, "owl:sameAs", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(this.getURI(), "dbont:mother", p.getEnt()[0].getURI());
                insert(p.getEnt()[0].getURI(), "dbont:child", this.getURI());
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
                insert(this.getURI(), "dbont:father", p.getEnt()[0].getURI());
                insert(p.getEnt()[0].getURI(), "dbont:child", this.getURI());
                
//                uri1 = insert("rdf:type", "axis-datamodel:Person");
//                insert(this.getURI(), "dbont:father", uri1);
//                insert(uri1, "dbont:child", this.getURI());
//                insert(uri1, "owl:sameAs", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(this.getURI(), "dbont:father", p.getEnt()[0].getURI());
                insert(p.getEnt()[0].getURI(), "dbont:child", this.getURI());
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
                insert(this.getURI(), "dbont:restInPlace", p.getEnt()[0].getURI());
//                uri1 = insert("rdf:type", "axis-datamodel:Place");
//                insert(this.getURI(), "dbont:restInPlace", uri1);
//                insert(uri1, "owl:sameAs", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(this.getURI(), "dbont:restInPlace", p.getEnt()[0].getURI());
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
                insert(this.getURI(), "axis-datamodel:performs", p.getEnt()[0].getURI());
                insert(p.getEnt()[0].getURI(), "axis-datamodel:isPerformedBy", this.getURI());
//                uri1 = insert("rdf:type", "axis-datamodel:PhysicalObject");
//                insert(this.getURI(), "axis-datamodel:performs", uri1);
//                insert(uri1, "axis-datamodel:isPerformedBy", this.getURI());
//                insert(uri1, "owl:sameAs", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(this.getURI(), "axis-datamodel:performs", p.getEnt()[0].getURI());
                insert(p.getEnt()[0].getURI(), "axis-datamodel:isPerformedBy", this.getURI());
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
