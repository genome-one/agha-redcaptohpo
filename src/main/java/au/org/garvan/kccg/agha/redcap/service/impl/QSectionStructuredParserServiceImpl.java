package au.org.garvan.kccg.agha.redcap.service.impl;

import au.org.garvan.kccg.agha.redcap.domain.PatientEntryData;
import au.org.garvan.kccg.agha.redcap.domain.PatientEntryDataTransform;
import au.org.garvan.kccg.agha.redcap.domain.QSectionEntry;
import au.org.garvan.kccg.agha.redcap.service.QSectionStructuredParserService;
import au.org.garvan.kccg.agha.redcap.service.impl.parser.SectionYAMLParser;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tudor on 14/03/17.
 */
@Service
public class QSectionStructuredParserServiceImpl implements QSectionStructuredParserService {

    private Map<String, Map<String, QSectionEntry>> sectionEntries;

    @Autowired
    private String sectionDefinitionsLocation;

    @Autowired
    public QSectionStructuredParserServiceImpl(String sectionDefinitionsLocation) {
        sectionEntries = new HashMap<>();
        loadDefinitions(sectionDefinitionsLocation);
    }

    private void loadDefinitions(String sectionDefinitionsLocation) {
        File[] files = new File(sectionDefinitionsLocation).listFiles();
        for (File file : files) {
            try {
                SectionYAMLParser sectionYAMLParser = new SectionYAMLParser(FileUtils.readFileToString(file, "UTF-8"));
                if (sectionYAMLParser.isValid()) {
                    sectionYAMLParser.process();
                    Map<String, QSectionEntry> sections = sectionYAMLParser.getSections();
                    for (QSectionEntry qSectionEntry : sections.values()) {
                        String category = qSectionEntry.getObservationSetCategory();
                        Map<String, QSectionEntry> sectionMap = new HashMap<>();
                        sectionMap.put(qSectionEntry.getObservationSetKey(), qSectionEntry);
                        for (QSectionEntry child : qSectionEntry.getChildren()) {
                            addChild(child, sectionMap);
                        }

                        Map<String, QSectionEntry> existingMap = sectionEntries.containsKey(category) ? sectionEntries.get(category) : new HashMap<>();
                        existingMap.putAll(sectionMap);
                        sectionEntries.put(category, existingMap);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addChild(QSectionEntry qSectionEntry, Map<String, QSectionEntry> sectionMap) {
        sectionMap.put(qSectionEntry.getObservationSetKey(), qSectionEntry);
        for (QSectionEntry child : qSectionEntry.getChildren()) {
            addChild(child, sectionMap);
        }
    }

    @Override
    public PatientEntryDataTransform map(PatientEntryData patientEntryData) {
        PatientEntryDataTransform patientEntryDataTransform = new PatientEntryDataTransform();
        for (String key : patientEntryData.getData().keySet()) {
            String[] parts = key.split("_");
            String category = parts[0];

            QSectionEntry qSectionEntry = sectionEntries.containsKey(category) ? sectionEntries.get(category).get(key) : null;
            if (qSectionEntry != null) {
                patientEntryDataTransform.add(key, qSectionEntry.getDefinition().getDirectMapping());
            }
        }
        return patientEntryDataTransform;
    }
}
