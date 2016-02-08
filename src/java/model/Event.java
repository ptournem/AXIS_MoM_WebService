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
import static model.Connector.$PREFIXS;
import static model.Connector.insert;
import static model.Connector.selectRegOfEntity;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

/**
 * Classe pour les entités de type Event
 */
public class Event extends Entity {

    public PropertyAdmin dateOfEvent;
    public PropertyAdmin placeOfEvent;
    public PropertyAdmin description;
    public PropertyAdmin sameAs;
    public PropertyAdmin socialNetwork;
    public PropertyAdmin hasParticipant;
    public PropertyAdmin website;

    public Property[] getPropertiesEvent() {
        ArrayList<Property> list = new ArrayList<Property>();

        if (!((this.dateOfEvent.getEntity_locale() == null) && (this.dateOfEvent.getValue_locale() == null))) {
            list.add(new Property(this.dateOfEvent.getName(), this.dateOfEvent.getValue_locale(), this.dateOfEvent.getEntity_locale(), this.dateOfEvent.getType(), this.dateOfEvent.getLang()));
        }
        if (!((this.placeOfEvent.getEntity_locale() == null) && (this.placeOfEvent.getValue_locale() == null))) {
            list.add(new Property(this.placeOfEvent.getName(), this.placeOfEvent.getValue_locale(), this.placeOfEvent.getEntity_locale(), this.placeOfEvent.getType(), this.placeOfEvent.getLang()));
        }
        if (!((this.hasParticipant.getEntity_locale() == null) && (this.hasParticipant.getValue_locale() == null))) {
            list.add(new Property(this.hasParticipant.getName(), this.hasParticipant.getValue_locale(), this.hasParticipant.getEntity_locale(), this.hasParticipant.getType(), this.hasParticipant.getLang()));
        }
        if (!((this.description.getEntity_locale() == null) && (this.description.getValue_locale() == null))) {
            list.add(new Property(this.description.getName(), this.description.getValue_locale(), this.description.getEntity_locale(), this.description.getType(), this.description.getLang()));
        }

        if (!((this.socialNetwork.getEntity_locale() == null) && (this.socialNetwork.getValue_locale() == null))) {
            list.add(new Property(this.socialNetwork.getName(), this.socialNetwork.getValue_locale(), this.socialNetwork.getEntity_locale(), this.socialNetwork.getType(), this.socialNetwork.getLang()));
        }
        Property[] ret = new Property[list.size()];
        return (Property[]) list.toArray(ret);
    }

    public PropertyAdmin[] getPropertiesAdminEvent() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();
        list.add(this.dateOfEvent);
        list.add(this.placeOfEvent);
        list.add(this.description);
        list.add(this.hasParticipant);
        list.add(this.sameAs);
        list.add(this.socialNetwork);

        PropertyAdmin[] ret = new PropertyAdmin[list.size()];
        return (PropertyAdmin[]) list.toArray(ret);
    }

    public void constructEvent(boolean getdbpedia) {
        this.description = new PropertyAdmin();
        this.description.setName("description");
        this.sameAs = new PropertyAdmin();
        this.sameAs.setName("sameas");
        this.socialNetwork = new PropertyAdmin();
        this.socialNetwork.setName("socialnetwork");
        this.dateOfEvent = new PropertyAdmin();
        this.dateOfEvent.setName("dateofevent");
        this.hasParticipant = new PropertyAdmin();
        this.hasParticipant.setName("hasparticipant");
        this.placeOfEvent = new PropertyAdmin();
        this.placeOfEvent.setName("placeofevent");
        this.website = new PropertyAdmin();
        this.website.setName("website");
        if (!this.getURI().contains("dbpedia")) {
            String req = String.format($PREFIXS
                    + " select ?description ?date ?socnet ?website"
                    + " (group_concat(?participant ; separator=\"&&&&\") as ?participants)"
                    + " (group_concat(?placeofevent;separator=\"&&&&\") as ?placesofevent)"
                    + " (group_concat(?same;separator=\"&&&&\") as ?sameas) where {"
                    + " values ?uri { <%s> }"
                    + " ?e axis-datamodel:uses ?uri ."
                    + " ?e a axis-datamodel:Entity ."
                    + " optional{"
                    + " ?uri axis-datamodel:hasRepresentation ?reg ."
                    + " ?reg a axis-datamodel:RegOfEvent ."
                    + " optional{ ?reg axis-datamodel:takesPlaceIn ?placeofevent .}"
                    + " optional{ ?reg axis-datamodel:hasParticipant ?participant .}"
                    + " optional{ ?reg dbont:date ?date .}"
                    + " }"
                    + " optional{"
                    + " ?uri axis-datamodel:hasRepresentation ?doc ."
                    + " ?doc a axis-datamodel:Document ."
                    + " optional{ ?doc rdf:Description ?description .}"
                    + " optional{ ?doc axis-datamodel:socialNetwork ?socnet .}"
                    + " optional{ ?doc dbont:wikiPageExternalLink ?website . }"
                    + " }"
                    + " optional{ ?uri owl:sameAs ?same .}"
                    + " } group by ?description ?date ?socnet ?website", this.getURI());
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
                if (rep.get("website") != null) {
                    this.website.setValue_locale(rep.get("website").asLiteral().getString());
                    this.website.setLang(rep.get("website").asLiteral().getLanguage());
                    this.website.setType("string");
                }
                if (rep.get("socnet") != null) {
                    this.socialNetwork.setValue_locale(rep.get("socnet").asLiteral().getString());
                    this.socialNetwork.setLang(rep.get("socnet").asLiteral().getLanguage());
                    this.socialNetwork.setType("string");
                }
                if (rep.get("date") != null) {
                    this.dateOfEvent.setValue_locale(rep.get("date").asLiteral().getString());
                    this.dateOfEvent.setLang(rep.get("date").asLiteral().getLanguage());
                    this.dateOfEvent.setType("string");
                }
                if (rep.get("placesofevent") != null) {
                    Entity[] t = getEntityTab(rep.get("placesofevent").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.placeOfEvent.setValue_locale(rep.get("placesofevent").asLiteral().getString());
                        this.placeOfEvent.setType("string");
                        this.placeOfEvent.setLang(rep.get("placesofevent").asLiteral().getLanguage());
                    } else {
                        this.placeOfEvent.setEntity_locale(t);
                        this.placeOfEvent.setType("uri");
                        this.placeOfEvent.setLang("fr");
                    }
                }
                if (rep.get("participants") != null) {
                    Entity[] t = getEntityTab(rep.get("participants").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.hasParticipant.setValue_locale(rep.get("participants").asLiteral().getString());
                        this.hasParticipant.setType("string");
                        this.hasParticipant.setLang(rep.get("participants").asLiteral().getLanguage());
                    } else {
                        this.hasParticipant.setEntity_locale(t);
                        this.hasParticipant.setType("uri");
                        this.hasParticipant.setLang("fr");
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
                        case "hasparticipant":
                            this.hasParticipant.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.hasParticipant.setEntity_locale(n.getEnt());
                                this.hasParticipant.setValue_locale(n.getValue());
                            } else {
                                this.hasParticipant.setEntity_dbpedia(n.getEnt());
                                this.hasParticipant.setValue_dbpedia(n.getValue());
                            }
                            break;
                        case "placeofevent":
                            this.placeOfEvent.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.placeOfEvent.setEntity_locale(n.getEnt());
                                this.placeOfEvent.setValue_locale(n.getValue());
                            } else {
                                this.placeOfEvent.setEntity_dbpedia(n.getEnt());
                                this.placeOfEvent.setValue_dbpedia(n.getValue());
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
                        case "dateofevent":
                            this.dateOfEvent.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.dateOfEvent.setEntity_locale(n.getEnt());
                                this.dateOfEvent.setValue_locale(n.getValue());
                            } else {
                                this.dateOfEvent.setEntity_dbpedia(n.getEnt());
                                this.dateOfEvent.setValue_dbpedia(n.getValue());
                            }
                            break;
                    }
                }
            }
        }
    }

    public void insertDateOfEvent(Property p) {
        insert(selectRegOfEntity(this.getURI(), "RegOfEvent"), "dbont:date", p.getValue(), p.getType());
    }

    public void insertPlaceOfEvent(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfEvent"), "axis-datamodel:takesPlaceIn", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfEvent"), "axis-datamodel:takesPlaceIn", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfPlace"), "axis-datamodel:isAPlaceOfEvent", this.getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfEvent"), "axis-datamodel:takesPlaceIn", p.getValue(), p.getType());
                break;
        }
    }

    public void insertHasParticipant(Property p) {
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfEvent"), "axis-datamodel:hasParticipant", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfEvent"), "axis-datamodel:hasParticipant", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfPhysicalPerson"), "axis-datamodel:participatesInEvent", this.getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfEvent"), "axis-datamodel:hasParticipant", p.getValue(), p.getType());
                break;
        }
    }
}
