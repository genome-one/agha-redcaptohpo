package au.org.garvan.kccg.agha.redcap.service;

import au.org.garvan.kccg.agha.redcap.domain.PatientEntryData;
import au.org.garvan.kccg.agha.redcap.domain.PatientEntryDataTransform;

/**
 * Created by tudor on 14/03/17.
 */
public interface QSectionStructuredParserService {

    PatientEntryDataTransform map(PatientEntryData patientEntryData);

}
