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
import static model.Connector.insert;
import static model.Connector.selectRegOfEntity;

/**
 *
 * @author APP-Riad.Belmahi
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
            list.add(new Property(this.website.getName(), this.website.getValue_locale(), this.website.getEntity_locale(), this.website.getType(),this.website.getLang()));
        }
        if (!((this.socialNetwork.getEntity_locale() == null) && (this.socialNetwork.getValue_locale() == null))) {
            list.add(new Property(this.socialNetwork.getName(), this.socialNetwork.getValue_locale(), this.socialNetwork.getEntity_locale(), this.socialNetwork.getType(),this.socialNetwork.getLang()));
        }
        if (!((this.sameAs.getEntity_locale() == null) && (this.sameAs.getValue_locale() == null))) {
            list.add(new Property(this.sameAs.getName(), this.sameAs.getValue_locale(), this.sameAs.getEntity_locale(), this.sameAs.getType(),this.sameAs.getLang()));
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
}
