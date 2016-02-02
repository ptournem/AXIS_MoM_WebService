/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static model.Connector.*;

public class Comment {

    String id;
    String authorName;
    String email;
    String comment;
    boolean validated;
    String createDt;
    Entity entity;

    public Comment(String id, String authorName, String email, String comment, boolean validated, String createDt, Entity entity) {
        this.id = id;
	this.authorName = authorName;
	this.email = email;
	this.comment = comment;
        this.validated = validated;
        this.createDt = createDt;
        this.entity = entity;
    }
    
    public Comment() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    
    public Comment insertComment() {
        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dat = dateFormat.format(actuelle);
        
        String uri = insert("rdf:type", "axis-datamodel:Comment");
        
        insert(selectRegOfEntity(this.getEntity().getURI(), "RegOfInformationItem"), "axis-datamodel:hasComment", uri);
        insert(uri, "axis-datamodel:isCommentOf", selectRegOfEntity(this.getEntity().getURI(), "RegOfInformationItem"));
        
        insert(uri, "axis-datamodel:creator", this.authorName, "fr");
        insert(uri, "axis-datamodel:content", this.comment, "fr");
        
        insert(uri, "axis-datamodel:creationDate", dat, "fr");
        insert(uri, "axis-datamodel:email", this.email, "fr");
        insert(uri, "axis-datamodel:id", uri, "fr");
        insert(uri, "axis-datamodel:validate", "false", "fr");
        
        this.setId(uri);
        
        return this;
    }
    
    public boolean deleteComment() {
        String uriC = "<"+this.getId()+">";
        deleteLinkEntity(selectRegOfEntity(this.getEntity().getURI(), "RegOfInformationItem"), "axis-datamodel:hasComment", uriC);
        return true;
    }
    
    public boolean changeValided(boolean b) {
        deleteLinkEntity(this.id, "axis-datamodel:validate", "?o");
        insert(this.id, "axis-datamodel:validate", Boolean.toString(b), "fr");
        return true;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", authorName=" + authorName + ", email=" + email + ", comment=" + comment + ", validated=" + validated + ", createDt=" + createDt + ", entity=" + entity + '}';
    }
}
