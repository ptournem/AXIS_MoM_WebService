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
 * Classe pour les entités de type Organisation
 */
public class Organisation extends Entity {

    public PropertyAdmin dateOfCreation;
    public PropertyAdmin placeOfOrganisation;
    public PropertyAdmin description;
    public PropertyAdmin sameAs;
    public PropertyAdmin website;
    public PropertyAdmin hasObject;
    public PropertyAdmin socialNetwork;
    public PropertyAdmin leader;

    public Property[] getPropertiesOrganisation() {
        ArrayList<Property> list = new ArrayList<Property>();

        if (!((this.website.getEntity_locale() == null) && (this.website.getValue_locale() == null))) {
            list.add(new Property(this.website.getName(), this.website.getValue_locale(), this.website.getEntity_locale(), this.website.getType(), this.website.getLang()));
        }
        if (!((this.socialNetwork.getEntity_locale() == null) && (this.socialNetwork.getValue_locale() == null))) {
            list.add(new Property(this.socialNetwork.getName(), this.socialNetwork.getValue_locale(), this.socialNetwork.getEntity_locale(), this.socialNetwork.getType(), this.socialNetwork.getLang()));
        }
        if (!((this.description.getEntity_locale() == null) && (this.description.getValue_locale() == null))) {
            list.add(new Property(this.description.getName(), this.description.getValue_locale(), this.description.getEntity_locale(), this.description.getType(), this.description.getLang()));
        }
        if (!((this.placeOfOrganisation.getEntity_locale() == null) && (this.placeOfOrganisation.getValue_locale() == null))) {
            list.add(new Property(this.placeOfOrganisation.getName(), this.placeOfOrganisation.getValue_locale(), this.placeOfOrganisation.getEntity_locale(), this.placeOfOrganisation.getType(), this.placeOfOrganisation.getLang()));
        }
        if (!((this.dateOfCreation.getEntity_locale() == null) && (this.dateOfCreation.getValue_locale() == null))) {
            list.add(new Property(this.dateOfCreation.getName(), this.dateOfCreation.getValue_locale(), this.dateOfCreation.getEntity_locale(), this.dateOfCreation.getType(), this.dateOfCreation.getLang()));
        }
        if (!((this.hasObject.getEntity_locale() == null) && (this.hasObject.getValue_locale() == null))) {
            list.add(new Property(this.hasObject.getName(), this.hasObject.getValue_locale(), this.hasObject.getEntity_locale(), this.hasObject.getType(), this.hasObject.getLang()));
        }
        if (!((this.leader.getEntity_locale() == null) && (this.leader.getValue_locale() == null))) {
            list.add(new Property(this.leader.getName(), this.leader.getValue_locale(), this.leader.getEntity_locale(), this.leader.getType(), this.leader.getLang()));
        }

        Property[] ret = new Property[list.size()];
        return (Property[]) list.toArray(ret);
    }

    public PropertyAdmin[] getPropertiesAdminOrganisation() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();

        this.dateOfCreation.setType("date");
        
        //list.add(this.typeOfOrganisation);
        list.add(this.placeOfOrganisation);
        list.add(this.dateOfCreation);
        list.add(this.description);
        list.add(this.website);
        list.add(this.socialNetwork);
        list.add(this.sameAs);
        list.add(this.hasObject);
        list.add(this.leader);

        PropertyAdmin[] ret = new PropertyAdmin[list.size()];
        return (PropertyAdmin[]) list.toArray(ret);
    }

    public void insertMuseum(Property p) {
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfMoralPerson"), "dbp:museum", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfMoralPerson"), "dbp:museum", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfObjectItem"), "dbp:museum", this.getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfMoralPerson"), "dbp:museum", p.getValue(), p.getType());
                break;
        }
    }

    public void insertLeader(Property p) {
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfMoralPerson"), "dbont:leaderName", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfMoralPerson"), "dbont:leaderName", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfPhysicalPerson"), "dbont:leaderName", this.getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfMoralPerson"), "dbont:leaderName", p.getValue(), p.getType());
                break;
        }
    }

    public void insertDateOfCreation(Property p) {
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfMoralPerson"), "dbp:established", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfMoralPerson"), "dbp:established", p.getEnt()[0].getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfMoralPerson"), "dbp:established", p.getValue(), p.getType());
                break;
        }
    }

    public void insertPlaceOfOrganisation(Property p) {
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfMoralPerson"), "axis-datamodel:takesPlaceIn", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfMoralPerson"), "axis-datamodel:takesPlaceIn", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfPlace"), "dbont:location ", this.getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfMoralPerson"), "axis-datamodel:takesPlaceIn", p.getValue(), p.getType());
                break;
        }
    }

    public void constructOrganisation(boolean getdbpedia) {
        this.description = new PropertyAdmin();
        this.description.setName("description");
        this.sameAs = new PropertyAdmin();
        this.sameAs.setName("sameas");
        this.socialNetwork = new PropertyAdmin();
        this.socialNetwork.setName("socialnetwork");
        this.dateOfCreation = new PropertyAdmin();
        this.dateOfCreation.setName("dateofcreation");
        this.hasObject = new PropertyAdmin();
        this.hasObject.setName("hasobject");
        this.placeOfOrganisation = new PropertyAdmin();
        this.placeOfOrganisation.setName("placeoforganisation");
        this.website = new PropertyAdmin();
        this.website.setName("website");
        this.leader = new PropertyAdmin();
        this.leader.setName("leader");
        if (!this.getURI().contains("dbpedia")) {
            String req = String.format($PREFIXS
                    + " select ?description ?date ?socnet ?website "
                    + " (group_concat(?leader;separator=\"&&&&\") as ?leaders)"
                    + " (group_concat(?place;separator=\"&&&&\") as ?places)"
                    + " (group_concat(?hasobject;separator=\"&&&&\") as ?hasobjects)"
                    + " (group_concat(?same;separator=\"&&&&\") as ?sameas) where {"
                    + " values ?uri { <%s> }"
                    + " ?e axis-datamodel:uses ?uri ."
                    + " ?e a axis-datamodel:Entity ."
                    + " optional{"
                    + " ?uri axis-datamodel:hasRepresentation ?reg ."
                    + " ?reg a axis-datamodel:RegOfMoralPerson ."
                    + " optional{ ?reg dbp:museum ?hasobject .}"
                    + " optional{ ?reg dbont:leaderName ?leader .}"
                    + " optional{ ?reg dbp:established ?date .}"
                    + " optional{ ?reg axis-datamodel:takesPlaceIn ?place .}"
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
                    this.description.setValue_locale(rep.get("description").asLiteral().getString().split("&&&&")[0]);
                    this.description.setLang(rep.get("description").asLiteral().getLanguage());
                    this.description.setType("string");
                }
                if (rep.get("website") != null) {
                    this.website.setValue_locale(rep.get("website").asLiteral().getString().split("&&&&")[0]);
                    this.website.setLang(rep.get("website").asLiteral().getLanguage());
                    this.website.setType("string");
                }
                if (rep.get("socnet") != null) {
                    this.socialNetwork.setValue_locale(rep.get("socnet").asLiteral().getString().split("&&&&")[0]);
                    this.socialNetwork.setLang(rep.get("socnet").asLiteral().getLanguage());
                    this.socialNetwork.setType("string");
                }
                if (rep.get("date") != null) {
                    this.dateOfCreation.setValue_locale(rep.get("date").asLiteral().getString().split("&&&&")[0]);
                    this.dateOfCreation.setLang(rep.get("date").asLiteral().getLanguage());
                    this.dateOfCreation.setType("string");
                }
                if (rep.get("places") != null) {
                    Entity[] t = getEntityTab(rep.get("places").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.placeOfOrganisation.setValue_locale(rep.get("places").asLiteral().getString().split("&&&&")[0]);
                        this.placeOfOrganisation.setType("string");
                        this.placeOfOrganisation.setLang(rep.get("places").asLiteral().getLanguage());
                    } else {
                        this.placeOfOrganisation.setEntity_locale(t);
                        this.placeOfOrganisation.setType("uri");
                        this.placeOfOrganisation.setLang("fr");
                    }
                }
                if (rep.get("leaders") != null) {
                    Entity[] t = getEntityTab(rep.get("leaders").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.leader.setValue_locale(rep.get("leaders").asLiteral().getString().split("&&&&")[0]);
                        this.leader.setType("string");
                        this.leader.setLang(rep.get("leaders").asLiteral().getLanguage());
                    } else {
                        this.leader.setEntity_locale(t);
                        this.leader.setType("uri");
                        this.leader.setLang("fr");
                    }
                }
                if (rep.get("sameas") != null) {
                    Entity[] t = getEntityTab(rep.get("sameas").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.sameAs.setValue_locale(rep.get("sameas").asLiteral().getString().split("&&&&")[0]);
                        this.sameAs.setType("string");
                        this.sameAs.setLang(rep.get("sameas").asLiteral().getLanguage());
                    } else {
                        this.sameAs.setEntity_locale(t);
                        this.sameAs.setType("uri");
                        this.sameAs.setLang("fr");
                    }
                }
                if (rep.get("hasobjects") != null) {
                    Entity[] t = getEntityTab(rep.get("hasobjects").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.hasObject.setValue_locale(rep.get("hasobjects").asLiteral().getString().split("&&&&")[0]);
                        this.hasObject.setType("string");
                        this.hasObject.setLang(rep.get("hasobjects").asLiteral().getLanguage());
                    } else {
                        this.hasObject.setEntity_locale(t);
                        this.hasObject.setType("uri");
                        this.hasObject.setLang("fr");
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
                        case "hasobject":
                            this.hasObject.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.hasObject.setEntity_locale(n.getEnt());
                                this.hasObject.setValue_locale(n.getValue());
                            } else {
                                this.hasObject.setEntity_dbpedia(n.getEnt());
                                this.hasObject.setValue_dbpedia(n.getValue());
                            }
                            break;
                        case "placeoforganisation":
                            this.placeOfOrganisation.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.placeOfOrganisation.setEntity_locale(n.getEnt());
                                this.placeOfOrganisation.setValue_locale(n.getValue());
                            } else {
                                this.placeOfOrganisation.setEntity_dbpedia(n.getEnt());
                                this.placeOfOrganisation.setValue_dbpedia(n.getValue());
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
                        case "dateofcreation":
                            this.dateOfCreation.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.dateOfCreation.setEntity_locale(n.getEnt());
                                this.dateOfCreation.setValue_locale(n.getValue());
                            } else {
                                this.dateOfCreation.setEntity_dbpedia(n.getEnt());
                                this.dateOfCreation.setValue_dbpedia(n.getValue());
                            }
                            break;
                        case "leader":
                            this.leader.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.leader.setEntity_locale(n.getEnt());
                                this.leader.setValue_locale(n.getValue());
                            } else {
                                this.leader.setEntity_dbpedia(n.getEnt());
                                this.leader.setValue_dbpedia(n.getValue());
                            }
                            break;
                    }
                }
            }
        }
    }
}
