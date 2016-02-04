/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ws;

import Dialog.Comment;
import Dialog.Entity;
import Dialog.Property;
import Dialog.PropertyAdmin;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author APP-Riad.Belmahi
 */
public class AXIS_MoM_WSTest {
    
    public AXIS_MoM_WSTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of AddEntity method, of class AXIS_MoM_WS.
     */
    @Test (expected = NullPointerException.class)
    public void testAddEntity() {
        System.out.println("AddEntity");
        String uri = "JUNITtestURI";
        String name ="JUNITtestName";
        String image ="JUNITtestImage";
        String type ="JUNITtestType";
        
        Entity e = new Entity(uri,name,image,type);
        
        AXIS_MoM_WS instance = new AXIS_MoM_WS();
        Entity expResult = e;
        Entity result = instance.AddEntity(e);
        assertEquals(expResult, result);
       // fail("testAddEntity IS FAILED");
    }
    
     @Test (expected = NullPointerException.class)
       public void testAddEntity2() {
        System.out.println("AddEntity");
      //  String uri = "<JUNITtestRiadEntityURI>";
        String name ="Maroc";
        String image ="imgmaroc.jpeg";
        String type ="location";
        
        Entity e = new Entity(name,image,type);
        
        AXIS_MoM_WS instance = new AXIS_MoM_WS();
        Entity expResult = e;
        Entity result = instance.AddEntity(e);
        assertEquals(expResult, result);
       // fail("testAddEntity IS FAILED");
    }

       @Test (expected = NullPointerException.class)
       public void testAddEntity3() {
        System.out.println("AddEntity");
       // String uri = "JUNITtestURI";
        String name ="JUNITtestName";
        String image ="JUNITtestImage.jpg";
        String type ="person";
        
        Entity e = new Entity(name,image,type);
        
        AXIS_MoM_WS instance = new AXIS_MoM_WS();
        Entity expResult = e;
        Entity result = instance.AddEntity(e);
        assertEquals(expResult, result);
       // fail("testAddEntity IS FAILED");
    }
    /**
     * Test of RemoveEntity method, of class AXIS_MoM_WS.
     */
//    @Test (expected = NullPointerException.class)
//    public void testRemoveEntity() {
//        System.out.println("RemoveEntity");
//       String uri = "";
//        String name ="JUNITtestNameDe";
//        String image ="";
//        String type ="JUNITtestTypeDe"; 
//        Entity e = new Entity(name,image,type);
//        AXIS_MoM_WS instance = new AXIS_MoM_WS();
//        Boolean expResult = true;
//        Boolean result = instance.RemoveEntity(e);
//        assertEquals(expResult, result);
//    }

    /**
     * Test of SetEntityProperty method, of class AXIS_MoM_WS.
     */
//    @Test (expected = NullPointerException.class)
//    public void testSetEntityProperty() {
//        System.out.println("SetEntityProperty");
//        Entity e = null;
//        Property p = null;
//        Entity valueEntity = null;
//        AXIS_MoM_WS instance = new AXIS_MoM_WS();
//        Boolean expResult = null;
//        Boolean result = instance.SetEntityProperty(e, p, valueEntity);
//        assertEquals(expResult, result);
//        fail("testSetEntityProperty IS FAILED");
//    }

    /**
     * Test of RemoveEntityProperty method, of class AXIS_MoM_WS.
     */
//    @Test (expected = NullPointerException.class)
//    public void testRemoveEntityProperty() {
//        System.out.println("RemoveEntityProperty");
//       Entity e = null;
//        Property p = null;
//        Entity valueEntity = null;
//        AXIS_MoM_WS instance = new AXIS_MoM_WS();
//        Boolean expResult = null;
//        Boolean result = instance.RemoveEntityProperty(e, p, valueEntity);
//        assertEquals(expResult, result);
//        fail("RemoveEntityProperty IS FAILED");
//    }

    /**
     * Test of RemoveEntityObjectPropertyWithObject method, of class AXIS_MoM_WS.
     */
//    @Test (expected = NullPointerException.class)
//    public void testRemoveEntityObjectPropertyWithObject() {
//        System.out.println("RemoveEntityObjectPropertyWithObject");
//        Entity e = null;
//        Property p = null;
//        Entity valueEntity = null;
//        AXIS_MoM_WS instance = new AXIS_MoM_WS();
//        Boolean expResult = null;
//        Boolean result = instance.RemoveEntityObjectPropertyWithObject(e, p, valueEntity);
//        assertEquals(expResult, result);
//        fail("testRemoveEntityObjectPropertyWithObject IS FAILED");
//    }

    /**
     * Test of LoadEntityProperties method, of class AXIS_MoM_WS.
     */
//    @Test (expected = NullPointerException.class)
//    public void testLoadEntityProperties() {
//        System.out.println("LoadEntityProperties");
//        Entity e = null;
//        AXIS_MoM_WS instance = new AXIS_MoM_WS();
//        Property[] expResult = null;
//        Property[] result = instance.LoadEntityProperties(e);
//        assertArrayEquals(expResult, result);
//        fail("testLoadEntityProperties IS FAILED");
//    }

    /**
     * Test of SearchOurEntitiesFromText method, of class AXIS_MoM_WS.
     */
//    @Test (expected = NullPointerException.class)
//    public void testSearchOurEntitiesFromText() {
//        System.out.println("SearchOurEntitiesFromText");
//        String needle = "";
//        AXIS_MoM_WS instance = new AXIS_MoM_WS();
//        Entity[] expResult = null;
//        Entity[] result = instance.SearchOurEntitiesFromText(needle);
//        assertArrayEquals(expResult, result);
//        fail("SearchOurEntitiesFromText IS FAILED");
//    }

    /**
     * Test of SearchAllEntitiesFromText method, of class AXIS_MoM_WS.
     */
//    @Test (expected = NullPointerException.class)
//    public void testSearchAllEntitiesFromText() {
//        System.out.println("SearchAllEntitiesFromText");
//        String needle = "";
//        AXIS_MoM_WS instance = new AXIS_MoM_WS();
//        Entity[] expResult = null;
//        Entity[] result = instance.SearchAllEntitiesFromText(needle);
//        assertArrayEquals(expResult, result);
//        fail("testSearchAllEntitiesFromText IS FAILED");
//    }

    /**
     * Test of AddComment method, of class AXIS_MoM_WS.
     */
//    @Test (expected = NullPointerException.class)
//    public void testAddComment() {
//        System.out.println("AddComment");
//        Comment c = null;
//        Entity e = null;
//        AXIS_MoM_WS instance = new AXIS_MoM_WS();
//        Comment expResult = null;
//        Comment result = instance.AddComment(c, e);
//        assertEquals(expResult, result);
//        fail("testAddComment IS FAILED");
//    }

    /**
     * Test of GrantComment method, of class AXIS_MoM_WS.
     */
//    @Test (expected = NullPointerException.class)
//    public void testGrantComment() {
//        System.out.println("GrantComment");
//        Comment c = null;
//        AXIS_MoM_WS instance = new AXIS_MoM_WS();
//        Boolean expResult = null;
//        Boolean result = instance.GrantComment(c);
//        assertEquals(expResult, result);
//        fail("testGrantComment IS FAILED");
//    }

    /**
     * Test of RemoveComment method, of class AXIS_MoM_WS.
     */
//    @Test (expected = NullPointerException.class)
//    public void testRemoveComment() {
//        System.out.println("RemoveComment");
//        Comment c = null;
//        AXIS_MoM_WS instance = new AXIS_MoM_WS();
//        Boolean expResult = null;
//        Boolean result = instance.RemoveComment(c);
//        assertEquals(expResult, result);
//        fail("testRemoveComment IS FAILELD");
//    }

    /**
     * Test of DenyComment method, of class AXIS_MoM_WS.
     */
//    @Test (expected = NullPointerException.class)
//    public void testDenyComment() {
//        System.out.println("DenyComment");
//        Comment c = null;
//        AXIS_MoM_WS instance = new AXIS_MoM_WS();
//        Boolean expResult = null;
//        Boolean result = instance.DenyComment(c);
//        assertEquals(expResult, result);
//        fail("testDenyComment IS FAILED");
//    }

    /**
     * Test of LoadComment method, of class AXIS_MoM_WS.
     */
//    @Test (expected = NullPointerException.class)
//    public void testLoadComment() {
//        System.out.println("LoadComment");
//        Entity e = null;
//        AXIS_MoM_WS instance = new AXIS_MoM_WS();
//        Comment[] expResult = null;
//        Comment[] result = instance.LoadComment(e);
//        assertArrayEquals(expResult, result);
//        fail("testLoadComment IS FAILED");
//    }

    /**
     * Test of GetAllEntities method, of class AXIS_MoM_WS.
     */
//    @Test (expected = NullPointerException.class)
//    public void testGetAllEntities() {
//        System.out.println("GetAllEntities");
//        AXIS_MoM_WS instance = new AXIS_MoM_WS();
//        Entity[] expResult = null;
//        Entity[] result = instance.GetAllEntities();
//        assertArrayEquals(expResult, result);
//        fail("testGetAllEntities IS FAILED");
//    }

    /**
     * Test of GetAllPropertiesAdmin method, of class AXIS_MoM_WS.
     */
//    @Test (expected = NullPointerException.class)
//    public void testGetAllPropertiesAdmin() {
//        System.out.println("GetAllPropertiesAdmin");
//        Entity e = null;
//        AXIS_MoM_WS instance = new AXIS_MoM_WS();
//        PropertyAdmin[] expResult = null;
//        PropertyAdmin[] result = instance.GetAllPropertiesAdmin(e);
//        assertArrayEquals(expResult, result);
//        fail("testGetAllPropertiesAdmin IS FAILED");
//    }

    /**
     * Test of GetEntity method, of class AXIS_MoM_WS.
     */
//    @Test (expected = NullPointerException.class)
//    public void testGetEntity() {
//        System.out.println("GetEntity");
//        Entity e = null;
//        AXIS_MoM_WS instance = new AXIS_MoM_WS();
//        Entity expResult = null;
//        Entity result = instance.GetEntity(e);
//        assertEquals(expResult, result);
//        fail("testGetEntity IS FAILED");
//    }
    
}
