package au.org.garvan.kccg.agha.redcap.service.impl.parser;

import au.org.garvan.kccg.agha.redcap.constants.QuestionnaireConstants;
import au.org.garvan.kccg.agha.redcap.domain.QSectionEntryDefinition;
import au.org.garvan.kccg.agha.redcap.domain.ValueMappingEntry;
import au.org.garvan.kccg.agha.redcap.exception.InvalidObservationSetDefinitionException;

import java.util.Map;

/**
 * Created by tudor on 14/03/17.
 */
public class DefinitionParser {

    private String observationSetKey;

    private Map definitionContentMap;

    private QSectionEntryDefinition qSectionEntryDefinition;

    public DefinitionParser(String observationSetKey, Map definitionContentMap) {
        this.observationSetKey = observationSetKey;
        this.qSectionEntryDefinition = new QSectionEntryDefinition();
        this.definitionContentMap = definitionContentMap;
        this.parseDefinition();
    }

    private void parseDefinition() {
        String type = (String) definitionContentMap.get(QuestionnaireConstants.TYPE);
        if (type == null) {
            throw new InvalidObservationSetDefinitionException("[" + observationSetKey + "] Type element of the observation set definition is missing.");
        }
        qSectionEntryDefinition.setType(type);

        String value = (String) definitionContentMap.get(QuestionnaireConstants.VALUE);
        if (value != null) {
            qSectionEntryDefinition.setValue(value);
        }

        String directMapping = (String) definitionContentMap.get(QuestionnaireConstants.DIRECTMAPPING);
        if (directMapping != null) {
            qSectionEntryDefinition.setDirectMapping(directMapping);
        }

        if (definitionContentMap.containsKey(QuestionnaireConstants.OPTIONS)) {
            Map map = (Map) definitionContentMap.get(QuestionnaireConstants.OPTIONS);
            for (Object optionValue : map.keySet()) {
                String optValue = (optionValue instanceof Integer) ? optionValue.toString() : (String) optionValue;
                Map optionsMap = (Map) map.get(optionValue);
                String actualValue = (String) optionsMap.get(QuestionnaireConstants.VALUE);
                String directMap = (String) optionsMap.get(QuestionnaireConstants.DIRECTMAPPING);
                qSectionEntryDefinition.addValueMapping(optValue, new ValueMappingEntry(actualValue, directMap));
            }
        }
    }

    public QSectionEntryDefinition getqSectionEntryDefinition() {
        return qSectionEntryDefinition;
    }
}
