package au.org.garvan.kccg.agha.redcap.domain;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by tudor on 15/03/17.
 */
public class PatientEntryData {

    private Map<String, String> data;

    public PatientEntryData() {
        this.data = new LinkedHashMap<>();
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public void add(String key, String mapping) {
        this.data.put(key, mapping);
    }
}
