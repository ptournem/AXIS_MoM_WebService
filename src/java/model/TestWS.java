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
public class TestWS {
    public static void main(String args[]) {
        test1();
    }
    
    public static void test1() {
        Entity e = new Entity();
        e.setImage("ig2i.jpg");
        e.setName("IG2I");
        e.setType("object");
        
        
        TestWebService ws = new TestWebService();
        Entity e2 = ws.AddEntity(e);
        
        System.out.println("-!- Entité ajoutée : "+e2);
        
        Entity[] maListe = ws.SearchOurEntitiesFromText("G2");
        System.out.println("-!- Résultat de la recherche pour 'G2' : ");
        for(int i =0; i<maListe.length; i++) {
            System.out.println(maListe[i]);
        }
    }
}
