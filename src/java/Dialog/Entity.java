/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

import static model.Connector.insert;
import static model.Connector.insertLitteral;

/**
 *
 * @author Mélanie
 */
public class Entity {
   
    public String URI;
    public String name;
    public String image;
    public String type;
    
    public static void main(String args[]) {

    }
    
    public Entity(String URI, String name, String image, String type) {
        this.URI = URI;
        this.name = name;
        this.image = image;
        this.type = type;
    }    
    
}
