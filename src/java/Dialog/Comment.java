/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

import static model.Connector.insert;
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
        String uri = insert("rdf:type", "axis-datamodel:Comments");
        
        insert(this.getEntity().getURI(), "axis-datamodel:hasRepresentation", uri);
        
        insert(uri, "author", this.authorName, "fr");
        insert(uri, "comment", this.comment, "fr");
        insert(uri, "createDt", this.createDt, "fr");
        insert(uri, "email", this.email, "fr");
        insert(uri, "id", this.id, "fr");
        insert(uri, "validated", "false", "fr");
        
        return this;
    }
    
    public boolean changeValided(boolean b) {
        deleteLinkEntity(this.id, "validated", "?o");
        insert(this.id, "validated", Boolean.toString(b), "fr");
        return true;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", authorName=" + authorName + ", email=" + email + ", comment=" + comment + ", validated=" + validated + ", createDt=" + createDt + ", entity=" + entity + '}';
    }
    
    

}
