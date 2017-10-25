package au.org.garvan.kccg.agha.redcap.service.impl;

import au.org.garvan.kccg.agha.redcap.domain.PatientEntryDataTransform;
import au.org.garvan.kccg.agha.redcap.service.PhenoPacketExportService;
import com.google.common.collect.ImmutableList;
import org.phenopackets.api.PhenoPacket;
import org.phenopackets.api.model.association.PhenotypeAssociation;
import org.phenopackets.api.model.condition.Phenotype;
import org.phenopackets.api.model.entity.Person;
import org.phenopackets.api.model.ontology.OntologyClass;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by tudor on 17/03/17.
 */
@Service
public class PhenoPacketExportServiceImpl implements PhenoPacketExportService {

    public PhenoPacket createPhenoPacket(PatientEntryDataTransform patientEntryDataTransform) {
        Person person = new Person();
        person.setId(UUID.randomUUID().toString());
        List<PhenotypeAssociation> phenotypeAssociation = createPhenotypes(person, patientEntryDataTransform.getData());

        PhenoPacket phenoPacket = new PhenoPacket.Builder()
                .id("PXF:000000")
                .addPerson(person)
                .setPhenotypeAssociations(phenotypeAssociation)
                .build();
        return phenoPacket;
    }

    private List<PhenotypeAssociation> createPhenotypes(Person person, Map<String, String> patientPhenotype) {
        List<PhenotypeAssociation> phenotypeAssociationList = new ArrayList<>();
        for (String hpoId : patientPhenotype.values()) {
            Phenotype phenotype = new Phenotype();
            phenotype.setTypes(ImmutableList.of(OntologyClass.of(hpoId, hpoId)));

            PhenotypeAssociation patientPhenotypeAssociation = new PhenotypeAssociation.Builder(phenotype)
                    .setEntity(person)
                    .build();
            phenotypeAssociationList.add(patientPhenotypeAssociation);
        }

        return phenotypeAssociationList;
    }


}
