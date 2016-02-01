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
        list.add(new Property(this.postalCode.getName(), this.postalCode.getValue_locale(), this.postalCode.getType(), this.postalCode.getEntity_locale()));
        list.add(new Property(this.region.getName(), this.region.getValue_locale(), this.region.getType(), this.region.getEntity_locale()));
        list.add(new Property(this.description.getName(), this.description.getValue_locale(), this.description.getType(), this.description.getEntity_locale()));
        list.add(new Property(this.country.getName(), this.country.getValue_locale(), this.country.getType(), this.country.getEntity_locale()));
        list.add(new Property(this.birthPlaceOf.getName(), this.birthPlaceOf.getValue_locale(), this.birthPlaceOf.getType(), this.birthPlaceOf.getEntity_locale()));
        list.add(new Property(this.locationOf.getName(), this.locationOf.getValue_locale(), this.locationOf.getType(), this.locationOf.getEntity_locale()));

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
        if (!this.getURI().contains("dbpedia")) {
//            this.birthPlaceOf = getPlacePropertyAdmin("birthplaceof");
//            this.country = getPlacePropertyAdmin("country");
//            this.description = getPlacePropertyAdmin("description");
//
//            this.locationOf = getPlacePropertyAdmin("locationof");
//            this.postalCode = getPlacePropertyAdmin("postalcode");
//            this.region = getPlacePropertyAdmin("region");
            this.birthPlaceOf = getPropertyAdmin("birthplaceof", "dbont:birthPlace");
            this.country = getPropertyAdmin("country", "dbont:country");
            this.description = getPropertyAdmin("description", "rdf:Description");
            this.locationOf = getPropertyAdmin("locationof", "axis-datamodel:isAPlaceOfObject");
            this.postalCode = getPropertyAdmin("postalcode", "dbont:postalCode");
            this.region = getPropertyAdmin("region", "dbont:region");
            this.sameAs = getPropertyAdmin("sameas", "owl:sameAs");
        }else{
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

//    public PropertyAdmin getPlacePropertyAdmin(String propertyName) {
//        PropertyAdmin pa = new PropertyAdmin();
//        switch (propertyName) {
//            case "country":
//                pa = getPropertyAdmin("country", "entity");
//                pa.setName(propertyName);
//                break;
//            case "region":
//                pa = getPropertyAdmin("region", "entity");
//                pa.setName(propertyName);
//                break;
//            case "description":
//                pa = getPropertyAdmin("Description", "literal");
//                pa.setName(propertyName);
//                break;
//            case "locationof":
//                pa = getPropertyAdmin("isAPlaceOfObject", "entity");
//                pa.setName(propertyName);
//                break;
//            case "birthplaceof":
//                pa = getPropertyAdmin("birthPlace", "entity");
//                pa.setName(propertyName);
//                break;
//            case "postalcode":
//                pa = getPropertyAdmin("postalCode", "literal");
//                pa.setName(propertyName);
//                break;
//        }
//        return pa;
//    }

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
