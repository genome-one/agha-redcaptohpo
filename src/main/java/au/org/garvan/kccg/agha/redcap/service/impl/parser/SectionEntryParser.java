package au.org.garvan.kccg.agha.redcap.service.impl.parser;

import au.org.garvan.kccg.agha.redcap.constants.QuestionnaireConstants;
import au.org.garvan.kccg.agha.redcap.domain.QSectionEntry;
import au.org.garvan.kccg.agha.redcap.domain.QSectionEntryDefinition;
import au.org.garvan.kccg.agha.redcap.exception.InvalidObservationSetDefinitionException;

import java.util.Map;

/**
 * Created by tudor on 14/03/17.
 */
public class SectionEntryParser {

    private String observationSetKey;

    private Map sectionContentMap;

    private QSectionEntry qSectionEntry;

    public SectionEntryParser(Object observationSetKey, Map sectionContentMap) {
        this.observationSetKey = (String) observationSetKey;
        this.sectionContentMap = sectionContentMap;
        this.qSectionEntry = new QSectionEntry((String) observationSetKey);

        processSection();
    }

    private void processSection() {
        if (!sectionContentMap.containsKey(QuestionnaireConstants.DEFINITION)) {
            throw new InvalidObservationSetDefinitionException("[" + observationSetKey + "] Definition element of the observation set is missing.");
        }

        QSectionEntryDefinition definition = new DefinitionParser(observationSetKey, (Map) sectionContentMap.get(QuestionnaireConstants.DEFINITION)).getqSectionEntryDefinition();
        if (definition != null) {
            qSectionEntry.setDefinition(definition);
        }

        if (sectionContentMap.containsKey(QuestionnaireConstants.CHILDREN)) {
            Map childrenMap = (Map) sectionContentMap.get(QuestionnaireConstants.CHILDREN);
            for (Object childKey : childrenMap.keySet()) {
                Map childContent = (Map) childrenMap.get(childKey);
                QSectionEntry childEntry = new SectionEntryParser(childKey, childContent).getqSectionEntry();
                if (childEntry != null) {
                    qSectionEntry.addChild(childEntry);
                }
            }
        }
    }

    public QSectionEntry getqSectionEntry() {
        return qSectionEntry;
    }
}
