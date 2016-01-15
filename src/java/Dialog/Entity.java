/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

import static model.Connector.*;

public class Entity {

    String URI;
    String name;
    String image;
    String type;

    public static void main(String args[]) {

    }

    public Entity(String URI) {
	this.URI = URI;
	this.name = URI;
	this.image = URI;
	this.type = URI;
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

}
