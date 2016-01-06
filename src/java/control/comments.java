/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Dialog.Comment;
import java.net.URI;

/**
 *
 * @author loannguyen
 */
public class comments {
    
    public URI AddComment(URI targetEntity, String name, String email,  String comment) {
        
        // on fait un insert semantic du commentaire à l'aide du connector
        return null;
    }

    public Boolean GrantComment(URI comment) {
        // valider un commentaire (ajouter champs "valide" ou "non valide" dans l'ontologie)
        return null;
    }

    public Boolean RemoveComment(URI comment) {
        // supprimer un commentaire
        return null;
    }

    public Comment[] LoadComments(URI entity) {
        // renvoie tout les commentaires attachée à une URI
        return null;
    }
    
}
