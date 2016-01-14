/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import Dialog.Entity;
import org.apache.jena.rdf.model.*;

/**
 *
 * @author APP-Riad.Belmahi
 */

public class Oeuvre extends Entity {

    public String dateCreation;
    public String location;
    public String author;

    public Oeuvre(String URI) {
        super(URI);
        this.dateCreation = URI;
        this.location = URI;
        this.author = URI;
    }



}
