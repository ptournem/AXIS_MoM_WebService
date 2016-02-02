package ws;

import Dialog.Comment;
import Dialog.Entity;
import Dialog.Property;
import Dialog.PropertyAdmin;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Interface à implémenter dans les web services qui vont avec le projet
 * AXIS_MoM_WebSite
 *
 */
@WebService(name = "AXIS_MoM_WS")
public interface AXIS_MoM_WSInterface {

    /**
     * Ajoute une entité
     *
     * @param e
     * @return
     */
    @WebMethod(operationName = "AddEntity")
    Entity AddEntity(@WebParam(name = "e") Entity e);

    /**
     * Supprime une entité
     *
     * @param e
     * @return
     */
    @WebMethod(operationName = "RemoveEntity")
    Boolean RemoveEntity(@WebParam(name = "e") Entity e);

    /**
     * Set la property p d'une entite e
     *
     * @param e
     * @param p
     * @param valueEntity
     * @return
     */
    @WebMethod(operationName = "SetEntityProperty")
    Boolean SetEntityProperty(@WebParam(name = "e") Entity e, @WebParam(name = "p") Property p, @WebParam(name = "valueEntity") Entity valueEntity);

    /**
     * Remove la property p d'une entité e
     *
     * @param e
     * @param p
     * @param valueEntity
     * @param valueText
     * @return
     */
    @WebMethod(operationName = "RemoveEntityObjectProperty")
    Boolean RemoveEntityProperty(@WebParam(name = "e") Entity e, @WebParam(name = "p") Property p, @WebParam(name = "valueEntity") Entity valueEntity);

    /**
     * Remove la property p d'une entité e et l'entité e
     *
     * @param e
     * @param p
     * @param valueEntity
     * @return
     */
    @WebMethod(operationName = "RemoveEntityObjectPropertyWithObject")
    Boolean RemoveEntityObjectPropertyWithObject(@WebParam(name = "e") Entity e, @WebParam(name = "p") Property p, @WebParam(name = "valueEntity") Entity valueEntity);

    /**
     * Charge les propriété d'une entité e
     *
     * @param e
     * @return
     */
    @WebMethod(operationName = "LoadEntityProperties")
    Property[] LoadEntityProperties(@WebParam(name = "e") Entity e);

    /**
     * Cherche les entité contenant le texte needle dans la bdd sémantique
     * interne
     *
     * @param needle
     * @return
     */
    @WebMethod(operationName = "SearchOurEntitiesFromText")
    Entity[] SearchOurEntitiesFromText(@WebParam(name = "needle") String needle);

    /**
     * Cherche les entité contenant le texte needle dans la bdd sémantique
     * interne ou le LoD
     *
     * @param needle
     * @return
     */
    @WebMethod(operationName = "SearchAllEntitiesFromText")
    Entity[] SearchAllEntitiesFromText(@WebParam(name = "needle") String needle);

    /**
     * Ajoute un commentaire c
     *
     * @param c
     * @return
     */
    @WebMethod(operationName = "AddComment")
    Comment AddComment(@WebParam(name = "c") Comment c, @WebParam(name = "e") Entity e);

    /**
     * Approuve un commentaire c
     *
     * @param c
     * @return
     */
    @WebMethod(operationName = "GrantComment")
    Boolean GrantComment(@WebParam(name = "c") Comment c);

    /**
     * Supprime un commentaire c
     *
     * @param c
     * @return
     */
    @WebMethod(operationName = "RemoveComment")
    Boolean RemoveComment(@WebParam(name = "c") Comment c, @WebParam(name = "e") Entity e);

    @WebMethod(operationName = "DenyComment")
    Boolean DenyComment(@WebParam(name = "c") Comment c);
    
    /**
     * Charge les commentaire d'une entité e
     *
     * @param e
     * @return
     */
    @WebMethod(operationName = "LoadComment")
    Comment[] LoadComment(@WebParam(name = "e") Entity e);

    
    /**
     * Renvoie tous les entities de la base locale
     *
     * @return
     */
    @WebMethod(operationName = "GetAllEntities")
    Entity[] GetAllEntities();

    @WebMethod(operationName = "GetAllPropertiesAdmin")
    PropertyAdmin[] GetAllPropertiesAdmin(@WebParam(name = "e") Entity e);

    @WebMethod(operationName = "GetEntity")
    Entity GetEntity(@WebParam(name = "e") Entity e);
}
