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
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
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
    public PropertyAdmin parent;
    public PropertyAdmin child;
    public PropertyAdmin isAuthorOf;
    public PropertyAdmin restInPlace;
    public PropertyAdmin sameAs;
    public PropertyAdmin description;
    public PropertyAdmin socialNetwork;
    
    public Property[] getPropertiesPerson() {
        ArrayList<Property> list = new ArrayList<Property>();

        if (!((this.birthDate.getEntity_locale() == null) && (this.birthDate.getValue_locale() == null))) {
            list.add(new Property(this.birthDate.getName(), this.birthDate.getValue_locale(), this.birthDate.getEntity_locale(), this.birthDate.getType(), this.birthDate.getLang()));
        }
        if (!((this.deathDate.getEntity_locale() == null) && (this.deathDate.getValue_locale() == null))) {
            list.add(new Property(this.deathDate.getName(), this.deathDate.getValue_locale(), this.deathDate.getEntity_locale(), this.deathDate.getType(), this.deathDate.getLang()));
        }
        if (!((this.placeOfBirth.getEntity_locale() == null) && (this.placeOfBirth.getValue_locale() == null))) {
            list.add(new Property(this.placeOfBirth.getName(), this.placeOfBirth.getValue_locale(), this.placeOfBirth.getEntity_locale(), this.placeOfBirth.getType(), this.placeOfBirth.getLang()));
        }
        if (!((this.parent.getEntity_locale() == null) && (this.parent.getValue_locale() == null))) {
            list.add(new Property(this.parent.getName(), this.parent.getValue_locale(), this.parent.getEntity_locale(), this.parent.getType(), this.parent.getLang()));
        }
        if (!((this.child.getEntity_locale() == null) && (this.child.getValue_locale() == null))) {
            list.add(new Property(this.child.getName(), this.child.getValue_locale(), this.child.getEntity_locale(), this.child.getType(), this.child.getLang()));
        }
        if (!((this.isAuthorOf.getEntity_locale() == null) && (this.isAuthorOf.getValue_locale() == null))) {
            list.add(new Property(this.isAuthorOf.getName(), this.isAuthorOf.getValue_locale(), this.isAuthorOf.getEntity_locale(), this.isAuthorOf.getType(), this.isAuthorOf.getLang()));
        }
        if (!((this.restInPlace.getEntity_locale() == null) && (this.restInPlace.getValue_locale() == null))) {
            list.add(new Property(this.restInPlace.getName(), this.restInPlace.getValue_locale(), this.restInPlace.getEntity_locale(), this.restInPlace.getType(), this.restInPlace.getLang()));
        }
        if (!((this.description.getEntity_locale() == null) && (this.description.getValue_locale() == null))) {
            list.add(new Property(this.description.getName(), this.description.getValue_locale(), this.description.getEntity_locale(), this.description.getType(), this.description.getLang()));
        }
        if (!((this.socialNetwork.getEntity_locale() == null) && (this.socialNetwork.getValue_locale() == null))) {
            list.add(new Property(this.socialNetwork.getName(), this.socialNetwork.getValue_locale(), this.socialNetwork.getEntity_locale(), this.socialNetwork.getType(),this.socialNetwork.getLang()));
        }
        Property[] ret = new Property[list.size()];
        return (Property[]) list.toArray(ret);
    }

    public PropertyAdmin[] getPropertiesAdminPerson() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();

        this.birthDate.setType("date");
        this.deathDate.setType("date");
        
        list.add(this.birthDate);
        list.add(this.deathDate);
        list.add(this.placeOfBirth);
        list.add(this.parent);
        list.add(this.child);
        list.add(this.isAuthorOf);
        list.add(this.restInPlace);
        list.add(this.sameAs);
        list.add(this.description);
        list.add(this.socialNetwork);

        PropertyAdmin[] ret = new PropertyAdmin[list.size()];
        return (PropertyAdmin[]) list.toArray(ret);
    }

    public void constructPerson(boolean getdbpedia) {
        this.birthDate = new PropertyAdmin();
        this.birthDate.setName("birthdate");
        this.placeOfBirth = new PropertyAdmin();
        this.placeOfBirth.setName("birthplace");
        this.deathDate = new PropertyAdmin();
        this.deathDate.setName("deathdate");
        this.parent = new PropertyAdmin();
        this.parent.setName("parent");
        this.child = new PropertyAdmin();
        this.child.setName("child");
        this.isAuthorOf = new PropertyAdmin();
        this.isAuthorOf.setName("isAuthorOf");
        this.restInPlace = new PropertyAdmin();
        this.restInPlace.setName("restinplace");
        this.description = new PropertyAdmin();
        this.description.setName("description");
        this.sameAs = new PropertyAdmin();
        this.sameAs.setName("sameas");
        this.socialNetwork = new PropertyAdmin();
        this.socialNetwork.setName("socialnetwork");
        if (!this.getURI().contains("dbpedia")) {
            String req = String.format($PREFIXS
                    + " select ?description ?deathdate ?birthdate ?socnet "
                    + " (group_concat(?parent;separator=\"&&&&\") as ?parents)"
                    + " (group_concat(?child;separator=\"&&&&\") as ?childs)"
                    + " (group_concat(?restinplace;separator=\"&&&&\") as ?restinplaces) "
                    + " (group_concat(?birthplace;separator=\"&&&&\") as ?birthplaces)"
                    + " (group_concat(?isauthorof;separator=\"&&&&\") as ?isauthorofs) "
                    + " (group_concat(?same;separator=\"&&&&\") as ?sameas) where {"
                    + " values ?uri { <%s> }"
                    + " ?e axis-datamodel:uses ?uri ."
                    + " ?e a axis-datamodel:Entity ."
                    + " optional{ "
                    + " ?uri axis-datamodel:hasRepresentation ?reg ."
                    + " ?reg a axis-datamodel:RegOfAgent."
                    + " optional{ ?reg dbont:parent ?parent .}"
                    + " optional{ ?reg dbont:child ?child .}"
                    + " optional{ ?reg dbont:birthPlace ?birthplace .}"
                    + " optional{ ?reg schema:birthDate ?birthdate .}"
                    + " optional{ ?reg schema:deathDate ?deathdate .}"
                    + " optional{ ?reg dbont:restInPlace ?restinplace .}"
                    + " optional{ ?reg axis-datamodel:performs ?isauthorof .}"
                    + " }"
                    + " optional{ "
                    + " ?uri axis-datamodel:hasRepresentation ?doc ."
                    + " ?doc a axis-datamodel:Document .  "
                    + " optional{ ?doc rdf:Description ?description .}"
                    + " optional{ ?doc axis-datamodel:socialNetwork ?socnet .}"
                    + " }"
                    + " optional{ ?uri owl:sameAs ?same .}"
                    + " } group by  ?deathdate ?birthdate ?description ?socnet", this.getURI());
            Query query = QueryFactory.create(req);
            QueryExecution qe = QueryExecutionFactory.sparqlService(
                    "http://localhost:3030/ds/query", query);

            ResultSet rs = qe.execSelect();
            if (rs.hasNext()) {
                QuerySolution rep = rs.next();
                if (rep.get("description") != null) {
                    this.description.setValue_locale(rep.get("description").asLiteral().getString());
                    this.description.setLang(rep.get("description").asLiteral().getLanguage());
                    this.description.setType("string");
                }
                if (rep.get("socnet") != null) {
                    this.socialNetwork.setValue_locale(rep.get("socnet").asLiteral().getString());
                    this.socialNetwork.setLang(rep.get("socnet").asLiteral().getLanguage());
                    this.socialNetwork.setType("string");
                }
                if (rep.get("birthdate") != null) {
                    this.birthDate.setType("date");
                    this.birthDate.setValue_locale(rep.get("birthdate").asLiteral().getString());
                    this.birthDate.setLang(rep.get("birthdate").asLiteral().getLanguage());
                }
                if (rep.get("deathdate") != null) {
                    this.deathDate.setValue_locale(rep.get("deathdate").asLiteral().getString());
                    this.deathDate.setType("date");
                    this.deathDate.setLang(rep.get("deathdate").asLiteral().getLanguage());
                }
                if (rep.get("parents") != null) {
                    Entity[] t = getEntityTab(rep.get("parents").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.parent.setValue_locale(rep.get("parents").asLiteral().getString());
                        this.parent.setLang(rep.get("parents").asLiteral().getLanguage());
                        this.parent.setType("string");
                    } else {
                        this.parent.setEntity_locale(t);
                        this.parent.setType("uri");
                        this.parent.setLang("fr");
                    }
                }
                if (rep.get("sameas") != null) {
                    Entity[] t = getEntityTab(rep.get("sameas").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.sameAs.setValue_locale(rep.get("sameas").asLiteral().getString());
                        this.sameAs.setLang(rep.get("sameas").asLiteral().getLanguage());
                        this.sameAs.setType("string");
                    } else {
                        this.sameAs.setEntity_locale(t);
                        this.sameAs.setType("uri");
                        this.sameAs.setLang("fr");
                    }
                }
                if (rep.get("childs") != null) {
                    Entity[] t = getEntityTab(rep.get("childs").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.child.setValue_locale(rep.get("childs").asLiteral().getString());
                        this.child.setLang(rep.get("childs").asLiteral().getLanguage());
                        this.child.setType("string");
                    } else {
                        this.child.setEntity_locale(t);
                        this.child.setType("uri");
                        this.child.setLang("fr");
                    }
                }
                if (rep.get("birthplaces") != null) {
                    Entity[] t = getEntityTab(rep.get("birthplaces").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.placeOfBirth.setValue_locale(rep.get("birthplaces").asLiteral().getString());
                        this.placeOfBirth.setLang(rep.get("birthplaces").asLiteral().getLanguage());
                        this.placeOfBirth.setType("string");
                    } else {
                        this.placeOfBirth.setEntity_locale(t);
                        this.placeOfBirth.setType("uri");
                        this.placeOfBirth.setLang("fr");
                    }
                }
                if (rep.get("restinplaces") != null) {
                    Entity[] t = getEntityTab(rep.get("restinplaces").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.restInPlace.setValue_locale(rep.get("restinplaces").asLiteral().getString());
                        this.restInPlace.setLang(rep.get("restinplaces").asLiteral().getLanguage());
                        this.restInPlace.setType("string");
                    } else {
                        this.restInPlace.setEntity_locale(t);
                        this.restInPlace.setType("uri");
                        this.restInPlace.setLang("fr");
                    }
                }
                if (rep.get("isauthorofs") != null) {
                    Entity[] t = getEntityTab(rep.get("isauthorofs").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.isAuthorOf.setValue_locale(rep.get("isauthorofs").asLiteral().getString());
                        this.isAuthorOf.setLang(rep.get("isauthorofs").asLiteral().getLanguage());
                        this.isAuthorOf.setType("string");
                    } else {
                        this.isAuthorOf.setEntity_locale(t);
                        this.isAuthorOf.setType("uri");
                        this.isAuthorOf.setLang("fr");
                    }
                }
            }
            qe.close();
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
                        case "parent":
                            this.parent.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.parent.setEntity_locale(n.getEnt());
                                this.parent.setValue_locale(n.getValue());
                            } else {
                                this.parent.setEntity_dbpedia(n.getEnt());
                                this.parent.setValue_dbpedia(n.getValue());
                            }
                            break;
                        case "child":
                            this.child.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.child.setEntity_locale(n.getEnt());
                                this.child.setValue_locale(n.getValue());

                            } else {
                                this.child.setEntity_dbpedia(n.getEnt());
                                this.child.setValue_dbpedia(n.getValue());
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
    
    public void insertBirthDate(Property p) {
        insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "schema:birthDate", p.getValue(), p.getType());
    }

    public void insertDeathDate(Property p) {
        insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "schema:deathDate", p.getValue(), p.getType());
    }

    public void insertPlaceOfBirth(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "dbont:birthPlace", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfPlace"), "dbont:birthPlaceOf", this.getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "dbont:birthPlace", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfPlace"), "dbont:birthPlaceOf", this.getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "dbont:birthPlaceOf", p.getValue(), p.getType());
                break;
        }
    }

    public void insertParent(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "dbont:parent", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfAgent"), "dbont:child", this.getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "dbont:parent", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfAgent"), "dbont:child", this.getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "dbont:parent", p.getValue(), p.getType());
                break;
        }
    }

    public void insertChild(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "dbont:child", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfAgent"), "dbont:parent", this.getURI());

//                uri1 = insert("rdf:type", "axis-datamodel:Person");
//                insert(this.getURI(), "dbont:mother", uri1);
//                insert(uri1, "dbont:child", this.getURI());
//                insert(uri1, "owl:sameAs", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "dbont:child", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfAgent"), "dbont:parent", this.getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "dbont:child", p.getValue(), p.getType());
                break;
        }
    }
    

    public void insertRestInPlace(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "dbont:restInPlace", p.getEnt()[0].getURI());
//                uri1 = insert("rdf:type", "axis-datamodel:Place");
//                insert(this.getURI(), "dbont:restInPlace", uri1);
//                insert(uri1, "owl:sameAs", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "dbont:restInPlace", p.getEnt()[0].getURI());
                break;

            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:Place");
                insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "dbont:restInPlace", uri1);
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }
    }

    public void insertIsAuthorOf(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "axis-datamodel:performs", p.getEnt()[0].getURI());
                insert(p.getEnt()[0].getURI(), "axis-datamodel:isPerformedBy", this.getURI());
//                uri1 = insert("rdf:type", "axis-datamodel:PhysicalObject");
//                insert(this.getURI(), "axis-datamodel:performs", uri1);
//                insert(uri1, "axis-datamodel:isPerformedBy", this.getURI());
//                insert(uri1, "owl:sameAs", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "axis-datamodel:performs", p.getEnt()[0].getURI());
                insert(p.getEnt()[0].getURI(), "axis-datamodel:isPerformedBy", this.getURI());
                break;

            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:PhysicalObject");
                insert(selectRegOfEntity(this.getURI(), "RegOfAgent"), "axis-datamodel:performs", uri1);
                insert(uri1, "axis-datamodel:isPerformedBy", this.getURI());
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }
    }

    @Override
    public String toString() {
        return "Person{" + "birthDate=" + birthDate + ", \n deathDate=" + deathDate + ",\n placeOfBirth=" + placeOfBirth + ",\n parent=" + parent + ",\n child=" + child + ",\n isAuthorOf=" + isAuthorOf + ",\n restInPlace=" + restInPlace + ",\n sameAs=" + sameAs + ",\n description=" + description + '}';
    }

}
