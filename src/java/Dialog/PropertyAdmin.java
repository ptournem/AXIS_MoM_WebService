package Dialog;

import java.util.ArrayList;

public class PropertyAdmin {

    private String name;
    private String value_locale;
    private String value_dbpedia;
    private Entity[] entity_locale;
    private Entity[] entity_dbpedia;
    private String type;

    public PropertyAdmin() {
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getValue_locale() {
	return value_locale;
    }

    public void setValue_locale(String value_locale) {
	this.value_locale = value_locale;
    }

    public String getValue_dbpedia() {
	return value_dbpedia;
    }

    public void setValue_dbpedia(String value_dbpedia) {
	this.value_dbpedia = value_dbpedia;
    }

    public Entity[] getEntity_locale() {
	return entity_locale;
    }

    public void setEntity_locale(Entity[] entity_locale) {
	this.entity_locale = entity_locale;
    }

    public Entity[] getEntity_dbpedia() {
	return entity_dbpedia;
    }

    public void setEntity_dbpedia(Entity[] entity_dbpedia) {
	this.entity_dbpedia = entity_dbpedia;
    }
    
    
    @Override
    public String toString() {
        String tabE = "";
        if(entity_dbpedia != null) {
            for (int i = 0; i < entity_dbpedia.length; i++) {
                tabE += this.entity_dbpedia[i]+" - ";
            }
        }
        
        String tabE2 = "";
        if(entity_locale != null) {
            for (int i = 0; i < entity_locale.length; i++) {
                tabE2 += this.entity_locale[i]+" - ";
            }
        }
        
        return "PropertyAdmin{" + "\u001B[34mname=\u001B[0m" + name + ", \u001B[34mvalue_locale=\u001B[0m" + value_locale + ", \u001B[34mvalue_dbpedia=\u001B[0m" + value_dbpedia + ", \u001B[34mentity_locale=\u001B[0m" + tabE2 + ", \u001B[34mentity_dbpedia=\u001B[0m" + tabE + ", \u001B[34mtype=\u001B[0m" + type + '}';
    }

}
