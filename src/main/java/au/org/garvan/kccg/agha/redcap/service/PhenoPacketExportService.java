package au.org.garvan.kccg.agha.redcap.service;

import au.org.garvan.kccg.agha.redcap.domain.PatientEntryDataTransform;
import org.phenopackets.api.PhenoPacket;

/**
 * Created by tudor on 17/03/17.
 */
public interface PhenoPacketExportService {

    PhenoPacket createPhenoPacket(PatientEntryDataTransform patientEntryDataTransform);
}
