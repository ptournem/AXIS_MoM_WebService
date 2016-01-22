/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

public class Property {

    String name;
    String value;
    String type;
    Entity ent;

    public Property(String name, String value, String type, Entity ent) {
	this.name = name;
	this.value = value;
        this.type = type;
        this.ent = ent;
    }

    public Property() {
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

    public Entity getEnt() {
	return ent;
    }

    public void setEnt(Entity ent) {
	this.ent = ent;
    }

    @Override
    public String toString() {
        return "Property{\u001B[34mname=\u001B[0m" + name + ", \u001B[34mvalue=\u001B[0m" + value + ",\u001B[34mtype=\u001B[0m" + type + ", \u001B[34ment=\u001B[0m" + ent + '}';
    }

}
