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
import static model.Connector.insert;
import static model.Connector.selectRegOfEntity;
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
public class Place extends Entity {

    public PropertyAdmin postalCode;
    public PropertyAdmin region;
    public PropertyAdmin country;
    public PropertyAdmin description;
    public PropertyAdmin birthPlaceOf;
    public PropertyAdmin locationOf;
    public PropertyAdmin sameAs;
    public PropertyAdmin socialNetwork;
    public PropertyAdmin isAPlaceOfEvent;
    public PropertyAdmin website;
    public PropertyAdmin deathPlaceOf;
    public PropertyAdmin isAPlaceOfOrganisation;

    public Property[] getPropertiesPlace() {
        ArrayList<Property> list = new ArrayList<Property>();
//        entityBrowser(this.getURI()
        if (!((this.postalCode.getEntity_locale() == null) && (this.postalCode.getValue_locale() == null))) {
            list.add(new Property(this.postalCode.getName(), this.postalCode.getValue_locale(), this.postalCode.getEntity_locale(), this.postalCode.getType(), this.postalCode.getLang()));
        }
        if (!((this.region.getEntity_locale() == null) && (this.region.getValue_locale() == null))) {
            list.add(new Property(this.region.getName(), this.region.getValue_locale(), this.region.getEntity_locale(), this.region.getType(), this.region.getLang()));
        }
        if (!((this.description.getEntity_locale() == null) && (this.description.getValue_locale() == null))) {
            list.add(new Property(this.description.getName(), this.description.getValue_locale(), this.description.getEntity_locale(), this.description.getType(), this.description.getLang()));
        }
        if (!((this.country.getEntity_locale() == null) && (this.country.getValue_locale() == null))) {
            list.add(new Property(this.country.getName(), this.country.getValue_locale(), this.country.getEntity_locale(), this.country.getType(), this.country.getLang()));
        }
        if (!((this.birthPlaceOf.getEntity_locale() == null) && (this.birthPlaceOf.getValue_locale() == null))) {
            list.add(new Property(this.birthPlaceOf.getName(), this.birthPlaceOf.getValue_locale(), this.birthPlaceOf.getEntity_locale(), this.birthPlaceOf.getType(), this.birthPlaceOf.getLang()));
        }
        if (!((this.locationOf.getEntity_locale() == null) && (this.locationOf.getValue_locale() == null))) {
            list.add(new Property(this.locationOf.getName(), this.locationOf.getValue_locale(), this.locationOf.getEntity_locale(), this.locationOf.getType(), this.locationOf.getLang()));
        }
        if (!((this.socialNetwork.getEntity_locale() == null) && (this.socialNetwork.getValue_locale() == null))) {
            list.add(new Property(this.socialNetwork.getName(), this.socialNetwork.getValue_locale(), this.socialNetwork.getEntity_locale(), this.socialNetwork.getType(),this.socialNetwork.getLang()));
        }
        if (!((this.deathPlaceOf.getEntity_locale() == null) && (this.deathPlaceOf.getValue_locale() == null))) {
            list.add(new Property(this.deathPlaceOf.getName(), this.deathPlaceOf.getValue_locale(), this.deathPlaceOf.getEntity_locale(), this.deathPlaceOf.getType(),this.deathPlaceOf.getLang()));
        }
        if (!((this.website.getEntity_locale() == null) && (this.website.getValue_locale() == null))) {
            list.add(new Property(this.website.getName(), this.website.getValue_locale(), this.website.getEntity_locale(), this.website.getType(),this.website.getLang()));
        }
        if (!((this.sameAs.getEntity_locale() == null) && (this.sameAs.getValue_locale() == null))) {
            list.add(new Property(this.sameAs.getName(), this.sameAs.getValue_locale(), this.sameAs.getEntity_locale(), this.sameAs.getType(),this.sameAs.getLang()));
        }
        if (!((this.isAPlaceOfOrganisation.getEntity_locale() == null) && (this.isAPlaceOfOrganisation.getValue_locale() == null))) {
            list.add(new Property(this.isAPlaceOfOrganisation.getName(), this.isAPlaceOfOrganisation.getValue_locale(), this.isAPlaceOfOrganisation.getEntity_locale(), this.isAPlaceOfOrganisation.getType(),this.isAPlaceOfOrganisation.getLang()));
        }

        Property[] ret = new Property[list.size()];
        return (Property[]) list.toArray(ret);
    }

    public PropertyAdmin[] getPropertiesAdminPlace() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();
        
        list.add(this.postalCode);
        list.add(this.region);
        list.add(this.country);
        list.add(this.description);
        list.add(this.birthPlaceOf);
        list.add(this.locationOf);
        list.add(this.deathPlaceOf);
        list.add(this.socialNetwork);
        list.add(this.website);
        list.add(this.sameAs);
        list.add(this.isAPlaceOfOrganisation);
        //list.add(this.isAPlaceOfEvent);
        
        PropertyAdmin[] ret = new PropertyAdmin[list.size()];
        return (PropertyAdmin[]) list.toArray(ret);
    }

    public void constructPlace(boolean getdbpedia) {
        this.birthPlaceOf = new PropertyAdmin();
        this.birthPlaceOf.setName("birthplaceof");
        this.country = new PropertyAdmin();
        this.country.setName("country");
        this.description = new PropertyAdmin();
        this.description.setName("description");
        this.locationOf = new PropertyAdmin();
        this.locationOf.setName("locationof");
        this.postalCode = new PropertyAdmin();
        this.postalCode.setName("postalcode");
        this.region = new PropertyAdmin();
        this.region.setName("region");
        this.sameAs = new PropertyAdmin();
        this.sameAs.setName("sameas");
        this.socialNetwork = new PropertyAdmin();
        this.socialNetwork.setName("socialnetwork");
        if (!this.getURI().contains("dbpedia")) {
            String req = String.format(Connector.$PREFIXS
                    + "select ?description ?postalcode ?socnet "
                    + " (group_concat(?country; separator=\"&&&&\") as ?countries) "
                    + " (group_concat(?region; separator=\"&&&&\") as ?regions) "
                    + " (group_concat(?birthpof; separator=\"&&&&\") as ?birthplaceof) "
                    + " (group_concat(?locof;separator=\"&&&&\") as ?locationof) "
                    + " (group_concat(?same;separator=\"&&&&\") as ?sameas) where {"
                    + " values ?uri { <%s> }"
                    + "  ?e axis-datamodel:uses ?uri ."
                    + " ?e a axis-datamodel:Entity ."
                    + "optional{ "
                    + "    ?uri axis-datamodel:hasRepresentation ?reg ."
                    + "    ?reg a axis-datamodel:RegOfPlace ."
                    + "    optional{ ?reg dbont:country ?country . }"
                    + "    optional{ ?reg dbont:region ?region . }"
                    + "    optional{ ?reg dbont:birthPlace ?birthpof . }"
                    + "    optional{ ?reg dbont:postalCode ?postalcode . }"
                    + "    optional{ ?reg axis-datamodel:isAPlaceOfObject ?locof . }"
                    + "  }"
                    + "optional{"
                    + "	?uri axis-datamodel:hasRepresentation ?doc ."
                    + "    ?doc a axis-datamodel:Document . "
                    + "    optional{ ?doc rdf:Description ?description . }"
                    + "    optional{ ?doc axis-datamodel:socialNetwork ?socnet . }"
                    + "  }"
                    + "optional{ ?uri owl:sameAs ?same .}"
                    + "} group by ?description ?postalcode ?socnet", this.getURI());
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
                if (rep.get("postalcode") != null) {
                    this.postalCode.setValue_locale(rep.get("postalcode").asLiteral().getString());
                    this.postalCode.setLang(rep.get("postalcode").asLiteral().getLanguage());
                    this.postalCode.setType("string");
                    
                }
                if (rep.get("socnet") != null) {
                    this.socialNetwork.setValue_locale(rep.get("socnet").asLiteral().getString());
                    this.socialNetwork.setLang(rep.get("socnet").asLiteral().getLanguage());
                    this.socialNetwork.setType("string");
                    
                }
                if (rep.get("countries") != null) {
                    Entity[] t = getEntityTab(rep.get("countries").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.country.setValue_locale(rep.get("countries").asLiteral().getString());
                        this.country.setType("string");
                        this.country.setLang(rep.get("countries").asLiteral().getLanguage());
                    } else {
                        this.country.setEntity_locale(t);
                        this.country.setType("uri");
                        this.country.setLang("fr");
                    }
                }
                if (rep.get("birthplaceof") != null) {
                    Entity[] t = getEntityTab(rep.get("birthplaceof").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.birthPlaceOf.setValue_locale(rep.get("birthplaceof").asLiteral().getString());
                        this.birthPlaceOf.setLang(rep.get("birthplaceof").asLiteral().getLanguage());
                        this.birthPlaceOf.setType("string");
                    } else {
                        this.birthPlaceOf.setEntity_locale(t);
                        this.birthPlaceOf.setType("uri");
                        this.birthPlaceOf.setLang("fr");
                    }
                }
                if (rep.get("sameas") != null) {
                    Entity[] t = getEntityTab(rep.get("sameas").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.sameAs.setValue_locale(rep.get("sameas").asLiteral().getString());
                        this.sameAs.setType("string");
                        this.sameAs.setLang(rep.get("sameas").asLiteral().getString());
                    } else {
                        this.sameAs.setEntity_locale(t);
                        this.sameAs.setType("uri");
                        this.sameAs.setLang("fr");
                    }
                }
                if (rep.get("locationof") != null) {
                    Entity[] t = getEntityTab(rep.get("locationof").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.locationOf.setValue_locale(rep.get("locationof").asLiteral().getString());
                        this.locationOf.setType("string");
                        this.locationOf.setLang(rep.get("locationof").asLiteral().getString());
                    } else {
                        this.locationOf.setEntity_locale(t);
                        this.locationOf.setType("uri");
                        this.locationOf.setLang("fr");
                    }
                }
                if (rep.get("regions") != null) {
                    Entity[] t = getEntityTab(rep.get("regions").asLiteral().getString().split("&&&&"));
                    if (t.length == 0) {
                        this.region.setValue_locale(rep.get("regions").asLiteral().getString());
                        this.region.setType("string");
                        this.region.setLang(rep.get("regions").asLiteral().getString());
                    } else {
                        this.region.setEntity_locale(t);
                        this.region.setType("uri");
                        this.region.setLang("fr");
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
                        case "birthplaceof":
                            this.birthPlaceOf.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.birthPlaceOf.setEntity_locale(n.getEnt());
                                this.birthPlaceOf.setValue_locale(n.getValue());
                            } else {
                                this.birthPlaceOf.setEntity_dbpedia(n.getEnt());
                                this.birthPlaceOf.setValue_dbpedia(n.getValue());
                            }
                            break;
                        case "country":
                            this.country.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.country.setEntity_locale(n.getEnt());
                                this.country.setValue_locale(n.getValue());
                            } else {
                                this.country.setEntity_dbpedia(n.getEnt());
                                this.country.setValue_dbpedia(n.getValue());
                            }
                            break;
                        case "locationof":
                            this.locationOf.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.locationOf.setEntity_locale(n.getEnt());
                                this.locationOf.setValue_locale(n.getValue());
                            } else {
                                this.locationOf.setEntity_dbpedia(n.getEnt());
                                this.locationOf.setValue_dbpedia(n.getValue());
                            }
                            break;
                        case "postalcode":
                            this.postalCode.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.postalCode.setEntity_locale(n.getEnt());
                                this.postalCode.setValue_locale(n.getValue());
                            } else {
                                this.postalCode.setEntity_dbpedia(n.getEnt());
                                this.postalCode.setValue_dbpedia(n.getValue());
                            }
                            break;
                        case "region":
                            this.region.setType(n.getType());
                            if (this.getURI().contains("dbpedia")) {
                                this.region.setEntity_locale(n.getEnt());
                                this.region.setValue_locale(n.getValue());
                            } else {
                                this.region.setEntity_dbpedia(n.getEnt());
                                this.region.setValue_dbpedia(n.getValue());
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

    public void insertCountry(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:country", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:country", p.getEnt()[0].getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:country", p.getValue(), p.getType());
                break;
        }
    }

    public void insertRegion(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:region", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:region", p.getEnt()[0].getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:region", p.getValue(), p.getType());
                break;
        }
    }

    public void insertLocationOf(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "axis-datamodel:isAPlaceOfObject", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "axis-datamodel:isAPlaceOfObject", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfObjectItem"), "axis-datamodel:takePlaceIn", this.getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "axis-datamodel:isAPlaceOfObject", p.getValue(), p.getType());
                break;
        }
    }

    public void insertIsAPlaceOfEvent(Property p) {
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "axis-datamodel:isAPlaceOfEvent", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "axis-datamodel:isAPlaceOfEvent", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfEvent"), "axis-datamodel:takesPlaceIn", this.getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "axis-datamodel:isAPlaceOfEvent", p.getValue(), p.getType());
                break;
        }
    }
    
    public void insertIsAPlaceOfOrganisation(Property p) {
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:location ", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:location ", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfMoralPerson"), "axis-datamodel:takesPlaceIn", this.getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:location ", p.getValue(), p.getType());
                break;
        }
    }
    
    public void insertBirthPlaceOf(Property p) {
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:birthPlace", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:birthPlace", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfPhysicalPerson"), "dbont:birthPlace", this.getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:birthPlace", p.getValue(), p.getType());
                break;
        }
    }

    public void insertDeathPlace(Property p) {
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:deathPlace", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:deathPlace", p.getEnt()[0].getURI());
                insert(selectRegOfEntity(p.getEnt()[0].getURI(), "RegOfPhysicalPerson"), "dbont:deathPlace", this.getURI());
                break;

            case "literal":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:deathPlace", p.getValue(), p.getType());
                break;
        }
    }
    
    public void insertPostalCode(Property p) {
        insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:postalCode", p.getValue(), p.getType());
    }

    @Override
    public String toString() {
        return "Place{" + "postalCode=" + postalCode + ", \nregion=" + region + ", \ncountry=" + country + ", \ndescription=" + description + ", \nbirthPlaceOf=" + birthPlaceOf + ", \nlocationOf=" + locationOf + ", \nsameAs=" + sameAs + '}';
    }

}
