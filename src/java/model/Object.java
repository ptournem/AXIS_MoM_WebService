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
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

/**
 *
 * @author APP-Riad.Belmahi
 */
public class Object extends Entity {

    public PropertyAdmin location;
    public PropertyAdmin author;
    public PropertyAdmin sameAs;
    public PropertyAdmin description;

    public Property[] getPropertiesObject() {
        ArrayList<Property> list = new ArrayList<Property>();
//        entityBrowser(this.getURI()
        if (!this.author.getName().isEmpty()) {
            list.add(new Property(this.author.getName(), this.author.getValue_locale(),  this.author.getEntity_locale(), this.author.getType(), this.author.getLang()));
        }
        if (!this.location.getName().isEmpty()) {
            list.add(new Property(this.location.getName(), this.location.getValue_locale(),  this.location.getEntity_locale(), this.location.getType(), this.location.getLang()));
        }
        if (!this.description.getName().isEmpty()) {
            list.add(new Property(this.description.getName(), this.description.getValue_locale(), this.description.getEntity_locale(), this.description.getType(),this.description.getLang()));
        }
        //list.add(new Property(this.dateCreation.getName(), this.dateCreation.getValue_locale(), this.dateCreation.getType(), this.dateCreation.getEntity_locale()));
        Property[] ret = new Property[list.size()];
        return (Property[]) list.toArray(ret);
    }

    public PropertyAdmin[] getPropertiesAdminObject() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();

        list.add(this.author);
        list.add(this.location);
        list.add(this.sameAs);
        list.add(this.description);
        //list.add(this.dateCreation);

        PropertyAdmin[] ret = new PropertyAdmin[list.size()];
        return (PropertyAdmin[]) list.toArray(ret);
    }

    public void constructObject(boolean getdbpedia) {
        this.author = new PropertyAdmin();
        this.author.setName("author");
        this.location = new PropertyAdmin();
        this.location.setName("location");
        this.description = new PropertyAdmin();
        this.description.setName("description");
        this.sameAs = new PropertyAdmin();
        this.sameAs.setName("sameas");
        if (!this.getURI().contains("dbpedia")) {
            String req = String.format($PREFIXS
                    + " select ?description  (group_concat(?location; separator=\"&&&&\") as ?locations) "
                    + " (group_concat(?author;separator=\"&&&&\") as ?authors) "
                    + " (group_concat(?same;separator=\"&&&&\") as ?sameas) where {"
                    + " values ?uri { <%s> }"
                    + " ?e axis-datamodel:uses ?uri ."
                    + " ?e a axis-datamodel:Entity ."
                    + " ?uri axis-datamodel:hasRepresentation ?reg ."
                    + " ?reg a axis-datamodel:RegOfObjectItem ."
                    + " ?uri axis-datamodel:hasRepresentation ?doc ."
                    + " ?doc a axis-datamodel:Document .  "
                    + " optional{ ?doc rdf:Description ?description .}"
                    + " optional{ ?uri owl:sameAs ?same .}"
                    + " optional{ ?uri axis-datamodel:isPerformedBy ?author .}"
                    + " optional{ ?reg axis-datamodel:takePlaceIn ?location .}"
                    + " } group by ?description", this.getURI());
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
                if (rep.get("authors") != null) {
                    Entity[] t = getEntityTab(rep.get("authors").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.author.setValue_locale(rep.get("authors").asLiteral().getString());
                        this.author.setType("string");
                        this.author.setLang(rep.get("authors").asLiteral().getLanguage());
                    } else {
                        this.author.setEntity_locale(t);
                        this.author.setType("uri");
                        this.author.setLang("fr");
                    }
                }
                if (rep.get("locations") != null) {
                    Entity[] t = getEntityTab(rep.get("locations").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.location.setValue_locale(rep.get("locations").asLiteral().getString());
                        this.location.setType("string");
                        this.location.setLang(rep.get("locations").asLiteral().getLanguage());
                    } else {
                        this.location.setEntity_locale(t);
                        this.location.setType("uri");
                        this.location.setLang("fr");
                    }
                }
                if (rep.get("sameas") != null) {
                    Entity[] t = getEntityTab(rep.get("sameas").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.sameAs.setValue_locale(rep.get("sameas").asLiteral().getString());
                        this.sameAs.setType("string");
                        this.sameAs.setLang(rep.get("sameas").asLiteral().getLanguage());
                    } else {
                        this.sameAs.setEntity_locale(t);
                        this.sameAs.setType("uri");
                        this.sameAs.setLang("fr");
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
                        case "author":
                            this.author.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.author.setEntity_locale(n.getEnt());
                                this.author.setValue_locale(n.getValue());
                            } else {
                                this.author.setEntity_dbpedia(n.getEnt());
                                this.author.setValue_dbpedia(n.getValue());
                            }
                            break;
                        case "location":
                            this.location.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.location.setEntity_locale(n.getEnt());
                                this.location.setValue_locale(n.getValue());
                            } else {
                                this.location.setEntity_dbpedia(n.getEnt());
                                this.location.setValue_dbpedia(n.getValue());
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
                insert(selectRegOfEntity(this.getURI(), "RegOfObjectItem"), "axis-datamodel:takePlaceIn", p.getEnt()[0].getURI());
                insert(p.getEnt()[0].getURI(), "axis-datamodel:isAPlaceOfObject", this.getURI());
//                uri1 = insert("rdf:type", "axis-datamodel:Place");
//                insert(this.getURI(), "axis-datamodel:takePlaceIn", uri1);
//                insert(uri1, "axis-datamodel:isAPlaceOfObject", this.getURI());
//                insert(uri1, "owl:sameAs", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfObjectItem"), "axis-datamodel:takePlaceIn", p.getEnt()[0].getURI());
                insert(p.getEnt()[0].getURI(), "axis-datamodel:isAPlaceOfObject", this.getURI());
                break;

            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:Place");
                insert(selectRegOfEntity(this.getURI(), "RegOfObjectItem"), "axis-datamodel:takePlaceIn", uri1);
                insert(uri1, "axis-datamodel:isAPlaceOfObject", this.getURI());
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }
    }

    public void insertAuthor(Property p) {

        String uri1 = null;

        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfObjectItem"), "axis-datamodel:isPerformedBy", p.getEnt()[0].getURI());
                insert(p.getEnt()[0].getURI(), "axis-datamodel:performs", this.getURI());
//                uri1 = insert("rdf:type", "axis-datamodel:PhysicalPerson");
//                insert(this.getURI(), "axis-datamodel:isPerformedBy", uri1);
//                insert(uri1, "axis-datamodel:performs", this.getURI());
//                insert(uri1, "owl:sameAs", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfObjectItem"), "axis-datamodel:isPerformedBy", p.getEnt()[0].getURI());
                insert(p.getEnt()[0].getURI(), "axis-datamodel:performs", this.getURI());
                break;

            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:PhysicalPerson");
                insert(selectRegOfEntity(this.getURI(), "RegOfObjectItem"), "axis-datamodel:isPerformedBy", uri1);
                insert(uri1, "axis-datamodel:performs", this.getURI());
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }

    }

    @Override
    public String toString() {
        return "Object{" + "location=" + location + ", author=" + author + ", sameAs=" + sameAs + ", description=" + description + '}';
    }

    public String superString() {
        return super.toString();
    }

}
