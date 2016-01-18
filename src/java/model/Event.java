/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Dialog.Entity;

/**
 *
 * @author Robois
 */
public class Event extends Entity{
    public String dateOfEvent;
    public String location;

    public Event(String URI) {
//        super(URI);
        this.dateOfEvent = URI;
        this.location = URI;
    }

}
