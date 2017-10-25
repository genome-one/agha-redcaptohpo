package au.org.garvan.kccg.agha.redcap.rest;

import au.org.garvan.kccg.agha.redcap.constants.RedCAPMappingConstants;
import au.org.garvan.kccg.agha.redcap.rest.dto.DecomposedPatientDataEntryDto;
import au.org.garvan.kccg.agha.redcap.rest.dto.PatientEntryDataTransformDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by tudor on 15/03/2017.
 */

public class RedCAPTransformControllerTest extends IntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldTransform() throws Exception {
        Map<String, String> patientData = new LinkedHashMap<>();
        patientData.put("mito_mp", "Y");
        patientData.put("mito_mc", "N");
        patientData.put("mito_fc", "Y");
        patientData.put("mito_rhab", "N");

        DecomposedPatientDataEntryDto decomposedPatientDataEntryDto = new
                DecomposedPatientDataEntryDto(patientData);

        String getResponse = mockMvc.perform(post(RedCAPMappingConstants.API_V1 +
                RedCAPMappingConstants.API_TRANSFORM).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(decomposedPatientDataEntryDto)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        PatientEntryDataTransformDto patientEntryDataTransformDto = mapper.readValue(getResponse, new TypeReference<PatientEntryDataTransformDto>() {
        });
        assertFalse(patientEntryDataTransformDto.getData().isEmpty());
        assertEquals(patientEntryDataTransformDto.getData().get("mito_mp"), "HP:0003326");
        assertEquals(patientEntryDataTransformDto.getData().get("mito_mc"), "HP:0003394");
        assertEquals(patientEntryDataTransformDto.getData().get("mito_fc"), "HP:0003750");
        assertEquals(patientEntryDataTransformDto.getData().get("mito_rhab"), "HP:0003201");
    }

}
