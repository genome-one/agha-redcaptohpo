package au.org.garvan.kccg.agha.redcap.rest.dto;

import au.org.garvan.kccg.agha.redcap.domain.PatientEntryData;

/**
 * Created by tudor on 15/03/17.
 */
public class DecomposedPatientDataEntryDtoSerializer {

    public static PatientEntryData dissasemble(DecomposedPatientDataEntryDto decomposedPatientDataEntryDto) {
        PatientEntryData patientEntryData = new PatientEntryData();
        patientEntryData.setData(decomposedPatientDataEntryDto.getData());
        return patientEntryData;
    }

}
