/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

public class Comment extends Entity {

    String AuthorName;
    String email;
    String comment;

    public Comment(String AuthorName, String email, String comment, String URI, String name) {
	super();
	this.setURI(URI);
	this.AuthorName = AuthorName;
	this.email = email;
	this.comment = comment;
    }

    public Comment() {
	super();
    }

    public String getAuthorName() {
	return AuthorName;
    }

    public void setAuthorName(String AuthorName) {
	this.AuthorName = AuthorName;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getComment() {
	return comment;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

}
