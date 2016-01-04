/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import org.apache.jena.rdf.model.*;

/**
 *
 * @author APP-Riad.Belmahi
 */

public class Oeuvre extends Object {
    
 // quelques définitions
static String oeuvreURI	= "http://somewhere/sacreNapoleon";
static String fullName 	= "sacreDeNapoleon";
 

 
// créer un modèle vide
Model model = ModelFactory.createDefaultModel();
 
// créer la ressource
Resource sacreDeNapoleon = model.createResource(oeuvreURI);
 
// ajouter la propriété



}
