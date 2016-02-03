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

    public Property[] getPropertiesPlace() {
        ArrayList<Property> list = new ArrayList<Property>();
//        entityBrowser(this.getURI()
        list.add(new Property(this.postalCode.getName(), this.postalCode.getValue_locale(), this.postalCode.getEntity_locale(), this.postalCode.getType(), this.postalCode.getLang()));
        list.add(new Property(this.region.getName(), this.region.getValue_locale(), this.region.getEntity_locale(), this.region.getType(), this.region.getLang()));
        list.add(new Property(this.description.getName(), this.description.getValue_locale(), this.description.getEntity_locale(), this.description.getType(), this.description.getLang()));
        list.add(new Property(this.country.getName(), this.country.getValue_locale(), this.country.getEntity_locale(), this.country.getType(), this.country.getLang()));
        list.add(new Property(this.birthPlaceOf.getName(), this.birthPlaceOf.getValue_locale(), this.birthPlaceOf.getEntity_locale(), this.birthPlaceOf.getType(), this.birthPlaceOf.getLang()));
        list.add(new Property(this.locationOf.getName(), this.locationOf.getValue_locale(), this.locationOf.getEntity_locale(), this.locationOf.getType(), this.locationOf.getLang()));

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
        this.sameAs.setName("region");
        
        if (!this.getURI().contains("dbpedia")) {
            String req = String.format(Connector.$PREFIXS
                    + "select ?description ?postalcode (group_concat(?country; separator=\"&&&&\") as ?countries) "
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
                    + "    optional{ ?reg axis-datamodel:takePlaceIn ?locof . }"
                    + "  }"
                    + "optional{"
                    + "	?uri axis-datamodel:hasRepresentation ?doc ."
                    + "    ?doc a axis-datamodel:Document . "
                    + "    optional{ ?doc rdf:Description ?description . }"
                    + "  }"
                    + "optional{ ?uri owl:sameAs ?same .}"
                    + "} group by ?description ?postalcode", this.getURI());
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
//                uri1 = insert("rdf:type", "axis-datamodel:Place");
//                insert(this.getURI(), "dbont:country", uri1);
//                insert(uri1, "owl:sameAs", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:country", p.getEnt()[0].getURI());
                break;

            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:Place");
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:country", uri1);
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }
    }

    public void insertRegion(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:region", p.getEnt()[0].getURI());
//                uri1 = insert("rdf:type", "axis-datamodel:Place");
//                insert(this.getURI(), "dbont:region", uri1);
//                insert(uri1, "owl:sameAs", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:region", p.getEnt()[0].getURI());
                break;

            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:Place");
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:region", uri1);
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }
    }

    public void insertLocationOf(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "axis-datamodel:takePlaceIn", p.getEnt()[0].getURI());
                insert(p.getEnt()[0].getURI(), "axis-datamodel:isAPlaceOfObject", this.getURI());
//                uri1 = insert("rdf:type", "axis-datamodel:PhysicalObject");
//                insert(this.getURI(), "axis-datamodel:isAPlaceOfObject", uri1);
//                insert(uri1, "axis-datamodel:takePlaceIn", this.getURI());
//                insert(uri1, "owl:sameAs", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "axis-datamodel:takePlaceIn", p.getEnt()[0].getURI());
                insert(p.getEnt()[0].getURI(), "axis-datamodel:isAPlaceOfObject", this.getURI());
                break;

            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:PhysicalObject");
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "axis-datamodel:isAPlaceOfObject", uri1);
                insert(uri1, "axis-datamodel:takePlaceIn", this.getURI());
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
                break;
        }
    }

    public void insertBirthPlaceOf(Property p) {
        String uri1 = null;
        switch (this.getTypeProperty(p)) {
            case "dbpedia":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:birthPlace", p.getEnt()[0].getURI());
//                uri1 = insert("rdf:type", "axis-datamodel:Person");
//                insert(this.getURI(), "dbont:birthPlace", uri1);
//                insert(uri1, "owl:sameAs", p.getEnt()[0].getURI());
                break;

            case "our":
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:birthPlace", p.getEnt()[0].getURI());
                break;

            case "literal":
                uri1 = insert("rdf:type", "axis-datamodel:Person");
                insert(selectRegOfEntity(this.getURI(), "RegOfPlace"), "dbont:birthPlace", uri1);
                insert(uri1, "rdfs:label", p.getValue(), p.getType());
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
