package au.org.garvan.kccg.agha.redcap.service.impl.parser;

import au.org.garvan.kccg.agha.redcap.domain.QSectionEntry;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tudor on 14/03/17.
 */
public class SectionYAMLParser {

    private Map<String, QSectionEntry> sections;

    private Map data;

    private boolean valid;

    public SectionYAMLParser(String content) {
        this.sections = new HashMap<>();
        this.data = new HashMap();
        this.valid = true;
        Yaml yaml = new Yaml();

        try {
            this.data = (Map) yaml.load(content);
        } catch (Exception e) {
            this.valid = false;
        }
    }

    public boolean isValid() {
        return valid;
    }

    public void process() {
        for(Object key : data.keySet()) {
            QSectionEntry qSectionEntry = new SectionEntryParser(key, (Map) data.get(key)).getqSectionEntry();
            sections.put(qSectionEntry.getObservationSetKey(), qSectionEntry);
        }
    }

    public Map<String, QSectionEntry> getSections() {
        return sections;
    }
}
