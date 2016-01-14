/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Dialog.Entity;

/**
 *
 * @author loannguyen
 */
public class Person extends Entity {
    public String birthDate;
    public String deathDate;
    public String placeOfBirth;

    public Person(String URI) {
        super(URI);
        this.birthDate = URI;
        this.deathDate = URI;
        this.placeOfBirth = URI;
    }  
}
