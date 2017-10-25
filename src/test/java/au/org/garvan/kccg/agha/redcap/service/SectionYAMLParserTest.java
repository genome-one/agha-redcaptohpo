package au.org.garvan.kccg.agha.redcap.service;

import au.org.garvan.kccg.agha.redcap.domain.QSectionEntry;
import au.org.garvan.kccg.agha.redcap.service.impl.parser.SectionYAMLParser;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by tudor on 15/03/2017.
 */
public class SectionYAMLParserTest {

    @Test
    public void shouldParseSuccessfully() throws Exception {
        String testYMLFile = getClass().getClassLoader().getResource("mito_fascul.yml").toURI().getPath().toString();

        SectionYAMLParser sectionYAMLParser = new SectionYAMLParser(FileUtils.readFileToString(new File(testYMLFile), "UTF-8"));
        assertTrue(sectionYAMLParser.isValid());

        sectionYAMLParser.process();
        Map<String, QSectionEntry> sections = sectionYAMLParser.getSections();
        assertFalse(sections.isEmpty());
        assertEquals(sections.size(), 1);

        assertTrue(sections.containsKey("mito_fascul"));
        QSectionEntry qSectionEntry = sections.get("mito_fascul");

        assertEquals(qSectionEntry.getObservationSetCategory(), "mito");
        assertEquals(qSectionEntry.getObservationSetValue(), "fascul");
        assertEquals(qSectionEntry.getDefinition().getType(), "binary");
        assertEquals(qSectionEntry.getDefinition().getDirectMapping(), "HP:0002380");
        assertEquals(qSectionEntry.getDefinition().getValueMappingStructure().size(), 2);

        assertFalse(qSectionEntry.getChildren().isEmpty());
    }

}
