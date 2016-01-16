/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

public class Property {

    String URI;
    String name;
    String value;
    String type;
    Entity e;

    public Property(String propURI, String name) {
	this.URI = propURI;
	this.name = name;
    }

    public Property() {
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

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public Entity getE() {
	return e;
    }

    public void setE(Entity e) {
	this.e = e;
    }

}
