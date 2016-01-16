/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import static model.Connector.*;


public class Entity {

    String URI;
    String name;
    String image;
    String type;

    public static void main(String args[]) {
        
        Entity e = new Entity();
        e.setURI("TESTURI456");
        
        Property p = new Property();
        p.setName("image");
        p.setValue("mon_image_test.jpg");
        
        e.insertImage(p);
        
    }

    public Entity(String URI, String name, String image, String type) {
        this.URI = URI;
        this.name = name;
        this.image = image;
        this.type = type;
    }
    public Entity() {
        
    }

    public String getURI() {
	return URI;
    }

    public void setURI(String URI) {
	this.URI = URI;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getImage() {
	return image;
    }

    public void setImage(String image) {
	this.image = image;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }


    public void constructEntity(String URI) {
        //construct de name
        //this.name = blabla;
        
        //construct de image
        //this.image = blabla
        
        //construct de type
        //this.type = blabla
    }
    
    public void insertName(Property p) {
        
    }
    
    public void insertImage(Property p) {
        
        String uid1 = insert("rdf:type", "axis:RegOfPhotoItem");
        String uid2 = insert("rdf:type", "axis:Location");
        String uid3 = insert("rdf:type", "axis:EmbodimentOfFile");
        
        insert("poc:"+this.URI, "rdf:uses", "poc:"+uid1);
        
        insert("poc:"+uid3, "axis:fileName", '"'+p.getValue()+'"');
        
        insert("poc:"+uid3, "axis:hasLocation", "poc:"+uid2);
        
        insert("poc:"+uid2, "axis:locates", "poc:"+uid3);
        
        insert("poc:"+uid1, "axis:hasExpression", "poc:"+uid2);
        
        // Entity => RegOfPhotoItem => Location <=> EmbodimentOfFile => p.value
        
        // on crée un "RegOfPhotoItem"
        // on le lie à "Location" via "hasExpression"
        // on le lie à "EmbodimentOfFile" via "locates"
        // on le lie à "notre_fichier_jpg" via "fileName"
        // et on lie "EmbodimentOfFile" à "Location" via "hasLocation"
        
    }
    
    public void insertType(Property p) {
        
    }
}
