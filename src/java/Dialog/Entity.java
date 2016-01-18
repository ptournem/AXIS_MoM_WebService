/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import static model.Connector.*;
import model.TestWebService;
import model.jaxws.AddEntity;


public class Entity {

    String URI;
    String name;
    String image;
    String type;

    public static void main(String args[]) {
        
        
        Entity e = new Entity();
        e.setImage("ig2i.jpg");
        e.setName("IG2I");
        e.setType("object");
        
        
        TestWebService ws = new TestWebService();
        Entity e2 = ws.AddEntity(e);
        
        e2.constructEntity();
        
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


    public void constructEntity() {
        //construct de name
        //this.name = blabla;
        
        //construct de image
        //this.image = blabla
        
        //construct de type
        //this.type = blabla
    }
    
    public void insertName(Property p) {
        insert(this.URI, "label", p.name, "fr");
    }
    
    public void insertImage(Property p) {
        
        String uri1 = insert("rdf:type", "axis:RegOfPhotoItem");
        String uri2 = insert("rdf:type", "axis:Location");
        String uri3 = insert("rdf:type", "axis:EmbodimentOfFile");
        
        insert(this.URI, "rdf:uses", uri1);
        
        insert(uri3, "axis:fileName", '"'+p.getValue()+'"');
        
        insert(uri3, "axis:hasLocation", uri2);
        
        insert(uri2, "axis:locates", uri3);
        
        insert(uri1, "axis:hasExpression", uri2);
        
        // Entity => RegOfPhotoItem => Location <=> EmbodimentOfFile => p.value
        
        // on crée un "RegOfPhotoItem"
        // on le lie à "Location" via "hasExpression"
        // on le lie à "EmbodimentOfFile" via "locates"
        // on le lie à "notre_fichier_jpg" via "fileName"
        // et on lie "EmbodimentOfFile" à "Location" via "hasLocation"
        
    }
    
}
