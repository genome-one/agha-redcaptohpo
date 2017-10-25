package au.org.garvan.kccg.agha.redcap.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tudor on 14/03/17.
 */
public class QSectionEntry {

    private String observationSetKey;

    private String observationSetCategory;

    private String observationSetValue;

    private QSectionEntryDefinition definition;

    private List<QSectionEntry> children;

    public QSectionEntry(String observationSetKey) {
        this.observationSetKey = observationSetKey;
        String[] parts = observationSetKey.split("_");
        observationSetCategory = parts[0];
        observationSetValue = parts[1];
        children = new ArrayList<>();
    }

    public void addChild(QSectionEntry qSectionEntry) {
        this.children.add(qSectionEntry);
    }

    public String getObservationSetKey() {
        return observationSetKey;
    }

    public void setObservationSetKey(String observationSetKey) {
        this.observationSetKey = observationSetKey;
    }

    public String getObservationSetCategory() {
        return observationSetCategory;
    }

    public void setObservationSetCategory(String observationSetCategory) {
        this.observationSetCategory = observationSetCategory;
    }

    public String getObservationSetValue() {
        return observationSetValue;
    }

    public void setObservationSetValue(String observationSetValue) {
        this.observationSetValue = observationSetValue;
    }

    public QSectionEntryDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(QSectionEntryDefinition definition) {
        this.definition = definition;
    }

    public List<QSectionEntry> getChildren() {
        return children;
    }

    public void setChildren(List<QSectionEntry> children) {
        this.children = children;
    }
}
