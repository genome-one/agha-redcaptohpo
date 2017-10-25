package au.org.garvan.kccg.agha.redcap.domain;

/**
 * Created by tudor on 14/03/17.
 */
public class ValueMappingEntry {

    private String value;

    private String directMapping;

    public ValueMappingEntry() {

    }

    public ValueMappingEntry(String value, String directMapping) {
        this.value = value;
        this.directMapping = directMapping;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDirectMapping() {
        return directMapping;
    }

    public void setDirectMapping(String directMapping) {
        this.directMapping = directMapping;
    }
}
