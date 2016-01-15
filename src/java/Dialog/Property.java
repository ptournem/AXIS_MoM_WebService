/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

public class Property {

    String propURI;
    String name;

    public Property(String propURI, String name) {
	this.propURI = propURI;
	this.name = name;
    }

    public Property() {
    }

    public String getPropURI() {
	return propURI;
    }

    public void setPropURI(String propURI) {
	this.propURI = propURI;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

}
