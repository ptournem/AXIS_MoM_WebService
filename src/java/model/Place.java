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

/**
 *
 * @author APP-Riad.Belmahi
 */
public class Place extends Entity{
public PropertyAdmin postalCode;
public PropertyAdmin region;
public PropertyAdmin country;
public PropertyAdmin description;
public PropertyAdmin birthPlaceOf;
public PropertyAdmin locationOf;

 public Property[] getPropertiesOrganisation() {
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
   public void constructPerson() {
        this.birthPlaceOf = getPlacePropertyAdmin("birthPlaceOf");
        this.country = getPlacePropertyAdmin("country");
        this.description = getPlacePropertyAdmin("description");
        
        this.locationOf = getPlacePropertyAdmin("locationOf");
        this.postalCode = getPlacePropertyAdmin("postalCode");
        this.region = getPlacePropertyAdmin("region");
        
        ArrayList<Property> p = getPropertiesMapFromLod(this.getURI());
        if(p != null){
        Iterator<Property> it = p.iterator();
        while(it.hasNext()){
            Property n = it.next();
            System.out.println("n:"+n.getName());
            switch (n.getName()) {
                case "birthPlaceOf":
                    this.birthPlaceOf.setEntity_dbpedia(n.getEnt());
                    this.birthPlaceOf.setValue_dbpedia(n.getValue());
                    break;
                case "country":
                    this.country.setEntity_dbpedia(n.getEnt());
                    this.country.setValue_dbpedia(n.getValue());
                    break;
                case "locationOf":
                    this.locationOf.setEntity_dbpedia(n.getEnt());
                    this.locationOf.setValue_dbpedia(n.getValue());
                    break;
                case "postalCode":
                    this.postalCode.setEntity_dbpedia(n.getEnt());
                    this.postalCode.setValue_dbpedia(n.getValue());
                    break;
                case "region":
                    this.region.setEntity_dbpedia(n.getEnt());
                    this.region.setValue_dbpedia(n.getValue());
                    break;
            }
            
        }}
    }
   
   public PropertyAdmin getPlacePropertyAdmin(String propertyName){
        PropertyAdmin pa = new PropertyAdmin();
        switch (propertyName) {
            case "country":
                pa = getPropertyAdmin("country", "entity");
                pa.setName(propertyName);
                break;
            case "region":
                pa = getPropertyAdmin("takePlaceIn", "entity");
                pa.setName(propertyName);
                break;
            case "description":
                pa = getPropertyAdmin("Description", "literal");
                pa.setName(propertyName);
                break;
            case "locationOf":
                pa = getPropertyAdmin("locationOf", "entity");
                pa.setName(propertyName);
                break;
            case "birthPlaceOf":
                pa = getPropertyAdmin("birthPlaceOf", "entity");
                pa.setName(propertyName);
                break;
            case "postalCode":
                pa = getPropertyAdmin("postalCode", "literal");
                pa.setName(propertyName);
                break;
        }
        return pa;
    }
   
   public PropertyAdmin[] getPropertiesAdminOrganisation() {
        ArrayList<PropertyAdmin> list = new ArrayList<PropertyAdmin>();

	list.add(this.postalCode);
        list.add(this.region);
        list.add(this.country);
        list.add(this.birthPlaceOf);
        list.add(this.locationOf);
        list.add(this.description);
	
	PropertyAdmin[] ret = new PropertyAdmin[list.size()];
	return (PropertyAdmin[]) list.toArray(ret);
    }
}
