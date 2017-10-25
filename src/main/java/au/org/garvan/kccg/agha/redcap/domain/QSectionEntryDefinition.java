package au.org.garvan.kccg.agha.redcap.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tudor on 14/03/17.
 */
public class QSectionEntryDefinition {

    private String type;

    private String value;

    private Map<String, ValueMappingEntry> valueMappingStructure;

    private String directMapping;

    public QSectionEntryDefinition() {
        valueMappingStructure = new HashMap<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Map<String, ValueMappingEntry> getValueMappingStructure() {
        return valueMappingStructure;
    }

    public void setValueMappingStructure(Map<String, ValueMappingEntry> valueMappingStructure) {
        this.valueMappingStructure = valueMappingStructure;
    }

    public String getDirectMapping() {
        return directMapping;
    }

    public void setDirectMapping(String directMapping) {
        this.directMapping = directMapping;
    }

    public void addValueMapping(String optionValue, ValueMappingEntry valueMappingEntry) {
        this.valueMappingStructure.put(optionValue, valueMappingEntry);
    }
}
