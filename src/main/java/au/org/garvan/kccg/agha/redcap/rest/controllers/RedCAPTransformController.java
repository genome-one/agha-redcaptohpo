package au.org.garvan.kccg.agha.redcap.rest.controllers;

import au.org.garvan.kccg.agha.redcap.constants.RedCAPMappingConstants;
import au.org.garvan.kccg.agha.redcap.domain.PatientEntryData;
import au.org.garvan.kccg.agha.redcap.domain.PatientEntryDataTransform;
import au.org.garvan.kccg.agha.redcap.rest.dto.DecomposedPatientDataEntryDto;
import au.org.garvan.kccg.agha.redcap.rest.dto.DecomposedPatientDataEntryDtoSerializer;
import au.org.garvan.kccg.agha.redcap.rest.dto.PatientEntryDataTransformDto;
import au.org.garvan.kccg.agha.redcap.service.PhenoPacketExportService;
import au.org.garvan.kccg.agha.redcap.service.QSectionStructuredParserService;
import org.phenopackets.api.PhenoPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tudor on 14/03/17.
 */
@RestController
public class RedCAPTransformController {

    private static final Logger log = LoggerFactory.getLogger(RedCAPTransformController.class);

    @Autowired
    private QSectionStructuredParserService qSectionStructuredParserService;

    @Autowired
    private PhenoPacketExportService phenoPacketExportService;

    @RequestMapping(
            value = RedCAPMappingConstants.API_V1 +
                    RedCAPMappingConstants.API_TRANSFORM,
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PatientEntryDataTransformDto tranform(@RequestBody DecomposedPatientDataEntryDto decomposedPatientDataEntryDto,
                                                 HttpServletRequest request) {
        log.info("Request to transform patient entry data");
        PatientEntryData patientEntryData = DecomposedPatientDataEntryDtoSerializer.dissasemble(decomposedPatientDataEntryDto);

        PatientEntryDataTransform patientEntryDataTransform = qSectionStructuredParserService.map(patientEntryData);
        PhenoPacket phenoPacket = phenoPacketExportService.createPhenoPacket(patientEntryDataTransform);
        return new PatientEntryDataTransformDto(patientEntryDataTransform.getData());
    }

    @RequestMapping(
            value = RedCAPMappingConstants.API_V1 +
                    RedCAPMappingConstants.API_TRANSFORM +
                    RedCAPMappingConstants.API_PXF,
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PhenoPacket tranformToPXF(@RequestBody DecomposedPatientDataEntryDto decomposedPatientDataEntryDto,
                                                 HttpServletRequest request) {
        log.info("Request to transform patient entry data to PXF");
        PatientEntryData patientEntryData = DecomposedPatientDataEntryDtoSerializer.dissasemble(decomposedPatientDataEntryDto);

        PatientEntryDataTransform patientEntryDataTransform = qSectionStructuredParserService.map(patientEntryData);
        PhenoPacket phenoPacket = phenoPacketExportService.createPhenoPacket(patientEntryDataTransform);
        return phenoPacket;
    }
}
