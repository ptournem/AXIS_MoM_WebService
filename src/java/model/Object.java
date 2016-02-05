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
    public PropertyAdmin socialNetwork;

    public Property[] getPropertiesObject() {
        ArrayList<Property> list = new ArrayList<Property>();
//        entityBrowser(this.getURI()
        if (!((this.author.getEntity_locale() == null) && (this.author.getValue_locale() == null))) {
            list.add(new Property(this.author.getName(), this.author.getValue_locale(),  this.author.getEntity_locale(), this.author.getType(), this.author.getLang()));
        }
        if (!((this.location.getEntity_locale() == null) && (this.location.getValue_locale() == null))) {
            list.add(new Property(this.location.getName(), this.location.getValue_locale(),  this.location.getEntity_locale(), this.location.getType(), this.location.getLang()));
        }
        if (!((this.description.getEntity_locale() == null) && (this.description.getValue_locale() == null))) {
            list.add(new Property(this.description.getName(), this.description.getValue_locale(), this.description.getEntity_locale(), this.description.getType(),this.description.getLang()));
        }
        if (!((this.socialNetwork.getEntity_locale() == null) && (this.socialNetwork.getValue_locale() == null))) {
            list.add(new Property(this.socialNetwork.getName(), this.socialNetwork.getValue_locale(), this.socialNetwork.getEntity_locale(), this.socialNetwork.getType(),this.socialNetwork.getLang()));
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
        list.add(this.socialNetwork);
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
        this.socialNetwork = new PropertyAdmin();
        this.socialNetwork.setName("socialnetwork");
//        this.owner = new PropertyAdmin();
//        this.owner.setName("owner");
//        this.museum = new PropertyAdmin();
//        this.museum.setName("museum");
//        this.year = new PropertyAdmin();
//        this.year.setName("year");
//        this.type = new PropertyAdmin();
//        this.type.setName("type");
        if (!this.getURI().contains("dbpedia")) {
            String req = String.format($PREFIXS
                    + " select ?description ?socnet ?owner ?type ?year "
                    + " (group_concat(?location; separator=\"&&&&\") as ?locations) "
                    + " (group_concat(?author;separator=\"&&&&\") as ?authors) "
                    + " (group_concat(?museum; separator=\"&&&&\") as ?museums) "
                    + " (group_concat(?same;separator=\"&&&&\") as ?sameas) where {"
                    + " values ?uri { <%s> }"
                    + " ?e axis-datamodel:uses ?uri ."
                    + " ?e a axis-datamodel:Entity ."
                    + " optional{"
                    + " ?uri axis-datamodel:hasRepresentation ?reg ."
                    + " ?reg a axis-datamodel:RegOfObjectItem ."
                    + "     optional{ ?reg axis-datamodel:takePlaceIn ?location .}"
                    + "     optional{ ?reg dbont:owner ?owner .}"
                    + "     optional{ ?reg dbp:museum ?museum . }"
                    + " }"
                    + " optional{"
                    + " ?uri axis-datamodel:hasRepresentation ?doc ."
                    + " ?doc a axis-datamodel:Document .  "
                    + "     optional{ ?doc rdf:Description ?description .}"
                    + "     optional{ ?doc axis-datamodel:socialNetwork ?socnet .}"
                    + "     optional{ ?doc dbp:year ?year . }"
                    + "     optional{ ?doc dbp:type ?type . }"
                    + " }"
                    + " optional{ ?uri owl:sameAs ?same .}"
                    + " optional{ ?uri axis-datamodel:isPerformedBy ?author .}"
                    + " } group by ?description ?socnet ?owner ?year ?type ", this.getURI());
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
//                if (rep.get("owner") != null) {
//                    this.owner.setValue_locale(rep.get("owner").asLiteral().getString());
//                    this.owner.setLang(rep.get("owner").asLiteral().getLanguage());
//                    this.owner.setType("string");
//                }
//                if (rep.get("year") != null) {
//                    this.year.setValue_locale(rep.get("year").asLiteral().getString());
//                    this.year.setLang(rep.get("year").asLiteral().getLanguage());
//                    this.year.setType("string");
//
//                }
//                if (rep.get("type") != null) {
//                    this.type.setValue_locale(rep.get("type").asLiteral().getString());
//                    this.type.setLang(rep.get("type").asLiteral().getLanguage());
//                    this.type.setType("string");
//
//                }
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
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfPlace"), "axis-datamodel:isAPlaceOfObject", this.getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfObjectItem"), "axis-datamodel:takePlaceIn", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfPlace"), "axis-datamodel:isAPlaceOfObject", this.getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfObjectItem"), "axis-datamodel:takePlaceIn", p.getValue(), p.getType());
                break;
        }
    }

    public void insertAuthor(Property p) {

        String uri1 = null;

        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfObjectItem"), "axis-datamodel:isPerformedBy", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfAgent"), "axis-datamodel:performs", this.getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfObjectItem"), "axis-datamodel:isPerformedBy", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfAgent"), "axis-datamodel:performs", this.getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfObjectItem"), "axis-datamodel:isPerformedBy", p.getValue(), p.getType());
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
