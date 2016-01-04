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
public class Comment extends Entity{
   
    String robin;
    String AuthorName;
    String email;
    String comment;
    String test;

    public Comment(String AuthorName, String email, String comment, String URI, String name) {
        super(URI, name);
        this.AuthorName = AuthorName;
        this.email = email;
        this.comment = comment;
    }
   
}
