/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

/**
 *
 * @author Mélanie
 */
public class PropertyText extends Property{
    
    String value;

    public PropertyText(String value, String propURI, String name) {
        super(propURI, name);
        this.value = value;
    }
    
    
}
